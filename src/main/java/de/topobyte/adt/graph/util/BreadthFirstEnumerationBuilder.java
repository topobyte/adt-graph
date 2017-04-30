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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.topobyte.adt.graph.Graph;

/**
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 * @param <T>
 *            type of nodes in the graph.
 */
public class BreadthFirstEnumerationBuilder<T>
		extends AbstractEnumerationBuilder<T>
{

	/**
	 * Create a new EnumerationBuilder for the denoted {@code graph}.
	 * 
	 * @param graph
	 *            the graph to build an enumeration for.
	 */
	public BreadthFirstEnumerationBuilder(Graph<T> graph)
	{
		super(graph);
	}

	@Override
	protected T chooseNext(List<T> neighbours, Set<T> neighbourSet)
	{
		T next = neighbours.remove(0);
		neighbourSet.remove(next);
		return next;
	}

	@Override
	protected List<T> createList()
	{
		// Use a linked list here, because elements will be appended and
		// also removed from the front (it is used as a queue)
		return new LinkedList<>();
	}

}
