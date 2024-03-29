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

import java.util.Map;

import se.uu.ub.cora.logger.Logger;
import se.uu.ub.cora.logger.LoggerProvider;
import se.uu.ub.cora.storage.idgenerator.RecordIdGenerator;
import se.uu.ub.cora.storage.idgenerator.RecordIdGeneratorProvider;

public class TimeStampIdGeneratorProvider implements RecordIdGeneratorProvider {
	private Logger log = LoggerProvider.getLoggerForClass(TimeStampIdGeneratorProvider.class);
	private TimeStampIdGenerator timeStampIdGenerator;

	@Override
	public int getOrderToSelectImplementionsBy() {
		return 0;
	}

	@Override
	public void startUsingInitInfo(Map<String, String> initInfo) {
		log.logInfoUsingMessage("TimeStampIdGeneratorProvider starting TimeStampIdGenerator...");
		timeStampIdGenerator = new TimeStampIdGenerator();
		log.logInfoUsingMessage("TimeStampIdGeneratorProvider started TimeStampIdGenerator");
	}

	@Override
	public RecordIdGenerator getRecordIdGenerator() {
		return timeStampIdGenerator;
	}

}
