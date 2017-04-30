// Copyright 2017 Sebastian Kuerten
//
// This file is part of adt-graph.
//
// adt-graph is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// adt-graph is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with adt-graph. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.adt.graph.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.TestUtil;

public class TestBreadtFirstEnumeration
{

	@Test
	public void test()
	{
		for (int nLevels = 1; nLevels <= 8; nLevels++) {
			test(nLevels);
		}
	}

	public void test(int nLevels)
	{
		int numNodes = (1 << nLevels) - 1;

		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		EnumerationBuilder<Integer> enumerationBuilder = new BreadthFirstEnumerationBuilder<>(
				graph);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		List<Integer> expected = TestUtil.ascending(1, numNodes);

		Assert.assertEquals(expected, enumeration);
	}

}
