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

	URI getUri();

	void setUri(URI uri);

	long getReferenceNodeId() throws RestClientException;

	NodeData createNode() throws RestClientException;

	RelationshipData createRelationship(NodeData start, NodeData end, String type) throws RestClientException;

	NodeData getNode(long nodeId) throws RestClientException;

	Collection<RelationshipData> getNodeRelationships(NodeData nodeData) throws RestClientException;

	RelationshipData getRelationship(long id) throws RestClientException;

	void deleteNode(NodeData node) throws RestClientException;

	void deleteRelationship(RelationshipData relationship) throws RestClientException;

	Collection<NodeData> traverseNodes(NodeData node, TraversalDescriptionData description) throws RestClientException;

	Collection<RelationshipData> traverseRelationships(NodeData node, TraversalDescriptionData description)
			throws RestClientException;

	Collection<PathData> traversePaths(NodeData node, TraversalDescriptionData description) throws RestClientException;

	void setProperty(PropertyContainerData data, String key, Object value) throws RestClientException;

	void deleteProperty(PropertyContainerData data, String key) throws RestClientException;

	IndexData getNodeIndex(String indexName) throws RestClientException;

	IndexData getRelationshipIndex(String indexName) throws RestClientException;

	Map<String, IndexData> getNodeIndexNames() throws RestClientException;

	Map<String, IndexData> getRelationshipIndexNames() throws RestClientException;

	IndexData createNodeIndex(String indexName) throws RestClientException;

	IndexData createRelationshipIndex(String indexName) throws RestClientException;

	void addNodeIndex(String name, String nodeUri, String key, Object value) throws RestClientException;

	void removeNodeIndex(String name, long nodeId, String key, Object value) throws RestClientException;

	void deleteNodeIndex(String name) throws RestClientException;

	void addRelationshipIndex(String name, String relationshipUri, String key, Object value) throws RestClientException;

	void removeRelationshipIndex(String name, long id, String key, Object value) throws RestClientException;

	void deleteRelationshipIndex(String name) throws RestClientException;
}
