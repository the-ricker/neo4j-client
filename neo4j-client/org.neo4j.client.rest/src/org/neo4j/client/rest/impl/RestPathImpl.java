/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.neo4j.client.Node;
import org.neo4j.client.PropertyContainer;
import org.neo4j.client.Relationship;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.RestPath;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.client.PathData;
import org.neo4j.client.rest.util.PathUtil;

/**
 * @author Ricker
 * 
 */
public class RestPathImpl implements RestPath {

	private RestGraphDatabaseImpl graphDatabase;
	private PathData data;
	private List<RestNode> nodes;
	private List<RestRelationship> relationships;

	public RestPathImpl(RestGraphDatabaseImpl graphDatabase, PathData data) {
		this.graphDatabase = graphDatabase;
		this.data = data;
		init();
	}

	private void init() {
		long id = 0;
		/*
		 * nodes
		 */
		if (data.getNodes() != null) {
			nodes = new ArrayList<RestNode>(data.getNodes().length);
			for (String n : data.getNodes()) {
				id = PathUtil.getNodeId(n);
				nodes.add(graphDatabase.getNodeById(id));
			}
		} else {
			nodes = new ArrayList<RestNode>(0);
		}

		/*
		 * relationships
		 */
		if (data.getRelationships() != null) {
			relationships = new ArrayList<RestRelationship>(data.getRelationships().length);
			for (String n : data.getRelationships()) {
				id = PathUtil.getRelationshipId(n);
				relationships.add(graphDatabase.getRelationshipById(id));
			}
		} else {
			relationships = new ArrayList<RestRelationship>(0);
		}
	}

	@Override
	public Node startNode() {
		if (!nodes.isEmpty()) {
			return nodes.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Node endNode() {
		if (!nodes.isEmpty()) {
			return nodes.get(nodes.size() - 1);
		} else {
			return null;
		}
	}

	@Override
	public Relationship lastRelationship() {
		if (!relationships.isEmpty()) {
			return relationships.get(relationships.size() - 1);
		} else {
			return null;
		}
	}

	@Override
	public Iterable<Relationship> relationships() {
		return new ArrayList<Relationship>(relationships);
	}

	@Override
	public Iterable<Relationship> reverseRelationships() {
		List<Relationship> copy = new ArrayList<Relationship>(relationships);
		Collections.reverse(copy);
		return copy;
	}

	@Override
	public Iterable<Node> nodes() {
		return new ArrayList<Node>(nodes);
	}

	@Override
	public Iterable<Node> reverseNodes() {
		List<Node> copy = new ArrayList<Node>(nodes);
		Collections.reverse(copy);
		return copy;
	}

	@Override
	public int length() {
		return data.getLength();
	}

	@Override
	public Iterator<PropertyContainer> iterator() {
		ArrayList<PropertyContainer> elements = new ArrayList<PropertyContainer>();
		Iterator<RestNode> n = nodes.iterator();
		Iterator<RestRelationship> r = relationships.iterator();
		while (n.hasNext()) {
			elements.add(n.next());
			if (r.hasNext()) {
				elements.add(r.next());
			}
		}
		return elements.iterator();
	}

}
