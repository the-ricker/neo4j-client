/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.net.URI;
import java.util.Collection;

import org.neo4j.client.rest.RestClientException;

/**
 * Handles the REST requests to the database server via HTTP. Most methods
 * return Future so that the caller can determine how long they wish to wait for
 * results.
 * 
 * @author Ricker
 * 
 */
public interface Loader {

	public URI getUri();

	public void setUri(URI uri);
	
	public long getReferenceNodeId()  throws RestClientException ;

	public NodeData createNode() throws RestClientException;

	public RelationshipData createRelationship(NodeData start, NodeData end, String type) throws RestClientException;

	public NodeData loadNode(long nodeId) throws RestClientException;

	public Collection<RelationshipData> loadNodeRelationships(NodeData nodeData) throws RestClientException;

	public RelationshipData loadRelationship(long id) throws RestClientException;

	public void saveNode(NodeData node) throws RestClientException;

	public void deleteNode(NodeData node) throws RestClientException;

	public void saveRelationship(RelationshipData relationship) throws RestClientException;

	public void deleteRelationship(RelationshipData relationship) throws RestClientException;

}
