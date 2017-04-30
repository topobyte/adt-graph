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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.primitives.Ints;

import de.topobyte.adt.graph.Graph;

public class TestStronlgyConnectedComponents
{

	@Test
	public void test1()
	{
		Graph<Integer> graph = new Graph<>();
		for (int i = 1; i <= 10; i++) {
			graph.addNode(i);
		}

		addCircle(graph, Ints.asList(1, 2, 3));
		addCircle(graph, Ints.asList(4, 5));
		addCircle(graph, Ints.asList(8, 9, 10));
		addChain(graph, Ints.asList(3, 7, 8));
		addChain(graph, Ints.asList(2, 4));

		Set<Set<Integer>> expected = new HashSet<>();
		expected.add(new HashSet<>(Ints.asList(1, 2, 3)));
		expected.add(new HashSet<>(Ints.asList(4, 5)));
		expected.add(new HashSet<>(Ints.asList(6)));
		expected.add(new HashSet<>(Ints.asList(7)));
		expected.add(new HashSet<>(Ints.asList(8, 9, 10)));

		Set<Set<Integer>> components = StronglyConnnectedComponents.find(graph);

		Assert.assertEquals(expected, components);
	}

	@Test
	public void test2()
	{
		Graph<Integer> graph = new Graph<>();
		for (int i = 1; i <= 6; i++) {
			graph.addNode(i);
		}

		addCircle(graph, Ints.asList(1, 2, 3));
		addCircle(graph, Ints.asList(4, 5, 6));

		addChain(graph, Ints.asList(2, 4));
		addChain(graph, Ints.asList(3, 5));

		Set<Set<Integer>> expected = new HashSet<>();
		expected.add(new HashSet<>(Ints.asList(1, 2, 3)));
		expected.add(new HashSet<>(Ints.asList(4, 5, 6)));

		Set<Set<Integer>> components = StronglyConnnectedComponents.find(graph);

		Assert.assertEquals(expected, components);
	}

	private void addChain(Graph<Integer> graph, List<Integer> ints)
	{
		for (int i = 0; i < ints.size() - 1; i++) {
			graph.addEdge(ints.get(i), ints.get(i + 1));
		}
	}

	private void addCircle(Graph<Integer> graph, List<Integer> ints)
	{
		for (int i = 0; i < ints.size() - 1; i++) {
			graph.addEdge(ints.get(i), ints.get(i + 1));
		}
		graph.addEdge(ints.get(ints.size() - 1), ints.get(0));
	}

}
