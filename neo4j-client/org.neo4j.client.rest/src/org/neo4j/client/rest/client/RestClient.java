/**
 * 
 */
package org.neo4j.client.rest.client;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.traversal.impl.TraversalDescriptionData;

/**
 * Handles the REST requests to the database server via HTTP. The methods accept
 * and return beans.
 * 
 * @author Ricker
 * 
 */
public interface RestClient {

	public URI getUri();

	public void setUri(URI uri);

	public long getReferenceNodeId() throws RestClientException;

	public NodeData createNode() throws RestClientException;

	public RelationshipData createRelationship(NodeData start, NodeData end, String type) throws RestClientException;

	public NodeData getNode(long nodeId) throws RestClientException;

	public Collection<RelationshipData> getNodeRelationships(NodeData nodeData) throws RestClientException;

	public RelationshipData getRelationship(long id) throws RestClientException;

	// public void saveNode(NodeData node) throws RestClientException;

	public void deleteNode(NodeData node) throws RestClientException;

	// public void saveRelationship(RelationshipData relationship) throws
	// RestClientException;

	public void deleteRelationship(RelationshipData relationship) throws RestClientException;

	public Collection<NodeData> traverseNodes(NodeData node, TraversalDescriptionData description)
			throws RestClientException;

	public Collection<RelationshipData> traverseRelationships(NodeData node, TraversalDescriptionData description)
			throws RestClientException;

	public Collection<PathData> traversePaths(NodeData node, TraversalDescriptionData description)
			throws RestClientException;

	public void setProperty(PropertyContainerData data, String key, Object value) throws RestClientException;

	public void deleteProperty(PropertyContainerData data, String key) throws RestClientException;

	public IndexData getNodeIndex(String indexName) throws RestClientException;
	
	public IndexData getRelationshipIndex(String indexName) throws RestClientException;

	public Map<String,IndexData> getNodeIndexNames();

}
