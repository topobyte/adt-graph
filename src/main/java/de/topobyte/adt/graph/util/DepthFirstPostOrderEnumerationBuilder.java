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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;

public class DepthFirstPostOrderEnumerationBuilder<T>
		implements EnumerationBuilder<T>
{

	Graph<T> graph;

	List<T> enumeration;
	Set<T> available;

	public DepthFirstPostOrderEnumerationBuilder(Graph<T> graph)
	{
		this.graph = graph;
		enumeration = new ArrayList<>();
		available = new HashSet<>();
	}

	@Override
	public List<T> buildEnumeration()
	{
		build();
		return getEnumeration();
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
			visit(graph, n);
		}
	}

	private void visit(Graph<T> graph, T n)
	{
		// first mark the node as visited
		available.remove(n);
		// then visit out nodes reachable via outgoing edges
		for (T out : graph.getEdgesOut(n)) {
			if (!available.contains(out)) {
				continue;
			}
			visit(graph, out);
		}
		// finally, add the node to the enumeration
		enumeration.add(n);
	}

}
