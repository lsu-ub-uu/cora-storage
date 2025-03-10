/*
 * Copyright 2022, 2025 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */
package se.uu.ub.cora.storage;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.initialize.ModuleInitializer;
import se.uu.ub.cora.initialize.ModuleInitializerImp;
import se.uu.ub.cora.initialize.spies.ModuleInitializerSpy;
import se.uu.ub.cora.logger.LoggerFactory;
import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.logger.spies.LoggerFactorySpy;

public class RecordStorageProviderTest {
	private LoggerFactory loggerFactory = new LoggerFactorySpy();
	private ModuleInitializerSpy moduleInitializerSpy;
	private RecordStorageInstanceProviderSpy instanceProviderSpy;

	@BeforeMethod
	public void beforeMethod() {
		LoggerProvider.setLoggerFactory(loggerFactory);
	}

	private void setupModuleInstanceProviderToReturnRecordStorageFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		instanceProviderSpy = new RecordStorageInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder", () -> instanceProviderSpy);
		RecordStorageProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@AfterMethod
	private void afterMethod() {
		RecordStorageProvider.onlyForTestSetRecordStorageInstanceProvider(null);
	}

	// Force test to run first
	@Test(priority = -1)
	public void testDefaultInitializerIsModuleInitalizer() {
		ModuleInitializer initializer = RecordStorageProvider.onlyForTestGetModuleInitializer();
		assertNotNull(initializer);
		assertTrue(initializer instanceof ModuleInitializerImp);
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<RecordStorageProvider> constructor = RecordStorageProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<RecordStorageProvider> constructor = RecordStorageProvider.class
				.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testGetRecordStorageIsSynchronized_toPreventProblemsWithFindingImplementations()
			throws Exception {
		Method getRecordStorage = RecordStorageProvider.class.getMethod("getRecordStorage");
		assertTrue(Modifier.isSynchronized(getRecordStorage.getModifiers()));
	}

	@Test
	public void testGetRecordStorageUsesModuleInitializerToGetFactory() {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		RecordStorage recordStorage = RecordStorageProvider.getRecordStorage();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				RecordStorageInstanceProvider.class);
		instanceProviderSpy.MCR.assertReturn("getRecordStorage", 0, recordStorage);
	}

	@Test
	public void testOnlyForTestSetRecordStorageInstanceProvider() {
		RecordStorageInstanceProviderSpy instanceProviderSpy2 = new RecordStorageInstanceProviderSpy();
		RecordStorageProvider.onlyForTestSetRecordStorageInstanceProvider(instanceProviderSpy2);

		RecordStorage recordStorage = RecordStorageProvider.getRecordStorage();

		instanceProviderSpy2.MCR.assertReturn("getRecordStorage", 0, recordStorage);
	}

	@Test
	public void testMultipleCallsToGetRecordStorageOnlyLoadsImplementationsOnce() {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}

	@Test
	public void testCallDataChanged() {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		String type = "someType";
		String id = "someId";
		String action = "someAction";
		RecordStorageProvider.dataChanged(type, id, action);

		instanceProviderSpy.MCR.assertCalledParameters("dataChanged", type, id, action);
	}
}
