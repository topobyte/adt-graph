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

package de.topobyte.adt.graph.factories;

import java.util.HashMap;
import java.util.Map;

public class HashMapFactory<K, V> implements MapFactory<K, V>
{

	@Override
	public Map<K, V> create()
	{
		return new HashMap<>();
	}

}
