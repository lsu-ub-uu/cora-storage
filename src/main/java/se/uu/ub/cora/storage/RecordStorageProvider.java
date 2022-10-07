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

import se.uu.ub.cora.initialize.AbstractProvider;
import se.uu.ub.cora.initialize.SelectOrder;

/**
 * RecordStorageProvider is used to provide access to storage for Records.
 * </p>
 * Implementing {@link RecordStorageInstanceProvider}s are found using javas module system, and the
 * one with the higest {@link SelectOrder} is used to provide access to record storage.
 */
public class RecordStorageProvider extends AbstractProvider {

	private static RecordStorageInstanceProvider recordStorageInstanceProvider;

	/**
	 * getRecordStorage returns a RecordStorage that can be used by anything that needs access to
	 * records.
	 * </p>
	 * Code using the returned {@link RecordStorage} instance MUST consider the returned intance as
	 * NOT thread safe.
	 * 
	 * @return A RecordStorage that gives access to storage for records
	 */
	public static RecordStorage getRecordStorage() {
		locateAndChooseRecordStorageInstanceProvider();
		return recordStorageInstanceProvider.getRecordStorage();
	}

	private static void locateAndChooseRecordStorageInstanceProvider() {
		if (recordStorageInstanceProvider == null) {
			recordStorageInstanceProvider = moduleInitializer
					.loadOneImplementationBySelectOrder(RecordStorageInstanceProvider.class);
		}
	}

	/**
	 * onlyForTestSetDataRecordFactory sets a RecordStorageInstanceProvider that will be used to
	 * return instances for the {@link #getRecordStorage()} method. This possibility to set a
	 * DataRecordFactory is provided to enable testing of getting a record storage in other classes
	 * and is not intented to be used in production.
	 * <p>
	 * The RecordStorageInstanceProvider to use in production should be provided through an
	 * implementation of {@link RecordStorageInstanceProvider} in a seperate java module.
	 * 
	 * @param recordStorageInstanceProvider
	 *            A recordStorageInstanceProvider to use to return recordStorage instances for
	 *            testing
	 */
	static void onlyForTestSetRecordStorageInstanceProvider(
			RecordStorageInstanceProvider recordStorageInstanceProvider) {
		RecordStorageProvider.recordStorageInstanceProvider = recordStorageInstanceProvider;
	}
}
