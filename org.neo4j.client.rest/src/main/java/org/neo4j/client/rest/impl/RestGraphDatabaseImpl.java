/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.Node;
import org.neo4j.client.Relationship;
import org.neo4j.client.RelationshipType;
import org.neo4j.client.index.IndexManager;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.util.PathUtil;

/**
 * Responsible for implementing the interface and maintaining the cache.
 * <p>
 * The {@link Loader} is responsible for interacting with the database via HTTP.
 * 
 * @author Ricker
 * 
 */
public class RestGraphDatabaseImpl implements RestGraphDatabase {

	public static final long DEFAULT_TIMEOUT = 2000;

	private static Log log = LogFactory.getLog(RestGraphDatabaseImpl.class);

	/*
	 * @see http://www.codeinstructions.com/2008/09/weakhashmap-is-not-cache-
	 * understanding.html
	 */
	private Map<Long, SoftReference<RestNodeImpl>> nodes;
	private Map<Long, SoftReference<RestRelationshipImpl>> relationships;
	private boolean autoload;
	private Loader loader;

	public RestGraphDatabaseImpl() {
		this(new Loader(null));
	}

	public RestGraphDatabaseImpl(URI uri) {
		this(new Loader(uri));
	}
	
	public RestGraphDatabaseImpl(Loader loader) {
		this.loader = loader;
		nodes = new WeakHashMap<Long, SoftReference<RestNodeImpl>>();
		relationships = new WeakHashMap<Long, SoftReference<RestRelationshipImpl>>();
		autoload = true;
	}

	@Override
	public URI getURI() {
		return loader.getUri();
	}

	public void setURI(String uri) throws URISyntaxException {
		loader.setUri(new URI(uri));
	}

	public void setURI(URI uri) {
		loader.setUri(uri);
	}

	@Override
	public RestNode createNode() {
		NodeData nodeData = null;
		try {
			nodeData = loader.createNode();
		} catch (RestClientException e) {
			log.error("Failed to create node", e);
		}
		RestNodeImpl node = new RestNodeImpl(this, nodeData);
		cacheNode(node);
		return node;
	}

	@Override
	public RestNode getNodeById(long id) {
		RestNodeImpl node = lookupNode(id);
		if (node == null) {
			node = new RestNodeImpl(this, id);
			if (autoload) {
				try {
					loadNode(node);
				} catch (RestClientException e) {
					log.error("Failed to load node " + id, e);
				}
			}
			cacheNode(node);
		}
		return node;
	}

	private RestNodeImpl lookupNode(long id) {
		SoftReference<RestNodeImpl> ref = nodes.get(id);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	private void cacheNode(RestNodeImpl node) {
		nodes.put(node.getId(), new SoftReference<RestNodeImpl>(node));
	}

	@Override
	public Relationship getRelationshipById(long id) {
		RestRelationshipImpl relationship = lookupRelationship(id);
		if (relationship == null) {
			relationship = new RestRelationshipImpl(this, id);
			if (autoload) {
				try {
					loadRelationship(relationship);
				} catch (RestClientException e) {
					log.error("Could not load relationship " + id, e);
				}
			}
			cacheRelationship(relationship);
		}
		return relationship;
	}

	private void cacheRelationship(RestRelationshipImpl relationship) {
		relationships.put(relationship.getId(), new SoftReference<RestRelationshipImpl>(relationship));
	}

	private RestRelationshipImpl lookupRelationship(long id) {
		SoftReference<RestRelationshipImpl> ref = relationships.get(id);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	@Override
	public Node getReferenceNode() {
		try {
			Long nodeId = loader.getReferenceNodeId();
			return getNodeById(nodeId);
		} catch (Exception e) {
			log.error("Failed to get reference node", e);
		}
		return null;
	}

	@Override
	public IndexManager index() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		nodes.clear();
		relationships.clear();
	}

	@Override
	public boolean isAutoLoad() {
		return autoload;
	}

	@Override
	public void setAutoLoad(boolean autoLoad) {
		this.autoload = autoLoad;
	}

	public void loadNode(RestNodeImpl node) throws RestClientException {
		NodeData nodeData = loader.loadNodeData(node.getId());
		node.setNodeData(nodeData);
		node.clearRelationships();
		if (nodeData != null) {
			for (RelationshipData relationshipData : loader.loadNodeRelationships(nodeData)) {
				long id = PathUtil.getRelationshipId(relationshipData.getSelf());
				RestRelationshipImpl relationship = lookupRelationship(id);
				if (relationship == null) {
					relationship = new RestRelationshipImpl(this, relationshipData);
					cacheRelationship(relationship);
				} else {
					relationship.setRelationshipData(relationshipData);
				}
				node.addRelationship(relationship);
			}
		}
	}

	public void saveNode(RestNodeImpl node) throws RestClientException {
		loader.saveNode(node);
	}

	public void deleteNode(RestNodeImpl node) throws RestClientException {
		if (node != null && !node.isDeleted()) {
			loader.deleteNode(node);
			nodes.remove(node.getId());
		}
	}

	public void loadRelationship(RestRelationshipImpl relationship) throws RestClientException {
		RelationshipData data = loader.loadRelationshipData(relationship.getId());
		relationship.setRelationshipData(data);
	}

	public void deleteRelationship(RestRelationshipImpl relationship) throws RestClientException {
		if (relationship != null && !relationship.isDeleted()) {
			loader.deleteRelationship(relationship);
			RestNodeImpl node = lookupNode(relationship.getStartNodeId());
			if (node != null && node.isLoaded()) {
				node.removeRelationship(relationship);
			}
			node = lookupNode(relationship.getEndNodeId());
			if (node != null && node.isLoaded()) {
				node.removeRelationship(relationship);
			}
			relationships.remove(relationship.getId());
		}
	}

	public void saveRelationship(RestRelationshipImpl relationship) {
		loader.saveRelationship(relationship);
	}

	public RestRelationshipImpl createRelationship(RestNodeImpl start, Node otherNode, RelationshipType type)
			throws RestClientException {
		RestNodeImpl end = lookupNode(otherNode.getId());
		RelationshipData relationshipdata = loader.createRelationship(start, end, type);
		RestRelationshipImpl relationship = new RestRelationshipImpl(this, relationshipdata);
		start.addRelationship(relationship);
		end.addRelationship(relationship);
		cacheRelationship(relationship);
		return relationship;
	}

}
