/*
 * Copyright 2023, 2024 Uppsala University Library
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

import se.uu.ub.cora.storage.StorageException;

public interface ArchivePathBuilder {

	/**
	 * buildPathToAResourceInArchive method builds a path to a resource stored in archive using
	 * type, id and datadivider.
	 * <p>
	 * It can throws ImageConverterException if an error ocurr.
	 * 
	 * @param dataDivider
	 * @param type
	 * @param id
	 * 
	 * @throws StorageException
	 *             if any problems.
	 * 
	 * @return
	 */
	String buildPathToAResourceInArchive(String dataDivider, String type, String id);
}
