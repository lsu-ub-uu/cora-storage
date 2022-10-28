/*
 * Copyright 2015, 2020, 2021, 2022 Uppsala University Library
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
import java.util.List;

import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.collected.Link;
import se.uu.ub.cora.data.collected.StorageTerm;

/**
 * RecordStorage is the interface that defines how records are stored and retreived from a Cora
 * system. This interface makes the storage implementation decoupled from the rest of the system
 * enabling different storage solutions to be developed and used depending on the needs of the
 * current system.
 * <p>
 * Instances of RecordStorage SHOULD be accessed through
 * {@link RecordStorageProvider#getRecordStorage()} for each thread that needs access to
 * RecordStorage. Implementations of RecordStorage SHOULD clearly state if they are threadsafe or
 * not, to enable RecordStorageProvider to return the same or new instances as needed.
 * <p>
 * The end goal is that implementing storage solutions should not have to know about implementations
 * details for recordTypes, and should be provided the information needed to handle abstract types,
 * without having to read and understand the set of existing record types.<br>
 * This is a work in progress as can be seen in
 * {@link #getTotalNumberOfRecordsForAbstractType(String, List, DataGroup)}.
 * <p>
 * list of things probably needed:<br>
 * <ul>
 * <li>read needs a new method read(List<String> possibleImplementingTypes, DataGroup filter)</li>
 * <li>readList needs a new method readAbstractList(String abstractType, List<String>
 * implementingTypes, DataGroup filter)</li>
 * <li>readAbstractList needs a new parameter List<String> implementingTypes</li>
 * </ul>
 * 
 */
public interface RecordStorage {

	/**
	 * read should return, from storage, the record that has one of the specified types and the
	 * specified id.
	 * </p>
	 * The list of types is intended to be used in two ways.
	 * <ol>
	 * <li>A single implenting type</li>
	 * <li>A list of all implementing types, if the type looked for is abstract</li>
	 * </ol>
	 * <p>
	 * If no record matching type and id is found MUST a {@link RecordNotFoundException} be thrown,
	 * indicating that the requested record can not be found.
	 * </p>
	 * If more than one record matching type and id is found MUST a {@link StorageException} be
	 * thrown, indicating that the requested record is a duplicate, this should not normally happen
	 * as the a recordId should be unique for an abstract type, and if there is more than one type
	 * in the types list should they all be children to a common abstract type.
	 * 
	 * @param types
	 *            A List<String> with a list of recordTypes
	 * @param id
	 *            A String with the records id
	 * @return A {@link DataGroup} with the requested records data
	 */
	DataGroup read(List<String> types, String id);

	/**
	 * create stores the provided dataRecord in storage. CollectedTerms, linkList and dataDivider is
	 * stored in relationship to type and id.
	 * <p>
	 * The terms (key value pairs) found in collectedTerms should be stored in such a way so they
	 * can be used to filter data when reading.
	 * <p>
	 * The links found in linkList should stored in such a way so they can be used in the
	 * {@link #readLinkList(String, String)} method to retreive all records that has links pointing
	 * to a specified record.
	 * <p>
	 * If a record matching type and id exists in storage since before MUST a
	 * {@link RecordConflictException} be thrown, indicating that the requested record can not be
	 * created.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @param dataRecord
	 *            A {@link DataGroup} with the records data
	 * @param storageTerms
	 *            A list of {@link StorageTerm} containg the storageTerms for the record.
	 * @param links
	 *            A {@link DataGroup} with a list of records that this record has links to
	 * @param dataDivider
	 *            A String representing the system the record belongs to.
	 */
	void create(String type, String id, DataGroup dataRecord, List<StorageTerm> storageTerms,
			List<Link> links, String dataDivider);

	/**
	 * deleteByTypeAndId deletes the existing dataRecord from storage. Any to the record associated
	 * storageTerms and links should also be removed from storage.
	 * <p>
	 * If no record matching type and id is found MUST a {@link RecordNotFoundException} be thrown,
	 * indicating that the record to delete can not be found.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 */
	void deleteByTypeAndId(String type, String id);

	/**
	 * linksExistForRecord returns if there are any other records that link to the record specified
	 * by the entered type and id.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @return A boolean, true if there are any links pointing to this record, else is false
	 *         returned
	 */
	boolean linksExistForRecord(String type, String id);

