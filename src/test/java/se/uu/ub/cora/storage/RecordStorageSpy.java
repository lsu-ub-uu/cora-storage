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

import java.util.List;
import java.util.Set;

import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataRecordGroup;
import se.uu.ub.cora.data.collected.Link;
import se.uu.ub.cora.data.collected.StorageTerm;

public class RecordStorageSpy implements RecordStorage {

	@Override
	public DataGroup read(List<String> types, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(String type, String id, DataGroup dataRecord, Set<StorageTerm> storageTerms,
			Set<Link> links, String dataDivider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteByTypeAndId(String type, String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean linksExistForRecord(String type, String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(String type, String id, DataGroup dataRecord, Set<StorageTerm> storageTerms,
			Set<Link> links, String dataDivider) {
		// TODO Auto-generated method stub

	}

	@Override
	public StorageReadResult readList(List<String> types, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean recordExists(List<String> types, String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Link> getLinksToRecord(String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalNumberOfRecordsForTypes(List<String> types, Filter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataRecordGroup read(String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StorageReadResult readList(String type, Filter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Link> getLinksFromRecord(String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<StorageTerm> getStorageTermsForRecord(String type, String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
