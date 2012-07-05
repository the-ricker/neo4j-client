/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.DynamicRelationshipType;
import org.neo4j.client.Node;
import org.neo4j.client.RelationshipType;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.util.PathUtil;

/**
 * The connection of the Relationship to the Nodes is weak. 
 * 
 * @author Ricker
 * 
 */
public class RestRelationshipImpl extends PropertyContainerImpl implements RestRelationship {

	private static Log log = LogFactory.getLog(RestRelationshipImpl.class); 
	
	private long id;
	private RelationshipData data;
	private long startNodeId;
	private long endNodeId;
	private boolean deleted;

	/**
	 * @param graphDatabase
	 */
	protected RestRelationshipImpl(RestGraphDatabaseImpl graphDatabase, RelationshipData data) {
		super(graphDatabase);
		setRelationshipData(data);
		deleted = false;
	}

	protected RestRelationshipImpl(RestGraphDatabaseImpl graphDatabase, long id) {
		super(graphDatabase);
		this.id = id;
		startNodeId = 0;
		endNodeId=0;
		deleted = false;
	}
	
	public void setRelationshipData(RelationshipData data) {
		this.data = data;
		if (data == null) {
			deleted = true;
		} else {
			id = PathUtil.getRelationshipId(data.getSelf());
			startNodeId = PathUtil.getNodeId(data.getStart());
			endNodeId =PathUtil.getNodeId(data.getEnd());
		}
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void delete() {
		try {
		graphDatabase.deleteRelationship(this);
		deleted = true;
		}  catch (RestClientException e) {
			log.error("Error deleting reference " + id, e);
		}
	}

	@Override
	public Node getStartNode() {
		return graphDatabase.getNodeById(startNodeId);
	}

	@Override
	public Node getEndNode() {
		return graphDatabase.getNodeById(endNodeId);
	}


	@Override
	public Node getOtherNode(Node node) {
		if (node != null) {
			if (node.getId() == startNodeId) {
				return getStartNode();
			}
			if (node.getId() == endNodeId) {
				return getEndNode();
			}
		}
		return null;
	}


	@Override
	public Node[] getNodes() {
		return new Node[]{getStartNode(), getEndNode()};
	}


	@Override
	public RelationshipType getType() {
		return DynamicRelationshipType.withName(data.getType());
	}

	@Override
	public boolean isType(RelationshipType type) {
		return type.equals(getType());
	}


	@Override
	public void save() throws RestClientException {
		graphDatabase.saveRelationship(this);
		setDirty(false);
	}

	@Override
	public String getSelf() {
		return data.getSelf();
	}

	@Override
	protected Map<String, Object> getData() {
		return data.getData();
	}

	@Override
	public long getStartNodeId() {
		return startNodeId;
	}

	@Override
	public long getEndNodeId() {
		return endNodeId;
	}

	@Override
	public void load() throws RestClientException {
		graphDatabase.loadRelationship(this);
		setDirty(false);
	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public boolean isLoaded() {
		return (data != null);
	}

	public void setDeleted(boolean b) {
		this.deleted = b;
	}

	public RelationshipData getRelationshipData() {
		return data;
	}

}
