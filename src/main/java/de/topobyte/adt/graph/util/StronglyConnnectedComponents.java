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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;

public class StronglyConnnectedComponents
{

	public static <T> Set<Set<T>> find(Graph<T> graph)
	{
		return findKosaraju(graph);
	}

	public static <T> Set<Set<T>> findKosaraju(Graph<T> graph)
	{
		Set<Set<T>> components = new HashSet<>();

		EnumerationBuilder<T> enumerationBuilder = new DepthFirstPostOrderEnumerationBuilder<>(
				graph);
		List<T> list = enumerationBuilder.buildEnumeration();
		Collections.reverse(list);

		Set<T> assigned = new HashSet<>();

		for (T element : list) {
			if (assigned.contains(element)) {
				continue;
			}
			Set<T> current = new HashSet<>();
			components.add(current);
			assign(graph, assigned, current, element);
		}

		return components;
	}

	private static <T> void assign(Graph<T> graph, Set<T> assigned,
			Set<T> current, T element)
	{
		current.add(element);
		assigned.add(element);
		for (T in : graph.getEdgesIn(element)) {
			if (assigned.contains(in)) {
				continue;
			}
			assign(graph, assigned, current, in);
		}
	}

}
