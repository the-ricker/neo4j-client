package org.neo4j.client.rest.index.impl;

import java.util.Map;

import org.neo4j.client.Node;
import org.neo4j.client.PropertyContainer;
import org.neo4j.client.index.Index;
import org.neo4j.client.index.IndexManager;
import org.neo4j.client.index.RelationshipIndex;
import org.neo4j.client.rest.client.RestClient;

public class RestIndexManagerImpl implements IndexManager {
	
	private RestClient loader;
	
	public RestIndexManagerImpl(RestClient loader) {
		this.loader = loader;
	}

	@Override
	public boolean existsForNodes(String indexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Index<Node> forNodes(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Index<Node> forNodes(String indexName, Map<String, String> customConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] nodeIndexNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsForRelationships(String indexName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RelationshipIndex forRelationships(String indexName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RelationshipIndex forRelationships(String indexName, Map<String, String> customConfiguration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] relationshipIndexNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getConfiguration(Index<? extends PropertyContainer> index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setConfiguration(Index<? extends PropertyContainer> index, String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeConfiguration(Index<? extends PropertyContainer> index, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
