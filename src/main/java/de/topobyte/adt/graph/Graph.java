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

package de.topobyte.adt.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A class to represent an unweighted directed graph.
 * 
 * @author Sebastian Kuerten (sebastian@topobyte.de)
 * 
 * @param <T>
 *            the type of node elements.
 */
public class Graph<T>
{

	private Set<T> nodes;
	private Map<T, Set<T>> edgesOut;
	private Map<T, Set<T>> edgesIn;

	public Graph()
	{
		nodes = new HashSet<>();
		edgesOut = new HashMap<>();
		edgesIn = new HashMap<>();
	}

	private Graph(Set<T> nodes, Map<T, Set<T>> edgesOut, Map<T, Set<T>> edgesIn)
	{
		this.nodes = nodes;
		this.edgesOut = edgesOut;
		this.edgesIn = edgesIn;
	}

	/**
	 * Add a node to the graph. Nothing happens if the node is already contained
	 * in the graph.
	 * 
	 * @param node
	 *            the node to add.
	 */
	public void addNode(T node)
	{
		if (nodes.contains(node)) {
			return;
		}
		nodes.add(node);
		edgesOut.put(node, new HashSet<T>());
		edgesIn.put(node, new HashSet<T>());
	}

	/**
	 * Returns <tt>true</tt> if this graph contains the specified node.
	 * 
	 * @param node
	 *            node whose presence in this graph is to be tested.
	 * @return <tt>true</tt> if this graph contains the specified node.
	 */
	public boolean containsNode(T node)
	{
		return nodes.contains(node);
	}

	/**
	 * Remove a node from the graph.
	 * 
	 * @param node
	 *            the node to remove.
	 */
	public void removeNode(T node)
	{
		nodes.remove(node);
		Set<T> in = edgesIn.get(node);
		Set<T> out = edgesOut.get(node);
		edgesIn.remove(node);
		edgesOut.remove(node);
		for (T other : out) {
			edgesIn.get(other).remove(node);
		}
		for (T other : in) {
			edgesOut.get(other).remove(node);
		}
	}

	/**
	 * Remove a collection of nodes from the graph.
	 * 
	 * @param toRemove
	 *            the nodes to remove.
	 */
	public void removeAllNodes(Collection<T> toRemove)
	{
		for (T node : toRemove) {
			removeNode(node);
		}
	}

	/**
	 * Add all these nodes to the graph.
	 * 
	 * @param nodesToAdd
	 *            a collection of nodes to add.
	 */
	public void addNodes(Collection<T> nodesToAdd)
	{
		for (T node : nodesToAdd) {
			addNode(node);
		}
	}

	/**
	 * Add this edge to the graph.
	 * 
	 * @param a
	 *            a node.
	 * @param b
	 *            another node.
	 */
	public void addEdge(T a, T b)
	{
		edgesOut.get(a).add(b);
		edgesIn.get(b).add(a);
	}

	/**
	 * Returns <tt>true</tt> if this graph contains the specified edge from
	 * <tt>a</tt> to <tt>b</tt>.
	 * 
	 * @param a
	 *            a node.
	 * @param b
	 *            another node.
	 * @return <tt>true</tt> if this graph contains an edge from <tt>a</tt> to
	 *         <tt>b</tt>.
	 */
	public boolean containsEdge(T a, T b)
	{
		Set<T> out = edgesOut.get(a);
		return out.contains(b);
	}

	/**
	 * Remove this edge from graph.
	 * 
	 * @param a
	 *            a node.
	 * @param b
	 *            another node.
	 */
	public void removeEdge(T a, T b)
	{
		edgesOut.get(a).remove(b);
		edgesIn.get(b).remove(a);
	}

	/**
	 * Return the nodes of this graph.
	 * 
	 * @return the set of nodes.
	 */
	public Collection<T> getNodes()
	{
		return nodes;
	}

	/**
	 * Get all outgoing edges (the nodes that can be reached via these edges).
	 * 
	 * @param node
	 *            the node whose edges to get.
	 * @return a set of reachable nodes.
	 */
	public Set<T> getEdgesOut(T node)
	{
		return edgesOut.get(node);
	}

	/**
	 * Get all incoming edges (the nodes that are the origin of these edges).
	 * 
	 * @param node
	 *            the node whose incoming edges to get.
	 * @return a set of nodes that can reach this one.
	 */
	public Set<T> getEdgesIn(T node)
	{
		return edgesIn.get(node);
	}

	/**
	 * Get the number of incoming edges.
	 * 
	 * @param node
	 *            the node.
	 * @return the number of incoming edges.
	 */
	public int degreeIn(T node)
	{
		return getEdgesIn(node).size();
	}

	/**
	 * Get the number of outgoing edges.
	 * 
	 * @param node
	 *            the node.
	 * @return the number of outgoing edges.
	 */
	public int degreeOut(T node)
	{
		return getEdgesOut(node).size();
	}

	/**
	 * Get a partition of the graph. Each subset consist of a set of nodes that
	 * are connected. This currently expects the graph to be undirected.
	 * 
	 * @return a partition of the graph.
	 */
	public Set<Set<T>> getPartition()
	{
		Set<Set<T>> partition = new HashSet<>();

		Set<T> left = new HashSet<>();
		left.addAll(nodes);
		while (!left.isEmpty()) {
			T next = left.iterator().next();
			left.remove(next);
			Set<T> connected = new HashSet<>();
			connected.add(next);
			Set<T> out = getReachable(next);
			for (T o : out) {
				connected.add(o);
				left.remove(o);
			}
			partition.add(connected);
		}

		return partition;
	}

	/**
	 * Find all nodes reachable in the graph starting from the specified node.
	 * 
	 * @param node
	 *            the node to start the traversal of the graph from.
	 * @return a set containing all reachable nodes of the graph.
	 */
	public Set<T> getReachable(T node)
	{
		Set<T> reachable = new HashSet<>();
		Set<T> check = new HashSet<>();
		check.add(node);
		while (!check.isEmpty()) {
			T next = check.iterator().next();
			check.remove(next);
			reachable.add(next);

			Set<T> out = getEdgesOut(next);
			for (T o : out) {
				if (reachable.contains(o)) {
					continue;
				}
				if (check.contains(o)) {
					continue;
				}
				check.add(o);
			}
		}
		return reachable;
	}

	/**
	 * Create a graph with the same nodes as this one, just all edges reversed.
	 * Note that the returned graph uses the same internal data structures as
	 * the original graph so changes on the reversed graph will affect the
	 * original graph and vice versa.
	 * 
	 * @return a graph with all edges reversed.
	 */
	public Graph<T> reversed()
	{
		return new Graph<>(nodes, edgesIn, edgesOut);
	}

}
