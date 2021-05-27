/*
 * Copyright 2015, 2020 Uppsala University Library
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

import java.util.Collection;

import se.uu.ub.cora.data.DataGroup;

/**
 * RecordStorage is the interface that defines how records are stored and retreived from a Cora
 * system. This interface makes the storage implementation decoupled from the rest of the system
 * enabling different storage solutions to be developed and used depending on the needs of the
 * current system.
 */
public interface RecordStorage {

	/**
	 * read should return, from storage, the record that matches type and id. <br>
	 * <br>
	 * If no record matching type and id is found MUST a {@link RecordNotFoundException} be thrown,
	 * indicating that the requested record can not be found.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @return A {@link DataGroup} with the requested records data
	 */
	DataGroup read(String type, String id);

	void create(String type, String id, DataGroup record, DataGroup collectedTerms,
			DataGroup linkList, String dataDivider);

	void deleteByTypeAndId(String type, String id);

	boolean linksExistForRecord(String type, String id);

	void update(String type, String id, DataGroup record, DataGroup collectedTerms,
			DataGroup linkList, String dataDivider);

	StorageReadResult readList(String type, DataGroup filter);

	StorageReadResult readAbstractList(String type, DataGroup filter);

	DataGroup readLinkList(String type, String id);

	Collection<DataGroup> generateLinkCollectionPointingToRecord(String type, String id);

	boolean recordsExistForRecordType(String type);

	boolean recordExistsForAbstractOrImplementingRecordTypeAndRecordId(String type, String id);

	int getTotalNumberOfRecords(String type, String id);
}
