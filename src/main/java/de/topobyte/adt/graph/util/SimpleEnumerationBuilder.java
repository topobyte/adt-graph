// Copyright 2015 Sebastian Kuerten
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

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 *         Build an enumeration of the graph's nodes by choosing a neighbour of
 *         the previously encountered nodes in each iteration.
 * 
 * @param <T>
 *            type of nodes in the graph.
 */
public class SimpleEnumerationBuilder<T> implements EnumerationBuilder<T>
{

	@Override
	public List<T> buildEnumeration()
	{
		build();
		return getEnumeration();
	}

	Graph<T> graph;

	Set<T> enumerated = new HashSet<T>();
	List<T> enumeration = new ArrayList<T>();
	Set<T> available = new HashSet<T>();

	/**
	 * Create a new EnumerationBuilder for the denoted graph {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 */
	public SimpleEnumerationBuilder(Graph<T> graph)
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

		while (true) {
			if (available.size() == 0) {
				break;
			}
			T n = available.iterator().next();
			// System.out.println("a: " + n);
			enumerate(n);
			Set<T> neighbours = new HashSet<T>();
			addNeighbours(neighbours, n);
			while (true) {
				if (neighbours.size() == 0) {
					break;
				}
				T next = chooseNext(neighbours);
				neighbours.remove(next);
				// System.out.println("b: " + next);
				enumerate(next);
				addNeighbours(neighbours, next);
			}
		}
	}

	private T chooseNext(Set<T> neighbours)
	{
		T next = neighbours.iterator().next();
		return next;
	}

	private void addNeighbours(Set<T> neighbours, T n)
	{
		Set<T> nsNeighbours = graph.getEdgesOut(n);
		for (T neighbour : nsNeighbours) {
			if (enumerated.contains(neighbour)) {
				continue;
			}
			if (neighbours.contains(neighbour)) {
				continue;
			}
			neighbours.add(neighbour);
		}
	}

	// int i = 0;

	private void enumerate(T n)
	{
		// i++;
		// System.out.println("enumerated: " + i);
		// System.out.println(available.size());
		available.remove(n);
		enumerated.add(n);
		enumeration.add(n);
	}

}
