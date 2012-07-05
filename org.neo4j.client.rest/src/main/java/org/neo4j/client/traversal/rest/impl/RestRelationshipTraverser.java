/**
 * 
 */
package org.neo4j.client.traversal.rest.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.client.Node;
import org.neo4j.client.Path;
import org.neo4j.client.Relationship;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.traversal.rest.RestTraverser;

/**
 * @author Ricker
 *
 */
public class RestRelationshipTraverser implements RestTraverser {
	
	private Iterable<Relationship> relationships;
	
	public RestRelationshipTraverser(Collection<RestRelationship> rels) {
		this.relationships = new ArrayList<Relationship>(rels);
	}
 
	@Override
	public Iterable<Node> getNodes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Relationship> getRelationships() {
		return relationships;
	}

	@Override
	public Iterator<Path> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Path> getPaths() {
		throw new UnsupportedOperationException();
	}

}
