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

package de.topobyte.adt.graph;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.primitives.Ints;

import de.topobyte.adt.graph.util.DepthFirstEnumerationBuilder;
import de.topobyte.adt.graph.util.EnumerationBuilder;

public class TestDepthFirstEnumeration
{

	@Test
	public void test()
	{
		List<Integer> expected1 = Ints.asList(1);
		List<Integer> expected2 = Ints.asList(1, 3, 2);
		List<Integer> expected3 = Ints.asList(1, 3, 7, 6, 2, 5, 4);
		List<Integer> expected4 = Ints.asList(1, 3, 7, 15, 14, 6, 13, 12, 2, 5,
				11, 10, 4, 9, 8);

		test(1, expected1);
		test(2, expected2);
		test(3, expected3);
		test(4, expected4);
	}

	public void test(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		EnumerationBuilder<Integer> enumerationBuilder = new DepthFirstEnumerationBuilder<>(
				graph);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

}
