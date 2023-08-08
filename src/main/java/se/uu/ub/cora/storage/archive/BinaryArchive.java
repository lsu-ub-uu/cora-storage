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

import java.io.InputStream;

import se.uu.ub.cora.storage.RecordConflictException;
import se.uu.ub.cora.storage.RecordNotFoundException;

/**
 * BinaryArchive is the interface that defines how records are stored and retreived from a Cora
 * systems archive. This interface makes the archiving implementation decoupled from the rest of the
 * system enabling different archive solutions to be developed and used depending on the needs of
 * the current system.
 * <p>
 * Instances of BinaryArchive SHOULD be accessed through
 * {@link BinaryArchiveProvider#getBinaryArchive()} for each thread that needs access to
 * BinaryArchive. Implementations of BinaryArchive SHOULD clearly state if they are threadsafe or
 * not, to enable BinaryArchiveProvider to return the same or new instances as needed.
 */
public interface BinaryArchive {

	/**
	 * create stores the binary in the archive.
	 * <p>
	 * If a binary matching type and id already exists in the archive MUST a
	 * {@link RecordConflictException} be thrown, indicating that the requested binary can not be
	 * created.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param type
	 *            A String with the binary's type
	 * @param id
	 *            A String with the binary's id
	 * @param binary
	 *            A {@link InputStream} with the binary's data
	 */
	void create(String type, String id, InputStream binary);

	/**
	 * update stores the binary in the archive.
	 * <p>
	 * If a binary matching type and id does not exist in the archive MUST a
	 * {@link RecordNotFoundException} be thrown, indicating that the requested binary can not be
	 * updated.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param type
	 *            A String with the binary's type
	 * @param id
	 *            A String with the binary's id
	 * @param binary
	 *            A {@link InputStream} with the binary's data
	 */
	void update(String type, String id, InputStream binary);

	/**
	 * Read the binary from the archive
	 * 
	 * @param id
	 *            A String with the binary's id
	 */
	void read(String type, String id);

	void delete(String type, String id);
}
