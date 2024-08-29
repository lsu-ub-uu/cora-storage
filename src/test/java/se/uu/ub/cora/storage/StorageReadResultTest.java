/*
 * Copyright 2019, 2024 Uppsala University Library
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataRecordGroup;

public class StorageReadResultTest {

	private StorageReadResult readResult;

	@BeforeMethod
	public void beforeMethod() {
		readResult = new StorageReadResult();
	}

	@Test
	public void testListOfDataGroupsInitializedWithEmptyArrayList() {
		List<DataGroup> listOfDataGroups = readResult.listOfDataGroups;
		assertNotNull(listOfDataGroups);
		assertTrue(listOfDataGroups instanceof ArrayList<DataGroup>);
	}

	@Test
	public void testListOfDataGroups() {
		List<DataGroup> listOfDataGroups = new ArrayList<>();
		readResult.listOfDataGroups = listOfDataGroups;
		assertEquals(readResult.listOfDataGroups, listOfDataGroups);
	}

	@Test
	public void testListOfDataRecordGroupsInitializedWithEmptyArrayList() {
		List<DataRecordGroup> listOfDataRecordGroups = readResult.listOfDataRecordGroups;
		assertNotNull(listOfDataRecordGroups);
		assertTrue(listOfDataRecordGroups instanceof ArrayList<DataRecordGroup>);
	}

	@Test
	public void testListOfDataRecordGroups() {
		List<DataRecordGroup> listOfDataRecordGroups = new ArrayList<>();
		readResult.listOfDataRecordGroups = listOfDataRecordGroups;
		assertEquals(readResult.listOfDataRecordGroups, listOfDataRecordGroups);
	}

	@Test
	public void testTotalNumberOfMatches() {
		readResult.totalNumberOfMatches = 0;
		assertEquals(readResult.totalNumberOfMatches, 0);
	}

	@Test
	public void testStart() {
		readResult.start = 0;
		assertEquals(readResult.start, 0);
	}
}
