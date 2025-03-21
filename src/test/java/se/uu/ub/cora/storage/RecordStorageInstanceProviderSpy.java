/*
 * Copyright 2022, 2025 Uppsala University Library
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

import java.util.function.Supplier;

import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class RecordStorageInstanceProviderSpy implements RecordStorageInstanceProvider {

	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();

	public RecordStorageInstanceProviderSpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("getOrderToSelectImplementionsBy",
				(Supplier<Integer>) () -> 0);
		MRV.setDefaultReturnValuesSupplier("getRecordStorage", RecordStorageSpy::new);

	}

	@Override
	public int getOrderToSelectImplementionsBy() {
		return (int) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public RecordStorage getRecordStorage() {
		return (RecordStorage) MCR.addCallAndReturnFromMRV();
	}

	@Override
	public void dataChanged(String type, String id, String action) {
		MCR.addCall("type", type, "id", id, "action", action);
	}
}
