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

import se.uu.ub.cora.initialize.ModuleInitializer;
import se.uu.ub.cora.initialize.ModuleInitializerImp;
import se.uu.ub.cora.initialize.SelectOrder;

/**
 * RecordStorageProvider is used to provide access to storage for Records.
 * </p>
 * Implementing {@link RecordStorageInstanceProvider}s are found using javas module system, and the
 * one with the higest {@link SelectOrder} is used to provide access to record storage.
 */
public class RecordStorageProvider {

	private static ModuleInitializer moduleInitializer = new ModuleInitializerImp();
	private static RecordStorageInstanceProvider recordStorageInstanceProvider;

	private RecordStorageProvider() {
		// prevent call to constructor
		throw new UnsupportedOperationException();
	}

	/**
	 * getRecordStorage returns a RecordStorage that can be used by anything that needs access to
	 * records.
	 * </p>
	 * Code using the returned {@link RecordStorage} instance MUST consider the returned instance as
	 * NOT thread safe.
	 * 
	 * @return A RecordStorage that gives access to storage for records
	 */
	public static synchronized RecordStorage getRecordStorage() {
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
	 * dataChanged method is intended to inform the instance provider about data that is changed in
	 * storage. This is to make it possible to implement a cached storage and update relevant
	 * records when data is changed. This change can be done by processes running in the same system
	 * or by processes running on other servers.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @param action
	 *            A String with the action of how the data was changed ("create", "update" or
	 *            "delete").
	 */
	public static void dataChanged(String type, String id, String action) {
		locateAndChooseRecordStorageInstanceProvider();
		recordStorageInstanceProvider.dataChanged(type, id, action);
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
	public static void onlyForTestSetRecordStorageInstanceProvider(
			RecordStorageInstanceProvider recordStorageInstanceProvider) {
		RecordStorageProvider.recordStorageInstanceProvider = recordStorageInstanceProvider;
	}

	static ModuleInitializer onlyForTestGetModuleInitializer() {
		return moduleInitializer;
	}

	static void onlyForTestSetModuleInitializer(ModuleInitializer moduleInitializer) {
		RecordStorageProvider.moduleInitializer = moduleInitializer;
	}

}
