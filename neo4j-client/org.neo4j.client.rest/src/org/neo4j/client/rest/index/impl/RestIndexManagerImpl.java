package org.neo4j.client.rest.index.impl;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.Node;
import org.neo4j.client.PropertyContainer;
import org.neo4j.client.index.Index;
import org.neo4j.client.index.IndexManager;
import org.neo4j.client.index.RelationshipIndex;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.client.RestClient;
import org.neo4j.client.rest.impl.RestGraphDatabaseImpl;
import org.neo4j.client.rest.index.RestIndex;

public class RestIndexManagerImpl implements IndexManager {

	private static Log log = LogFactory.getLog(RestIndexManagerImpl.class);

	private RestClient client;
	private RestGraphDatabaseImpl db;
	private Map<String, SoftReference<RestIndex<RestNode>>> nodeIndexes;
	private Map<String, SoftReference<RestIndex<RestRelationship>>> relationshipIndexes;

	public RestIndexManagerImpl(RestGraphDatabaseImpl db, RestClient client) {
		this.db = db;
		this.client = client;
		nodeIndexes = new HashMap<String, SoftReference<RestIndex<RestNode>>>();
		relationshipIndexes = new HashMap<String, SoftReference<RestIndex<RestRelationship>>>();
	}

	@Override
	public boolean existsForNodes(String indexName) {
		RestIndex<RestNode> index = lookupNodeIndex(indexName);
		if (index != null) {
			return true;
		}
		IndexData data = null;
		try {
			data = client.getNodeIndex(indexName);
		} catch (RestClientException e) {
			log.error(e);
		}
		if (data != null) {
			createNodeIndex(indexName, data);
			return true;
		}
		return false;
	}

	private RestIndexImpl<RestNode> createNodeIndex(String name, IndexData data) {
		RestIndexImpl<RestNode> nodeIndex = new RestIndexImpl<RestNode>(this, name, data);
		nodeIndexes.put(name, new SoftReference<RestIndex<RestNode>>(nodeIndex));
		return nodeIndex;
	}

	private RestIndex<RestNode> lookupNodeIndex(String name) {
		SoftReference<RestIndex<RestNode>> ref = nodeIndexes.get(name);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	@Override
	public Index<Node> forNodes(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] nodeIndexNames() {
		Map<String, IndexData> indexes = client.getNodeIndexNames();
		// TODO reset index with data?
		return indexes.keySet().toArray(new String[]{});
	}

	@Override
	public boolean existsForRelationships(String indexName) {
		RestIndex<RestRelationship> index = lookupRelationshipIndex(indexName);
		if (index != null) {
			return true;
		}
		IndexData data = null;
		try {
			data = client.getRelationshipIndex(indexName);
		} catch (RestClientException e) {
			log.error(e);
		}
		if (data != null) {
			createRelationshipIndex(indexName, data);
			return true;
		}
		return false;
	}

	private RestIndexImpl<RestRelationship> createRelationshipIndex(String name, IndexData data) {
		RestIndexImpl<RestRelationship> index = new RestIndexImpl<RestRelationship>(this, name, data);
		relationshipIndexes.put(name, new SoftReference<RestIndex<RestRelationship>>(index));
		return index;
	}

	private RestIndex<RestRelationship> lookupRelationshipIndex(String name) {
		SoftReference<RestIndex<RestRelationship>> ref = relationshipIndexes.get(name);
		if (ref != null) {
			return ref.get();
		}
		return null;
	}

	@Override
	public RelationshipIndex forRelationships(String indexName) {

		return null;
	}

	@Override
	public String[] relationshipIndexNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public RestGraphDatabase getDatabase() {
		return db;
	}

}
