/*
 * Copyright 2022, 2023 Uppsala University Library
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
package se.uu.ub.cora.storage.archive;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.initialize.AbstractProvider;
import se.uu.ub.cora.initialize.spies.ModuleInitializerSpy;
import se.uu.ub.cora.logger.LoggerFactory;
import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.logger.spies.LoggerFactorySpy;

public class ResourceArchiveProviderTest {
	private LoggerFactory loggerFactory = new LoggerFactorySpy();
	private ModuleInitializerSpy moduleInitializerSpy;
	private ResourceArchiveInstanceProviderSpy instanceProviderSpy;

	@BeforeMethod
	public void beforeMethod() {
		LoggerProvider.setLoggerFactory(loggerFactory);
		ResourceArchiveProvider.onlyForTestSetInstanceProvider(null);
	}

	private void setupModuleInstanceProviderToReturnRecordStorageFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		instanceProviderSpy = new ResourceArchiveInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder",
				((Supplier<ResourceArchiveInstanceProvider>) () -> instanceProviderSpy));
		ResourceArchiveProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@Test
	public void testGetBinaryArchiveProviderExtendsAbstractProvider() throws Exception {
		assertTrue(AbstractProvider.class.isAssignableFrom(ResourceArchiveProvider.class));
	}

	@Test
	public void testGetBinaryArchiveIsSynchronized_toPreventProblemsWithFindingImplementations()
			throws Exception {
		Method getBinaryArchive = ResourceArchiveProvider.class.getMethod("getResourceArchive");
		assertTrue(Modifier.isSynchronized(getBinaryArchive.getModifiers()));
	}

	@Test
	public void testGetBinaryArchiveUsesModuleInitializerToGetFactory() throws Exception {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		ResourceArchive binaryArchive = ResourceArchiveProvider.getResourceArchive();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				ResourceArchiveInstanceProvider.class);
		instanceProviderSpy.MCR.assertReturn("getResourceArchive", 0, binaryArchive);
	}

	@Test
	public void testOnlyForTestSetBinaryArchiveInstanceProvider() throws Exception {
		ResourceArchiveInstanceProviderSpy instanceProviderSpy2 = new ResourceArchiveInstanceProviderSpy();
		ResourceArchiveProvider.onlyForTestSetInstanceProvider(instanceProviderSpy2);

		ResourceArchive binaryArchive = ResourceArchiveProvider.getResourceArchive();

		instanceProviderSpy2.MCR.assertReturn("getResourceArchive", 0, binaryArchive);
	}

	@Test
	public void testMultipleCallsToGetRecordStorageOnlyLoadsImplementationsOnce() throws Exception {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		ResourceArchiveProvider.getResourceArchive();
		ResourceArchiveProvider.getResourceArchive();
		ResourceArchiveProvider.getResourceArchive();
		ResourceArchiveProvider.getResourceArchive();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}
}