	/**
	 * update updates the existing dataRecord in storage with the new provided dataRecord.
	 * CollectedTerms, linkList and dataDivider are updated in relationship to type and id. When the
	 * update is complete should the stored data only contain the information that was provided in
	 * this update call.
	 * <p>
	 * The terms (key value pairs) found in collectedTerms should be stored in such a way so they
	 * can be used to filter data when reading.
	 * <p>
	 * The links found in linkList should stored in such a way so they can be used in the
	 * {@link #readLinkList(String, String)} method to retreive all records that has links pointing
	 * to a specified record.
	 * <p>
	 * If no record matching type and id is found MUST a {@link RecordNotFoundException} be thrown,
	 * indicating that the record to update can not be found.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @param dataRecord
	 *            A {@link DataGroup} with the records data
	 * @param storageTerms
	 *            A list of {@link StorageTerm} containg the storageTerms for the record.
	 * @param links
	 *            A {@link DataGroup} with a list of records that this record has links to
	 * @param dataDivider
	 *            A String representing the system the record belongs to.
	 */
	void update(String type, String id, DataGroup dataRecord, List<StorageTerm> storageTerms,
			List<Link> links, String dataDivider);

	/**
	 * readList should return, from storage, the records that has the corresponding type and matches
	 * the provided filter.
	 * <p>
	 * If a filter is specified should the returned records only be those which match the
	 * filter.<br>
	 * Filter information is based on the collectedTerms / storageTerms entered together with the
	 * record when creating or updating the record. If the filter contains include or exclude
	 * information should only those records that match the include and exclude parameters be
	 * returned.<br>
	 * If the filter specifies a specific range of records to return, should only the records that
	 * are inside the specified range and match any specified filter be returned.
	 * </p>
	 * If no records are found should a StorageReadResult with no results be returned.
	 * 
	 * @param types
	 *            A list of strings with the types of records to read.
	 * @param filter
	 *            A {@link DataGroup} with filter information about which subset of records to
	 *            return.
	 * @return A StorageReadResult with the records that exist in storage for the specified types
	 *         and filter
	 */
	StorageReadResult readList(List<String> types, DataGroup filter);

	/**
	 * recordExistsForListOfImplementingRecordTypesAndRecordId returns true if a record exists in
	 * storage for one of the specified types and the specified id.
	 * </p>
	 * The list of types is intended to be used in two ways.
	 * <ol>
	 * <li>A single implementing type</li>
	 * <li>A list of all implenting types, if the type looked for is abstract</li>
	 * </ol>
	 * 
	 * @param types
	 *            A List<String> with a list of recordTypes to see if any one of them have the
	 *            requested id.
	 * 
	 * @param id
	 *            A string with the id of the record to be found.
	 * @return A boolean wether the the record id combined with any of the types is found or not.
	 */
	boolean recordExistsForListOfImplementingRecordTypesAndRecordId(List<String> types, String id);

	/**
	 * getLinksToRecord returns a collection of all links from other records, pointing to the record
	 * specified by type and id.
	 * 
	 * </p>
	 * If no links are found should an empty collection be returned.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @return
	 */
	Collection<Link> getLinksToRecord(String type, String id);

	/**
	 * getTotalNumberOfRecordsForTypes should return the number of records that are stored under the
	 * specified types.
	 * <p>
	 * If a filter is specified the total number of records should reflect only those which match
	 * the filter. Filter information is based on the collectedTerms / storageTerms entered together
	 * with the record when creating or updating the record.<br>
	 * If the filter specifies a specific range of records to return, the range should be ignored
	 * and the returned total number of records should be the total number of records stored for the
	 * type that match the provided filter.<br>
	 * If the filter contains no include or exclude information should all records be counted.
	 * <p>
	 * If the requested type does not exist MUST a {@link RecordNotFoundException} be thrown,
	 * indicating that the requested type of records can not be found.
	 * 
	 * @param types
	 *            A List of strings with the record type
	 * @param filter
	 *            A {@link DataGroup} with filter information about which subset of records to
	 *            count.
	 * @return a long with the number of records that exist in storage for the specified type and
	 *         filter
	 */
	long getTotalNumberOfRecordsForTypes(List<String> types, DataGroup filter);

}
