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

public class BinaryArchiveProviderTest {
	private LoggerFactory loggerFactory = new LoggerFactorySpy();
	private ModuleInitializerSpy moduleInitializerSpy;
	private BinaryArchiveInstanceProviderSpy instanceProviderSpy;

	@BeforeMethod
	public void beforeMethod() {
		LoggerProvider.setLoggerFactory(loggerFactory);
		BinaryArchiveProvider.onlyForTestSetBinaryArchiveInstanceProvider(null);
	}

	private void setupModuleInstanceProviderToReturnRecordStorageFactorySpy() {
		moduleInitializerSpy = new ModuleInitializerSpy();
		instanceProviderSpy = new BinaryArchiveInstanceProviderSpy();
		moduleInitializerSpy.MRV.setDefaultReturnValuesSupplier(
				"loadOneImplementationBySelectOrder",
				((Supplier<BinaryArchiveInstanceProvider>) () -> instanceProviderSpy));
		BinaryArchiveProvider.onlyForTestSetModuleInitializer(moduleInitializerSpy);
	}

	@Test
	public void testGetBinaryArchiveProviderExtendsAbstractProvider() throws Exception {
		assertTrue(AbstractProvider.class.isAssignableFrom(BinaryArchiveProvider.class));
	}

	@Test
	public void testGetBinaryArchiveIsSynchronized_toPreventProblemsWithFindingImplementations()
			throws Exception {
		Method getBinaryArchive = BinaryArchiveProvider.class.getMethod("getBinaryArchive");
		assertTrue(Modifier.isSynchronized(getBinaryArchive.getModifiers()));
	}

	@Test
	public void testGetBinaryArchiveUsesModuleInitializerToGetFactory() throws Exception {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		BinaryArchive binaryArchive = BinaryArchiveProvider.getBinaryArchive();

		moduleInitializerSpy.MCR.assertParameters("loadOneImplementationBySelectOrder", 0,
				BinaryArchiveInstanceProvider.class);
		instanceProviderSpy.MCR.assertReturn("getBinaryArchive", 0, binaryArchive);
	}

	@Test
	public void testOnlyForTestSetBinaryArchiveInstanceProvider() throws Exception {
		BinaryArchiveInstanceProviderSpy instanceProviderSpy2 = new BinaryArchiveInstanceProviderSpy();
		BinaryArchiveProvider.onlyForTestSetBinaryArchiveInstanceProvider(instanceProviderSpy2);

		BinaryArchive binaryArchive = BinaryArchiveProvider.getBinaryArchive();

		instanceProviderSpy2.MCR.assertReturn("getBinaryArchive", 0, binaryArchive);
	}

	@Test
	public void testMultipleCallsToGetRecordStorageOnlyLoadsImplementationsOnce() throws Exception {
		setupModuleInstanceProviderToReturnRecordStorageFactorySpy();

		BinaryArchiveProvider.getBinaryArchive();
		BinaryArchiveProvider.getBinaryArchive();
		BinaryArchiveProvider.getBinaryArchive();
		BinaryArchiveProvider.getBinaryArchive();

		moduleInitializerSpy.MCR.assertNumberOfCallsToMethod("loadOneImplementationBySelectOrder",
				1);
	}
}
