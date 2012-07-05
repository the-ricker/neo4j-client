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
	
	@Override
	public RestGraphDatabase getGraphDatabase();
	
//	public boolean isDirty();
//	
//	public void setDirty(boolean dirty);
//	
//	public void save() throws RestClientException;
//	
//	public void load() throws RestClientException;
//	
//	public boolean isLoaded();
	
	public void refresh();
	
	/**
	 * The URI of this property container
	 * @return
	 */
	public String getSelf();

}
