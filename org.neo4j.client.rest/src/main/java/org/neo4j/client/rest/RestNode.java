/**
 * 
 */
package org.neo4j.client.rest;

import org.neo4j.client.Node;

/**
 * @author Ricker
 *
 */
public interface RestNode extends Node, RestPropertyContainer {
	
	/**
	 * True if the {@link #delete} method is called. Calling any of the properties after
	 * an node is deleted will throw an {@link UnsupportedOperationException}.
	 * 
	 */
	public boolean isDeleted();

}
