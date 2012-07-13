package org.neo4j.client.rest.traversal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.client.Node;
import org.neo4j.client.Path;
import org.neo4j.client.Relationship;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.traversal.RestTraverser;

public class RestNodeTraverser implements RestTraverser {

	private Iterable<Node> nodes;
	
	public RestNodeTraverser(Collection<RestNode> nodes) {
		this.nodes = new ArrayList<Node>(nodes);
	}
	
	@Override
	public Iterable<Node> getNodes() {
		return nodes;
	}

	@Override
	public Iterable<Relationship> getRelationships() {
		throw new UnsupportedOperationException();
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
