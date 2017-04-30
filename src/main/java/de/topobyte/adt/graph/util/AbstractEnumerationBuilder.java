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
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.HashSetFactory;
import de.topobyte.adt.graph.factories.SetFactory;

/**
 * A common base class for {@link BreadthFirstEnumerationBuilder} and
 * {@link DepthFirstEnumerationBuilder}.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 * @param <T>
 *            type of nodes in the graph.
 */
abstract class AbstractEnumerationBuilder<T> implements EnumerationBuilder<T>
{

	SetFactory<T> setFactory;
	Graph<T> graph;

	Set<T> enumerated;
	List<T> enumeration;
	Set<T> available;

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 */
	public AbstractEnumerationBuilder(Graph<T> graph)
	{
		this(graph, new HashSetFactory<T>());
	}

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 * @param setFactory
	 *            a factory to create sets for storing nodes with.
	 */
	public AbstractEnumerationBuilder(Graph<T> graph, SetFactory<T> setFactory)
	{
		this.graph = graph;
		this.setFactory = setFactory;
		init();
	}

	private void init()
	{
		enumerated = setFactory.create();
		enumeration = new ArrayList<>();
		available = setFactory.create();
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
			// System.out.println("a: " + n);
			enumerate(n);
			List<T> neighbours = createList();
			Set<T> neighbourSet = setFactory.create();
			addNeighbours(neighbours, neighbourSet, n);
			while (!neighbourSet.isEmpty()) {
				T next = chooseNext(neighbours, neighbourSet);
				// System.out.println("b: " + next);
				enumerate(next);
				addNeighbours(neighbours, neighbourSet, next);
			}
		}
	}

	protected abstract List<T> createList();

	protected abstract T chooseNext(List<T> neighbours, Set<T> neighbourSet);

	private void addNeighbours(List<T> neighbours, Set<T> neighbourSet, T n)
	{
		Set<T> nsNeighbours = graph.getEdgesOut(n);
		for (T neighbour : nsNeighbours) {
			if (enumerated.contains(neighbour)) {
				continue;
			}
			if (neighbourSet.contains(neighbour)) {
				continue;
			}
			neighbours.add(neighbour);
			neighbourSet.add(neighbour);
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
