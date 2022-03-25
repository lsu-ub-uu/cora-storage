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
package se.uu.ub.cora.storage.archive;

import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.storage.RecordConflictException;

/**
 * RecordArchive is the interface that defines how records are stored and retreived from a Cora
 * systems archive. This interface makes the archiving implementation decoupled from the rest of the
 * system enabling different archive solutions to be developed and used depending on the needs of
 * the current system.
 * <p>
 * Instances of RecordArchive SHOULD be accessed through
 * {@link RecordArchiveProvider#getRecordArchive()} for each thread that needs access to
 * RecordArchive. Implementations of RecordArchive SHOULD clearly state if they are threadsafe or
 * not, to enable RecordArchiveProvider to return the same or new instances as needed.
 */
public interface RecordArchive {

	/**
	 * create stores the record in the archive.
	 * <p>
	 * If a record matching type and id already exists in storage a {@link RecordConflictException}
	 * is thrown, indicating that the requested record can not be created.
	 * 
	 * @param type
	 *            A String with the records type
	 * @param id
	 *            A String with the records id
	 * @param dataRecord
	 *            A {@link DataGroup} with the records data
	 */
	void create(String type, String id, DataGroup dataRecord);
}
