/*
 * Copyright 2021 Uppsala University Library
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

public final class StorageException extends RuntimeException {
	private static final long serialVersionUID = 72005255019256838L;

	public static StorageException withMessage(String message) {
		return new StorageException(message);
	}

	public static StorageException withMessageAndException(String message, Exception e) {
		return new StorageException(message, e);
	}

	private StorageException(String message) {
		super(message);
	}

	private StorageException(String message, Exception e) {
		super(message, e);
	}
}
