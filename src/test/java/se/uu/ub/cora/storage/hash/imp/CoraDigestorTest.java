/*
 * Copyright 2025 Uppsala University Library
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
package se.uu.ub.cora.storage.hash.imp;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.storage.StorageException;
import se.uu.ub.cora.storage.hash.CoraDigestor;

public class CoraDigestorTest {
	CoraDigestor digestor;

	@BeforeMethod
	private void beforeMethod() {
		digestor = new CoraDigestorImp();
	}

	@Test
	public void testSha256Hex() {
		String someStringHasehd = "6f0416d8003de967a2af13a93f47aaeded2885e4b6911690d84c384b80a6e56f";
		String someOtherStringHashed = "74532669416912cce813472ed0d7c95a59c85ed470f488a72c9fc50518430b2a";

		String hashedString = digestor.stringToSha256Hex("someString");
		assertEquals(hashedString, someStringHasehd);

		String hashedOtherString = digestor.stringToSha256Hex("someOtherString");
		assertEquals(hashedOtherString, someOtherStringHashed);
	}

	@Test
	public void testOnlForTestSetSha256Algorithm() {
		String nonExistingAlgorithm = "someAlgorithm";
		((CoraDigestorImp) digestor).onlyForTestSetSha256Algorithm(nonExistingAlgorithm);

		try {
			digestor.stringToSha256Hex("someString");

			fail("It should fail");
		} catch (Exception e) {
			assertTrue(e instanceof StorageException);
			assertEquals(e.getMessage(), "Failed to get " + nonExistingAlgorithm + " algorithm");
			assertEquals(e.getCause().getMessage(),
					nonExistingAlgorithm + " MessageDigest not available");
		}
	}

}
