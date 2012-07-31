package org.neo4j.client.rest.index.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.index.Index;
import org.neo4j.client.index.IndexManager;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.client.RestClient;
import org.neo4j.client.rest.impl.RestGraphDatabaseImpl;

/**
 * Indexes are NOT cached. Every time you ask for the index you will get a new
 * object.
 * 
 * @author Ricker
 * 
 */
public class RestIndexManagerImpl implements IndexManager {

	private static Log log = LogFactory.getLog(RestIndexManagerImpl.class);

	private static final String[] EMPTY_NAMES = new String[] {};

	private RestClient client;
	private RestGraphDatabaseImpl db;

	public RestIndexManagerImpl(RestGraphDatabaseImpl db, RestClient client) {
		this.db = db;
		this.client = client;
	}

	@Override
	public boolean existsForNodes(String indexName) {
		IndexData data = null;
		try {
			data = client.getNodeIndex(indexName);
		} catch (RestClientException e) {
			log.error(e);
		}
		return (data != null);
	}

	@Override
	public Index<RestNode> forNodes(String indexName) {
		try {
			IndexData data = client.getNodeIndex(indexName);
			if (data == null) {
				data = client.createNodeIndex(indexName);
			}
			RestNodeIndexImpl nodeIndex = new RestNodeIndexImpl(db, client, indexName, data);
			return nodeIndex;
		} catch (RestClientException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public String[] nodeIndexNames() {
		try {
			Map<String, IndexData> indexes = client.getNodeIndexNames();
			return indexes.keySet().toArray(new String[] {});
		} catch (RestClientException e) {
			log.error(e);
		}
		return EMPTY_NAMES;
	}

	@Override
	public boolean existsForRelationships(String indexName) {
		IndexData data = null;
		try {
			data = client.getRelationshipIndex(indexName);
		} catch (RestClientException e) {
			log.error(e);
		}
		return (data != null);
	}

	@Override
	public Index<RestRelationship> forRelationships(String indexName) {
		try {
			IndexData data = client.getRelationshipIndex(indexName);
			if (data == null) {
				data = client.createRelationshipIndex(indexName);
			}
			RestRelationshipIndexImpl index = new RestRelationshipIndexImpl(db, client, indexName, data);
			return index;
		} catch (RestClientException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public String[] relationshipIndexNames() {
		try {
			Map<String, IndexData> indexes = client.getRelationshipIndexNames();
			return indexes.keySet().toArray(new String[] {});
		} catch (RestClientException e) {
			log.error(e);
		}
		return EMPTY_NAMES;
	}

	public RestGraphDatabase getDatabase() {
		return db;
	}

}
