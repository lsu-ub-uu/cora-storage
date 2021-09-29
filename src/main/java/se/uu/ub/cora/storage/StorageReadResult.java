/*
 * Copyright 2017, 2018 Uppsala University Library
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

import java.util.List;

import se.uu.ub.cora.data.DataGroup;

/**
 * StorageReadResult contains the result of a read from storage. It consists of a list with the
 * requested records, a number start which indicate where this lists first record is in the list of
 * matches if a subset of all matches where requested and a number with the total number of matches
 * that the read request generated from storage.
 */
public final class StorageReadResult {
	/**
	 * start is the number where this lists first record is in the list of matches if a subset of
	 * all matches where requested. Counted such as the first match is generates a start of 0.
	 */
	public long start;

	/**
	 * totalNumberOfMatches contains the total number of records in storage for the recordType,
	 * matching any provided filter, ignoring fromNo and toNo.
	 */
	public long totalNumberOfMatches;

	/**
	 * listOfDataGroups is a list records that match the request that has been made
	 */
	public List<DataGroup> listOfDataGroups;

}
