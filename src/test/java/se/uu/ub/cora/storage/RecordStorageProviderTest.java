/*
 * Copyright 2022 Uppsala University Library
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

import static org.testng.Assert.assertTrue;

import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.initialize.AbstractProvider;
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
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();
		RecordStorageProvider.onlyForTestSetRecordStorageInstanceProvider(null);
	}

	private void setupModuleInstanceProviderToReturnRecordStorageFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		instanceProviderSpy = new RecordStorageInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder",
				((Supplier<RecordStorageInstanceProvider>) () -> instanceProviderSpy));
		RecordStorageProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@Test
	public void classExtendsAbstractProvider() throws Exception {
		assertTrue(AbstractProvider.class.isAssignableFrom(RecordStorageProvider.class));
	}

	@Test
	public void testGetRecordStorageUsesModuleInitializerToGetFactory() throws Exception {
		RecordStorage recordStorage = RecordStorageProvider.getRecordStorage();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				RecordStorageInstanceProvider.class);
		instanceProviderSpy.MCR.assertReturn("getRecordStorage", 0, recordStorage);
	}

	@Test
	public void testOnlyForTestSetRecordStorageInstanceProvider() throws Exception {
		RecordStorageInstanceProviderSpy instanceProviderSpy2 = new RecordStorageInstanceProviderSpy();
		RecordStorageProvider.onlyForTestSetRecordStorageInstanceProvider(instanceProviderSpy2);

		RecordStorage recordStorage = RecordStorageProvider.getRecordStorage();

		instanceProviderSpy2.MCR.assertReturn("getRecordStorage", 0, recordStorage);
	}

	@Test
	public void testMultipleCallsToGetRecordStorageOnlyLoadsImplementationsOnce() throws Exception {
		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();
		RecordStorageProvider.getRecordStorage();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}
}
