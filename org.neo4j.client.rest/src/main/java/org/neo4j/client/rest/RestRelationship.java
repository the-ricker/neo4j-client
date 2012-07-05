package org.neo4j.client.rest;

import org.neo4j.client.Relationship;

public interface RestRelationship extends Relationship, RestPropertyContainer {

	
	/**
	 * The ID of the start node. 
	 * @return
	 */
	public long getStartNodeId();

	public long getEndNodeId();
	
	/**
	 * True if the {@link #delete} method is called. 
	 */
	public boolean isDeleted();

}
