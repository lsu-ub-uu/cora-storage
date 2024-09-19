/*
 * Copyright 2019 Uppsala University Library
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
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.logger.spies.LoggerFactorySpy;
import se.uu.ub.cora.logger.spies.LoggerSpy;
import se.uu.ub.cora.storage.idgenerator.RecordIdGenerator;

public class TimeStampIdGeneratorProviderTest {
	private Map<String, String> initInfo = new HashMap<>();
	private LoggerFactorySpy loggerFactorySpy;
	private TimeStampIdGeneratorProvider timeStampIdGeneratorProvider;

	@BeforeMethod
	public void beforeMethod() throws Exception {
		loggerFactorySpy = new LoggerFactorySpy();
		LoggerProvider.setLoggerFactory(loggerFactorySpy);
		initInfo = new HashMap<>();
		timeStampIdGeneratorProvider = new TimeStampIdGeneratorProvider();
	}

	@Test
	public void testGetOrderToSelectImplementationsByIsZero() {
		assertEquals(timeStampIdGeneratorProvider.getOrderToSelectImplementionsBy(), 0);
	}

	@Test
	public void testNormalStartupReturnsTimeStampIdGenerator() {
		timeStampIdGeneratorProvider.startUsingInitInfo(initInfo);
		RecordIdGenerator recordIdGenerator = timeStampIdGeneratorProvider.getRecordIdGenerator();
		assertTrue(recordIdGenerator instanceof TimeStampIdGenerator);
	}

	@Test
	public void testNormalStartupReturnsTheSameStreamStorageForMultipleCalls() {
		timeStampIdGeneratorProvider.startUsingInitInfo(initInfo);
		RecordIdGenerator recordIdGenerator = timeStampIdGeneratorProvider.getRecordIdGenerator();
		RecordIdGenerator recordIdGenerator2 = timeStampIdGeneratorProvider.getRecordIdGenerator();
		assertSame(recordIdGenerator, recordIdGenerator2);
	}

	@Test
	public void testLoggingNormalStartup() {
		timeStampIdGeneratorProvider.startUsingInitInfo(initInfo);
		LoggerSpy loggerSpy = (LoggerSpy) loggerFactorySpy.MCR.getReturnValue("factorForClass", 0);

		loggerSpy.MCR.assertNumberOfCallsToMethod("logInfoUsingMessage", 2);
		loggerSpy.MCR.assertParameters("logInfoUsingMessage", 0,
				"TimeStampIdGeneratorProvider starting TimeStampIdGenerator...");
		loggerSpy.MCR.assertParameters("logInfoUsingMessage", 1,
				"TimeStampIdGeneratorProvider started TimeStampIdGenerator");
	}

}
