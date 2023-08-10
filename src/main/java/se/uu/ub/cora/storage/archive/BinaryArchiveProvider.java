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

import se.uu.ub.cora.initialize.AbstractProvider;
import se.uu.ub.cora.initialize.SelectOrder;

/**
 * BinaryArchiveProvider is used to provide access to archive for binaries.
 * </p>
 * Implementing {@link BinaryArchiveInstanceProvider}s are found using javas module system, and the
 * one with the higest {@link SelectOrder} is used to provide access to binary archive.
 */
public class BinaryArchiveProvider extends AbstractProvider {
	private static BinaryArchiveInstanceProvider binaryArchiveInstanceProvider;

	/**
	 * getBinaryArchive returns a BinaryArchive that can be used by anything that needs access to
	 * records.
	 * </p>
	 * Code using the returned {@link BinaryArchive} instance MUST consider the returned instance as
	 * NOT thread safe.
	 * 
	 * @return A BinaryArchive that gives access to storage for records
	 */
	public static synchronized BinaryArchive getBinaryArchive() {
		locateAndChooseBinaryArchiveInstanceProvider();
		return binaryArchiveInstanceProvider.getBinaryArchive();
	}

	private static void locateAndChooseBinaryArchiveInstanceProvider() {
		if (binaryArchiveInstanceProvider == null) {
			binaryArchiveInstanceProvider = moduleInitializer
					.loadOneImplementationBySelectOrder(BinaryArchiveInstanceProvider.class);
		}
	}

	/**
	 * onlyForTestSetDataRecordFactory sets a BinaryArchiveInstanceProvider that will be used to
	 * return instances for the {@link #getBinaryArchive()} method. This possibility to set a
	 * DataRecordFactory is provided to enable testing of getting a record storage in other classes
	 * and is not intented to be used in production.
	 * <p>
	 * The BinaryArchiveInstanceProvider to use in production should be provided through an
	 * implementation of {@link BinaryArchiveInstanceProvider} in a seperate java module.
	 * 
	 * @param binaryArchiveInstanceProvider
	 *            A binaryArchiveInstanceProvider to use to return binaryArchive instances for
	 *            testing
	 */
	public static void onlyForTestSetBinaryArchiveInstanceProvider(
			BinaryArchiveInstanceProvider binaryArchiveInstanceProvider) {
		BinaryArchiveProvider.binaryArchiveInstanceProvider = binaryArchiveInstanceProvider;
	}
}
