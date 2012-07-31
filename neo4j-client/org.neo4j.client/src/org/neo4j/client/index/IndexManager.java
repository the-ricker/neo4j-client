/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.client.index;

import java.util.Map;

import org.neo4j.client.Node;
import org.neo4j.client.Relationship;

/**
 * A one stop shop for accessing {@link Index}s for {@link Node}s and
 * {@link Relationship}s. An {@link IndexManager} is paired with a
 * {@link GraphDatabaseService} via {@link GraphDatabaseService#index()} so that
 * indexes can be accessed directly from the graph database.
 */
public interface IndexManager {

	/**
	 * Returns whether or not there exists a node index with the name
	 * {@code indexName}. Indexes are created when needed in calls to
	 * {@link #forNodes(String)} and {@link #forNodes(String, Map)}.
	 * 
	 * @param indexName
	 *            the name of the index to check.
	 * @return whether or not there exists a node index with the name
	 *         {@code indexName}.
	 */
	boolean existsForNodes(String indexName);

	/**
	 * Returns an {@link Index} for {@link Node}s with the name
	 * {@code indexName}. If such an index doesn't exist it will be created with
	 * default configuration. Indexes created with
	 * {@link #forNodes(String, Map)} can be returned by this method also, so
	 * that you don't have to supply and match its configuration for consecutive
	 * accesses.
	 * 
	 * This is the prefered way of accessing indexes, whether they were created
	 * with {@link #forNodes(String)} or {@link #forNodes(String, Map)}.
	 * 
	 * @param indexName
	 *            the name of the node index.
	 * @return the {@link Index} corresponding to the {@code indexName}.
	 */
	Index<? extends Node> forNodes(String indexName);

	/**
	 * Returns the names of all existing {@link Node} indexes. Those names can
	 * then be used to get to the actual {@link Index} instances.
	 * 
	 * @return the names of all existing {@link Node} indexes.
	 */
	String[] nodeIndexNames();

	/**
	 * Returns whether or not there exists a relationship index with the name
	 * {@code indexName}. Indexes are created when needed in calls to
	 * {@link #forRelationships(String)} and
	 * {@link #forRelationships(String, Map)}.
	 * 
	 * @param indexName
	 *            the name of the index to check.
	 * @return whether or not there exists a relationship index with the name
	 *         {@code indexName}.
	 */
	boolean existsForRelationships(String indexName);

	/**
	 * Returns an {@link Index} for {@link Relationship}s with the name
	 * {@code indexName}. If such an index doesn't exist it will be created with
	 * default configuration. Indexes created with
	 * {@link #forRelationships(String, Map)} can be returned by this method
	 * also, so that you don't have to supply and match its configuration for
	 * consecutive accesses.
	 * 
	 * This is the prefered way of accessing indexes, whether they were created
	 * with {@link #forRelationships(String)} or
	 * {@link #forRelationships(String, Map)}.
	 * 
	 * @param indexName
	 *            the name of the node index.
	 * @return the {@link Index} corresponding to the {@code indexName}.
	 */
	Index<? extends Relationship> forRelationships(String indexName);

	/**
	 * Returns the names of all existing {@link Relationship} indexes. Those
	 * names can then be used to get to the actual {@link Index} instances.
	 * 
	 * @return the names of all existing {@link Relationship} indexes.
	 */
	String[] relationshipIndexNames();

}
