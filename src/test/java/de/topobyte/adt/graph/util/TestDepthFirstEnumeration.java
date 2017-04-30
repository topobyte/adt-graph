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

public class TestDepthFirstEnumeration
{

	@Test
	public void test()
	{
		List<Integer> expected11 = Ints.asList(1);
		List<Integer> expected12 = Ints.asList(1, 3, 2);
		List<Integer> expected13 = Ints.asList(1, 3, 7, 6, 2, 5, 4);
		List<Integer> expected14 = Ints.asList(1, 3, 7, 15, 14, 6, 13, 12, 2, 5,
				11, 10, 4, 9, 8);

		test1(1, expected11);
		test1(2, expected12);
		test1(3, expected13);
		test1(4, expected14);

		List<Integer> expected21 = Ints.asList(1);
		List<Integer> expected22 = Ints.asList(1, 2, 3);
		List<Integer> expected23 = Ints.asList(1, 2, 4, 5, 3, 6, 7);
		List<Integer> expected24 = Ints.asList(1, 2, 4, 8, 9, 5, 10, 11, 3, 6,
				12, 13, 7, 14, 15);

		test2(1, expected21);
		test2(2, expected22);
		test2(3, expected23);
		test2(4, expected24);

		List<Integer> expected31 = Ints.asList(1);
		List<Integer> expected32 = TestUtil.descending(3, 1);
		List<Integer> expected33 = TestUtil.descending(7, 1);
		List<Integer> expected34 = TestUtil.descending(15, 1);

		test3(1, expected31);
		test3(2, expected32);
		test3(3, expected33);
		test3(4, expected34);
	}

	public void test1(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		DepthFirstEnumerationBuilder<Integer> enumerationBuilder = new DepthFirstEnumerationBuilder<>(
				graph, TestUtil.TREE_SET_FACTORY);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

	public void test2(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		DepthFirstEnumerationBuilder<Integer> enumerationBuilder = new DepthFirstEnumerationBuilder<>(
				graph, TestUtil.TREE_SET_FACTORY);
		enumerationBuilder.invertOrderOfNewNeighbors = true;
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

	public void test3(int nLevels, List<Integer> expected)
	{
		Graph<Integer> graph = TestUtil.createBinaryTree(nLevels);

		DepthFirstEnumerationBuilder<Integer> enumerationBuilder = new DepthFirstEnumerationBuilder<>(
				graph, TestUtil.REVERSE_ORDER_TREE_SET_FACTORY);
		List<Integer> enumeration = enumerationBuilder.buildEnumeration();

		Assert.assertEquals(expected, enumeration);
	}

}
