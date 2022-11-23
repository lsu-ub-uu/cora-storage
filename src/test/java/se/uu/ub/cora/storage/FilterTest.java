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
package se.uu.ub.cora.storage;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FilterTest {

	private Filter filter;
	private Part part;

	@BeforeMethod
	public void beforeMethod() {
		filter = new Filter();
		part = new Part();
	}

	@Test
	public void testNoFilterResults() throws Exception {
		boolean filterResults = filter.filtersResults();

		assertFalse(filterResults);
	}

	@Test
	public void testFromNoSet() throws Exception {
		filter.fromNo = 10;

		boolean filterResults = filter.filtersResults();

		assertTrue(filterResults);
	}

	@Test
	public void testToNoSet() throws Exception {
		filter.toNo = 10;

		boolean filterResults = filter.filtersResults();

		assertTrue(filterResults);
	}

	@Test
	public void testIncludeSet() throws Exception {
		filter.include = List.of(part);

		boolean filterResults = filter.filtersResults();

		assertTrue(filterResults);
	}

	@Test
	public void testExcludeSet() throws Exception {
		filter.exclude = List.of(part);

		boolean filterResults = filter.filtersResults();

		assertTrue(filterResults);
	}

	@Test
	public void testFromNoIsDefault() throws Exception {
		assertTrue(filter.fromNoIsDefault());
	}

	@Test
	public void testFromNoIsNotDefault() throws Exception {
		filter.fromNo = 100;

		assertFalse(filter.fromNoIsDefault());
	}

	@Test
	public void testToNoIsDefault() throws Exception {
		assertTrue(filter.toNoIsDefault());
	}

	@Test
	public void testToNoIsNotDefault() throws Exception {
		filter.toNo = 200;

		assertFalse(filter.toNoIsDefault());
	}

	@Test
	public void testHasIncludeParts() throws Exception {
		assertFalse(filter.hasIncludeParts());
	}

	@Test
	public void testHasIncludePartsForAddedPart() throws Exception {
		filter.include.add(new Part());

		assertTrue(filter.hasIncludeParts());
	}
}
