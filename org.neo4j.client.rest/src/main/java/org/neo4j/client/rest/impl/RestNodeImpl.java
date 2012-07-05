/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.Direction;
import org.neo4j.client.Node;
import org.neo4j.client.Relationship;
import org.neo4j.client.RelationshipType;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.util.PathUtil;
import org.neo4j.client.rest.util.RelationshipUtil;

/**
 * @author Ricker
 * 
 */
public class RestNodeImpl extends PropertyContainerImpl implements RestNode {

	public final static long NO_ID = -1;

	private static Log log = LogFactory.getLog(RestNodeImpl.class);

	private NodeData data;
	private Collection<RestRelationshipImpl> relationships;
	private long id;
	private boolean deleted;

	protected RestNodeImpl(RestGraphDatabaseImpl graphDatabase, long id) {
		super(graphDatabase);
		this.id = id;
		data = null;
		relationships = new HashSet<RestRelationshipImpl>();
		deleted = false;
	}

	protected RestNodeImpl(RestGraphDatabaseImpl graphDatabase, NodeData data) {
		super(graphDatabase);
		relationships = new HashSet<RestRelationshipImpl>();
		setNodeData(data);
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void delete() {
		try {
			graphDatabase.deleteNode(this);
			data = null;
			relationships = null;
			deleted = true;
		} catch (RestClientException e) {
			log.error("Error deleting node " + id, e);
		}
	}

	@Override
	public Iterable<Relationship> getRelationships() {
		checkDeleted();
		return new HashSet<Relationship>(relationships);
	}

	@Override
	public boolean hasRelationship() {
		checkDeleted();
		return !relationships.isEmpty();
	}

	@Override
	public Iterable<Relationship> getRelationships(RelationshipType... types) {
		checkDeleted();
		return getRelationships(Direction.BOTH, types);
	}

	@Override
	public Iterable<Relationship> getRelationships(Direction direction, RelationshipType... types) {
		checkDeleted();
		HashSet<Relationship> results = new HashSet<Relationship>();
		for (RestRelationshipImpl rel : relationships) {
			if (direction == Direction.BOTH || direction == RelationshipUtil.getDirection(this, rel)) {
				if (RelationshipUtil.intersects(rel.getType(), types)) {
					results.add(rel);
				}
			}
		}
		return results;
	}

	@Override
	public boolean hasRelationship(RelationshipType... types) {
		checkDeleted();
		return hasRelationship(Direction.BOTH, types);
	}

	@Override
	public boolean hasRelationship(Direction direction, RelationshipType... types) {
		checkDeleted();
		for (RestRelationshipImpl rel : relationships) {
			if (direction == Direction.BOTH || direction == RelationshipUtil.getDirection(this, rel)) {
				if (RelationshipUtil.intersects(rel.getType(), types)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Iterable<Relationship> getRelationships(Direction dir) {
		checkDeleted();
		if (dir == Direction.BOTH) {
			return getRelationships();
		}
		HashSet<Relationship> results = new HashSet<Relationship>();
		for (RestRelationshipImpl rel : relationships) {
			if (dir == RelationshipUtil.getDirection(this, rel)) {
				results.add(rel);
			}
		}
		return results;
	}

	@Override
	public boolean hasRelationship(Direction dir) {
		checkDeleted();
		for (RestRelationshipImpl rel : relationships) {
			if (dir == Direction.BOTH || dir == RelationshipUtil.getDirection(this, rel)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterable<Relationship> getRelationships(RelationshipType type, Direction dir) {
		checkDeleted();
		return getRelationships(dir, type);
	}

	@Override
	public boolean hasRelationship(RelationshipType type, Direction dir) {
		checkDeleted();
		return hasRelationship(dir, type);
	}

	@Override
	public Relationship getSingleRelationship(RelationshipType type, Direction dir) {
		checkDeleted();
		for (RestRelationshipImpl rel : relationships) {
			if (dir == Direction.BOTH || dir == RelationshipUtil.getDirection(this, rel)) {
				if (RelationshipUtil.matches(rel.getType(), type)) {
					return rel;
				}
			}
		}
		return null;
	}

	@Override
	public Relationship createRelationshipTo(Node otherNode, RelationshipType type) {
		checkDeleted();
		RestRelationshipImpl relationship = null;
		try {
			relationship = graphDatabase.createRelationship(this, otherNode, type);
			relationships.add(relationship);
			setDirty(true);
		} catch (RestClientException e) {
			log.error("Failed to create relationship", e);
		}
		return relationship;
	}

	private void checkDeleted() {
		if (deleted) {
			throw new UnsupportedOperationException("Node is deleted");
		}
	}

	@Override
	public void save() throws RestClientException {
		checkDeleted();
		graphDatabase.saveNode(this);
		setDirty(false);
	}

	@Override
	protected Map<String, Object> getData() {
		return data.getData();
	}

	@Override
	public String getSelf() {
		checkDeleted();
		return data.getSelf();
	}

	public void setNodeData(NodeData nodeData) {
		this.data = nodeData;
		if (data != null) {
			id = PathUtil.getNodeId(data.getSelf());
			deleted = false;
			setDirty(false);
		} else {
			id = NO_ID;
			deleted = true;
		}
	}

	public NodeData getNodeData() {
		return data;
	}

	@Override
	public void load() throws RestClientException {
		graphDatabase.loadNode(this);
		setDirty(false);
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public boolean isLoaded() {
		return (data != null);
	}

	public void clearRelationships() {
		relationships.clear();
	}

	public void removeRelationship(RestRelationshipImpl relationship) {
		relationships.remove(relationship);
	}

	public void addRelationship(RestRelationshipImpl relationship) {
		relationships.add(relationship);
	}

}