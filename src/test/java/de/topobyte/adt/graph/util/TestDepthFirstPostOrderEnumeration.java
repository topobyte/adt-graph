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

import com.google.common.primitives.Ints;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.TestUtil;

public class TestDepthFirstPostOrderEnumeration
{

	@Test
	public void test()
	{
		List<Integer> expected11 = Ints.asList(1);
		List<Integer> expected12 = Ints.asList(2, 3, 1);
		List<Integer> expected13 = Ints.asList(4, 5, 2, 6, 7, 3, 1);
		List<Integer> expected14 = Ints.asList(8, 9, 4, 10, 11, 5, 2, 12, 13, 6,
				14, 15, 7, 3, 1);

		test1(1, expected11);
		test1(2, expected12);
		test1(3, expected13);
		test1(4, expected14);

		List<Integer> expected31 = Ints.asList(1);
		List<Integer> expected32 = TestUtil.descending(3, 1);
		List<Integer> expected33 = TestUtil.descending(7, 1);
		List<Integer> expected34 = TestUtil.descending(15, 1);

		test2(1, expected31);
		test2(2, expected32);
		test2(3, expected33);
		test2(4, expected34);
	}

	public void test1(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		DepthFirstPostOrderEnumerationBuilder<Integer> enumerationBuilder = new DepthFirstPostOrderEnumerationBuilder<>(
				graph, TestUtil.TREE_SET_FACTORY);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

	public void test2(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		DepthFirstPostOrderEnumerationBuilder<Integer> enumerationBuilder = new DepthFirstPostOrderEnumerationBuilder<>(
				graph, TestUtil.REVERSE_ORDER_TREE_SET_FACTORY);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

}
