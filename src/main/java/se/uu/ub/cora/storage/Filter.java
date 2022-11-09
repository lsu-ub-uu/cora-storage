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

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Filter is a dataholder which holds data in order to filter out data from storage queries.
 * </p>
 * <i>fromNo</i> and <i>toNo</i> are used to define the range of matches to be filter from the
 * storage query. The matches outside this range will be excluded. Matches start with 1 and are
 * inclusive, so that 1-1 would result in returning only the first match. 1-10 would return the
 * first ten matches.
 * <ul>
 * <li>fromNo: A long that defines the first match position to be included on the query, 1 is the
 * first match.</li>
 * <li>toNo: A long that the defines the last match position to be included on the query.</li>
 * </ul>
 * 
 * There are two main settings to filter the results, include and exclude. Parts defined in include
 * are included in the result and things defined in exclude are excluded from the results. Include
 * and exclude are built using the same logic and are list of {@link Part}s.
 * </p>
 * 
 * A record is considered a match if it matches any condition constructed from the different parts,
 * therefore an OR is used between these conditions. Both include and exclude are composed of
 * {@link Part}s. These Parts defines a list of conditions which all must be true in order to match,
 * therefore an AND is used between these conditions.
 *
 * </p>
 * <u>Results are matches as follows:</u>
 * </p>
 * (include) AND NOT (exclude)
 * </p>
 * include and exclude is matched as:
 * </p>
 * (part OR part)
 * </p>
 * each part is:
 * </p>
 * (key operator value AND key operator value)
 * </p>
 * 
 * 
 */
public class Filter {
	public long fromNo = 1;
	public long toNo = Long.MAX_VALUE;
	public List<Part> include = new ArrayList<>();
	public List<Part> exclude = new ArrayList<>();

	/**
	 * filtersResults returns true if this filter is set to limit results, either by limiting the
	 * set of returned by only requesting a subset of results through fromNo and toNo, or by having
	 * at least one part in include or exclude. If no default value is changed is false returned.
	 */
	public boolean filtersResults() {
		return allValuesAreDefault();
	}

	private boolean allValuesAreDefault() {
		return fromNo != 1 || toNo != Long.MAX_VALUE || !include.isEmpty() || !exclude.isEmpty();
	}

}
