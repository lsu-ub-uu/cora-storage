/*
 * Copyright 2016, 2024 Uppsala University Library
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

import java.io.InputStream;

/**
 * RecordStorage is the interface that defines how streams are stored and retreived from a Cora
 * system. This interface makes the stream storage implementation decoupled from the rest of the
 * system enabling different stream storage solutions to be developed and used depending on the
 * needs of the current system.
 */

public interface StreamStorage {

	/**
	 * Stores a stream in storage based on the provided streamId and data divider.
	 *
	 * @param streamId
	 *            The unique identifier of the stream.
	 * @param dataDivider
	 *            The divider used to organize data within the storage.
	 * @param inputStreamm
	 *            The InputStream containing the data to be stored.
	 * 
	 * @throws RuntimeException
	 *             if any exception occurs during the storage process.
	 */

	long store(String streamId, String dataDivider, InputStream stream);

	/**
	 * Retrieves a stream from storage based on the provided streamId and data divider.
	 *
	 * @param streamId
	 *            The unique identifier of the stream.
	 * @param dataDivider
	 *            The divider used to organize data within the storage.
	 * @return An {@link InputStream} representing the retrieved data.
	 * 
	 * @throws RuntimeException
	 *             if the stream does not exist or if any exception occurs on the retrieval.
	 */
	InputStream retrieve(String streamId, String dataDivider);

	/**
	 * Deletes a stream from storage based on the provided streamId and data divider.
	 *
	 * @param streamId
	 *            The unique identifier of the stream.
	 * @param dataDivider
	 *            The divider used to organize data within the storage.
	 * @throws RuntimeException
	 *             if the stream does not exist or if any exception occurs on the retrieval.
	 */
	void delete(String streamId, String dataDivider);
}
