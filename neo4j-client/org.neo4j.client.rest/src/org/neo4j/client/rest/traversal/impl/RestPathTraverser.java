package org.neo4j.client.rest.traversal.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.neo4j.client.Node;
import org.neo4j.client.Path;
import org.neo4j.client.Relationship;
import org.neo4j.client.rest.RestPath;
import org.neo4j.client.rest.traversal.RestTraverser;

public class RestPathTraverser implements RestTraverser {

	private Collection<Path> paths;

	public RestPathTraverser(Collection<RestPath> paths) {
		this.paths = new ArrayList<Path>(paths);
	}

	@Override
	public Iterable<Node> getNodes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Relationship> getRelationships() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Path> iterator() {
		return paths.iterator();
	}

	@Override
	public Iterable<Path> getPaths() {
		return paths;
	}

}
