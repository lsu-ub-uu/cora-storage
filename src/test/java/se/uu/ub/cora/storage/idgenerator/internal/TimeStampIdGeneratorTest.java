/*
 * Copyright 2015 Uppsala University Library
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

package se.uu.ub.cora.storage.idgenerator.internal;

import static org.testng.Assert.assertEquals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.testng.Assert;
import org.testng.annotations.Test;

import se.uu.ub.cora.storage.idgenerator.RecordIdGenerator;

public class TimeStampIdGeneratorTest {
	@Test
	public void testGenerateId() {
		RecordIdGenerator idGenerator = new TimeStampIdGenerator();
		String keyType = idGenerator.getIdForType("type");
		String keyType2 = idGenerator.getIdForType("type2");
		Assert.assertNotEquals(keyType, keyType2,
				"The generated keys should not be equal for two different types");
	}

	@Test
	public void testGetIdForTypeWithSimultaneousVirtualThreads() throws InterruptedException {
		TimeStampIdGenerator generator = new TimeStampIdGenerator();
		int numberOfexecs = 1000;
		int threadCount = 1000;
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch doneLatch = new CountDownLatch(threadCount);
		Map<String, String> ids = new ConcurrentHashMap<>();

		for (int i = 0; i < threadCount; i++) {
			Thread.startVirtualThread(() -> {
				try {
					startLatch.await(); // Wait for the starting signal
					int execs = numberOfexecs;
					while (execs != 0) {
						String id = generator.getIdForType("type");
						ids.put(id, id);
						execs--;
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} finally {
					doneLatch.countDown();
				}
			});
		}

		System.out.println("Ready? " + ids.size());
		startLatch.countDown(); // Release all threads to start at the same time
		doneLatch.await(); // Wait for all threads to finish

		assertEquals(ids.size(), threadCount * numberOfexecs,
				"Size " + ids.size() + ", and diff: " + (threadCount * numberOfexecs - ids.size()));
	}

}
