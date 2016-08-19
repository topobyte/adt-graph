// Copyright 2016 Sebastian Kuerten
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 *         Build an enumeration of the graph's nodes in topological order.
 * 
 * @param <T>
 *            type of nodes in the graph.
 */
public class TopologicalEnumerationBuilder<T> implements EnumerationBuilder<T>
{

	@Override
	public List<T> buildEnumeration()
	{
		build();
		return getEnumeration();
	}

	Graph<T> graph;

	Set<T> enumerated = new HashSet<>();
	List<T> enumeration = new ArrayList<>();
	Set<T> available = new HashSet<>();

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 */
	public TopologicalEnumerationBuilder(Graph<T> graph)
	{
		this.graph = graph;
	}

	@Override
	public List<T> getEnumeration()
	{
		return enumeration;
	}

	private void build()
	{
		Collection<T> nodes = graph.getNodes();
		for (T node : nodes) {
			available.add(node);
		}

		while (!available.isEmpty()) {
			T n = available.iterator().next();
			available.remove(n);
			visit(n);
		}

		Collections.reverse(enumeration);
	}

	private Set<T> mark = new HashSet<>();

	private void visit(T n)
	{
		if (mark.contains(n)) {
			throw new RuntimeException("This graph is not acyclic");
		}
		if (enumerated.contains(n)) {
			return;
		}
		mark.add(n);
		for (T neighbor : graph.getEdgesOut(n)) {
			visit(neighbor);
		}
		enumeration.add(n);
		enumerated.add(n);
		mark.remove(n);
	}

}
