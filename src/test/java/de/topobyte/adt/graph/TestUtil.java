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

package de.topobyte.adt.graph;

import java.util.Set;

import de.topobyte.adt.graph.factories.TreeMapFactory;
import de.topobyte.adt.graph.factories.TreeSetFactory;

public class TestUtil
{

	/**
	 * Create a binary tree with the specified number of levels.
	 * 
	 * For two levels, it will look like this:
	 * 
	 * <pre>
	 *            (1)
	 *           /   \
	 *         (2)   (3)
	 * </pre>
	 * 
	 * For three levels, it will look like this:
	 * 
	 * <pre>
	 *              (1)
	 *           /       \
	 *         (2)       (3)
	 *        /   \     /   \
	 *      (4)  (5)  (6)   (7)
	 * </pre>
	 */
	public static Graph<Integer> createBinaryTree(int nLevels)
	{
		Graph<Integer> graph = new Graph<>(new TreeSetFactory<Integer>(),
				new TreeMapFactory<Integer, Set<Integer>>());

		// total number of nodes
		int m1 = (1 << nLevels) - 1;
		// number of inner nodes
		int m2 = (1 << nLevels - 1) - 1;

		for (int i = 1; i <= m1; i++) {
			graph.addNode(i);
		}
		for (int i = 1; i <= m2; i++) {
			graph.addEdge(i, i * 2);
			graph.addEdge(i, i * 2 + 1);
		}

		return graph;
	}

}
