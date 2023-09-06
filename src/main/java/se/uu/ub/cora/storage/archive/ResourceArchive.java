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

import se.uu.ub.cora.storage.ResourceConflictException;
import se.uu.ub.cora.storage.ResourceNotFoundException;

/**
 * ResourceArchive is the interface that defines how resources are stored and retreived from a Cora
 * systems archive. This interface makes the archiving implementation decoupled from the rest of the
 * system enabling different archive solutions to be developed and used depending on the needs of
 * the current system.
 * <p>
 * Instances of ResourceArchive SHOULD be accessed through
 * {@link ResourceArchiveProvider#getResourceArchive()} for each thread that needs access to
 * ResourceArchive. Implementations of ResourceArchive SHOULD clearly state if they are threadsafe
 * or not, to enable ResourceArchiveProvider to return the same or new instances as needed.
 */
public interface ResourceArchive {

	/**
	 * create stores the resource in the archive.
	 * <p>
	 * If a resource matching type and id already exists in the archive MUST a
	 * {@link ResourceConflictException} be thrown, indicating that the requested resource can not
	 * be created.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param dataDivider
	 *            A String with the resource's data divider
	 * @param type
	 *            A String with the resource type
	 * @param id
	 *            A String with the resource id
	 * @param resource
	 *            A {@link InputStream} with the resource's data
	 * @param mimeType
	 *            String with the MIME type of the resource
	 * @return A long with resource file size
	 */
	long create(String dataDivider, String type, String id, InputStream resource, String mimeType);

	/**
	 * read the resource from the archive
	 * 
	 * <p>
	 * If a resource matching type and id does not exist in the archive MUST a
	 * {@link ResourceNotFoundException} be thrown, indicating that the requested resource can not
	 * be read.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param dataDivider
	 *            A String with the resource's data divider
	 * @param type
	 *            A String with the resource type
	 * @param id
	 *            A String with the reource id
	 * @return An InputStream with the requested resource
	 */
	InputStream read(String dataDivider, String type, String id);

	/**
	 * update stores the resource in the archive.
	 * <p>
	 * If a resource matching type and id does not exist in the archive MUST a
	 * {@link ResourceNotFoundException} be thrown, indicating that the requested resource can not
	 * be updated.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param dataDivider
	 *            A String with the resource's data divider
	 * @param type
	 *            A String with the resource type
	 * @param id
	 *            A String with the resource id
	 * @param resource
	 *            A {@link InputStream} with the resource data
	 * @param mimeType
	 *            String with the MIME type of the resource
	 */
	void update(String dataDivider, String type, String id, InputStream resource, String mimeType);

	/**
	 * delete removes a resource from the archive
	 * 
	 * <p>
	 * If a resource matching type and id does not exist in the archive MUST a
	 * {@link ResourceNotFoundException} be thrown, indicating that the requested resource can not
	 * be deleted.
	 * <p>
	 * Any other errors MUST throw an {@link ArchiveException}
	 * 
	 * @param dataDivider
	 *            A String with the resource's data divider
	 * @param type
	 *            A String with the resource type
	 * @param id
	 *            A String with the resource id
	 */
	void delete(String dataDivider, String type, String id);
}
