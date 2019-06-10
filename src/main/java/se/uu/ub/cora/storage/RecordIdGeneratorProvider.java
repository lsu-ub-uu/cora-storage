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
package se.uu.ub.cora.storage;

import java.util.Map;

/**
 * RecordIdGeneratorProvider is used to provide ID generation for Records
 */
public interface RecordIdGeneratorProvider extends SelectOrder {

	/**
	 * startUsingInitInfo is expected to be called on system startup to allow implementing classes
	 * to startup the implementing RecordIdGenerator as needed
	 */
	void startUsingInitInfo(Map<String, String> initInfo);

	/**
	 * getRecordIdGenerator should be implemented in such a way that it returns a RecordIdGenerator
	 * that can be used by anything that needs access to generate IDs for Records. Multiple calls to
	 * getRecordIdGenerator should return instances or the same instance, depending on the
	 * implementation. It must be possible to use the currently returned instance without
	 * considering if other calls has been made to this method.
	 * 
	 * @return A RecordIdGenerator that gives access to new IDs for Records
	 */
	RecordIdGenerator getRecordIdGenerator();
}
