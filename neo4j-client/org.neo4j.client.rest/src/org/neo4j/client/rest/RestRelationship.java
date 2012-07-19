package org.neo4j.client.rest;

import org.neo4j.client.Relationship;

/**
 *  
 *  
 * @author Ricker
 *
 */
public interface RestRelationship extends Relationship, RestPropertyContainer {

	
	/**
	 * The ID of the start node. 
	 * @return
	 */
	public long getStartNodeId();

	/**
	 * The ID of the end node.
	 * @return
	 */
	public long getEndNodeId();
	
	/**
	 * True if the {@link #delete} method has been called. 
	 */
	public boolean isDeleted();

}
