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
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;
import de.topobyte.adt.graph.factories.SetFactory;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 * @param <T>
 *            type of nodes in the graph.
 */
public class DepthFirstEnumerationBuilder<T>
		extends AbstractEnumerationBuilder<T>
{

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 */
	public DepthFirstEnumerationBuilder(Graph<T> graph)
	{
		super(graph);
	}

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 * @param setFactory
	 *            a factory to create sets for storing nodes with.
	 */
	public DepthFirstEnumerationBuilder(Graph<T> graph,
			SetFactory<T> setFactory)
	{
		super(graph, setFactory);
	}

	@Override
	protected T chooseNext(List<T> neighbours, Set<T> neighbourSet)
	{
		T next = neighbours.remove(neighbours.size() - 1);
		neighbourSet.remove(next);
		return next;
	}

	@Override
	protected List<T> createList()
	{
		// Use a array based list here, because elements will be appended and
		// only removed from the tail (it is used as a stack)
		return new ArrayList<>();
	}

}
