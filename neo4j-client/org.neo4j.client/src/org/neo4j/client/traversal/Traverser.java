/**
 * 
 */
package org.neo4j.client.traversal;

import java.util.Iterator;

import org.neo4j.client.Node;
import org.neo4j.client.Path;
import org.neo4j.client.Relationship;

/**
 * 
 * This interface represents the traverser which is used to step through the
 * results of a traversal. Each step can be represented in different ways. The
 * default is as {@link Path} objects which all other representations can be
 * derived from, i.e {@link Node} or {@link Relationship}. Each step can also be
 * represented in one of those representations directly.
 * 
 */
public interface Traverser {

	Iterable<Node> getNodes();

	Iterable<Relationship> getRelationships();

	Iterator<Path> iterator();

	Iterable<Path> getPaths();

}
