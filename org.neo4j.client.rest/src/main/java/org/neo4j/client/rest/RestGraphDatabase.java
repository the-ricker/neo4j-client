/**
 * 
 */
package org.neo4j.client.rest;

import java.net.URI;

import org.neo4j.client.GraphDatabase;
import org.neo4j.client.rest.traversal.RestTraversalDescription;
import org.neo4j.client.rest.traversal.RestTraverser;
import org.neo4j.client.traversal.TraversalException;

/**
 * @author Ricker
 * 
 */
public interface RestGraphDatabase extends GraphDatabase {

	public static String DEFAULT_URI = "http://localhost:7474/db/data/";

	/**
	 * The URI for the database
	 * 
	 * @return
	 */
	public URI getURI();

	public void setAutoLoad(boolean autoload);
	
	public boolean isAutoLoad();

	public RestTraverser traverse(RestTraversalDescription description, RestNode start) throws TraversalException;
}
