/**
 * 
 */
package org.neo4j.client.rest;

import org.neo4j.client.PropertyContainer;

/**
 * @author Ricker
 *
 */
public interface RestPropertyContainer extends PropertyContainer {
	
	/**
	 * The database of this node or relationship.
	 */
	@Override
	public RestGraphDatabase getGraphDatabase();
	
	
	/**
	 * Instructs the database to reload the properties with the values 
	 * stored in the database. It will overwrite any values in this object.
	 */
	public void refresh() throws RestClientException;
	
	/**
	 * The URI of this property container
	 * @return
	 */
	public String getSelf();

}
