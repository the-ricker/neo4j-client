/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.Node;
import org.neo4j.client.RelationshipType;
import org.neo4j.client.index.IndexManager;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.RestPath;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.traversal.RestTraversalDescription;
import org.neo4j.client.rest.traversal.RestTraverser;
import org.neo4j.client.rest.traversal.impl.RestNodeTraverser;
import org.neo4j.client.rest.traversal.impl.RestPathTraverser;
import org.neo4j.client.rest.traversal.impl.RestRelationshipTraverser;
import org.neo4j.client.rest.traversal.impl.TraversalDescriptionData;
import org.neo4j.client.rest.util.PathUtil;
import org.neo4j.client.traversal.TraversalException;

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
		this(new LoaderImpl(null));
	}

	public RestGraphDatabaseImpl(URI uri) {
		this(new LoaderImpl(uri));
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
		return getNodeById(id, autoload);
	}
	

	private RestNodeImpl getNodeById(long id, boolean load) {
		RestNodeImpl node = lookupNode(id);
		if (node == null) {
			node = new RestNodeImpl(this, id);
			if (load) {
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
	public RestRelationship getRelationshipById(long id) {
		return getRelationshipById(id, autoload);
	}
	
	private RestRelationshipImpl getRelationshipById(long id, boolean load) {
		RestRelationshipImpl relationship = lookupRelationship(id);
		if (relationship == null) {
			relationship = new RestRelationshipImpl(this, id);
			if (load) {
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
		NodeData nodeData = loader.loadNode(node.getId());
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
		loader.saveNode(node.getNodeData());
	}

	public void deleteNode(RestNodeImpl node) throws RestClientException {
		if (node != null && !node.isDeleted()) {
			loader.deleteNode(node.getNodeData());
			nodes.remove(node.getId());
		}
	}

	public void loadRelationship(RestRelationshipImpl relationship) throws RestClientException {
		RelationshipData data = loader.loadRelationship(relationship.getId());
		relationship.setRelationshipData(data);
	}

	public void deleteRelationship(RestRelationshipImpl relationship) throws RestClientException {
		if (relationship != null && !relationship.isDeleted()) {
			loader.deleteRelationship(relationship.getRelationshipData());
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

	public void saveRelationship(RestRelationshipImpl relationship) throws RestClientException {
		loader.saveRelationship(relationship.getRelationshipData());
	}

	public RestRelationshipImpl createRelationship(RestNodeImpl start, Node otherNode, RelationshipType type)
			throws RestClientException {
		RestNodeImpl end = lookupNode(otherNode.getId());
		RelationshipData relationshipdata = loader.createRelationship(start.getNodeData(), end.getNodeData(),
				type.name());
		RestRelationshipImpl relationship = new RestRelationshipImpl(this, relationshipdata);
		start.addRelationship(relationship);
		end.addRelationship(relationship);
		cacheRelationship(relationship);
		return relationship;
	}

	@Override
	public RestTraverser traverse(RestTraversalDescription description, RestNode start) throws TraversalException {
		RestNodeImpl impl = lookupNode(start.getId());
		TraversalDescriptionData traversalData = new TraversalDescriptionData(description);
		switch (description.getReturnType()) {
		case NODE:
			return traverseNode(traversalData, impl);
		case RELATIONSHIP:
			return traverseRelationship(traversalData, impl);
		case PATH:
		case FULLPATH:
			return traversePath(traversalData, impl);
		}
		throw new TraversalException("Unknown return type");
	}

	private RestTraverser traversePath(TraversalDescriptionData description, RestNodeImpl start)
			throws TraversalException {
		try {
			List<RestPath> paths = new ArrayList<RestPath>();
			for (PathData pathData : loader.traversePaths(start.getNodeData(), description)) {
				paths.add(new RestPathImpl(this, pathData));
			}
			return new RestPathTraverser(paths);
		} catch (RestClientException e) {
			throw new TraversalException(e);
		}
	}

	private RestTraverser traverseRelationship(TraversalDescriptionData description, RestNodeImpl start)
			throws TraversalException {
		try {
			List<RestRelationship> relationships = new ArrayList<RestRelationship>();
			for (RelationshipData relData : loader.traverseRelationships(start.getNodeData(), description)) {
				relationships.add(getRelationship(relData));
			}
			return new RestRelationshipTraverser(relationships);
		} catch (RestClientException e) {
			throw new TraversalException(e);
		}
	}

	private RestTraverser traverseNode(TraversalDescriptionData description, RestNodeImpl start)
			throws TraversalException {
		try {
			List<RestNode> nodes = new ArrayList<RestNode>();
			for (NodeData nodeData : loader.traverseNodes(start.getNodeData(), description)) {
				nodes.add(getNode(nodeData));
			}
			return new RestNodeTraverser(nodes);
		} catch (RestClientException e) {
			throw new TraversalException(e);
		}
	}
	
	private RestNodeImpl getNode(NodeData nodeData) {
		long id = PathUtil.getNodeId(nodeData.getSelf());
		RestNodeImpl node = getNodeById(id, false);
		node.setNodeData(nodeData);
		return node;
	}

	private RestRelationshipImpl getRelationship(RelationshipData relationshipData) {
		long id = PathUtil.getRelationshipId(relationshipData.getSelf());
		RestRelationshipImpl relationship = getRelationshipById(id, false);
		relationship.setRelationshipData(relationshipData);
		return relationship;
	}
	
	
}
