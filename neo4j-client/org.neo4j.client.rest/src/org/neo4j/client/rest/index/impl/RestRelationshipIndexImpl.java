package org.neo4j.client.rest.index.impl;

import org.neo4j.client.index.IndexHits;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestRelationship;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.client.RestClient;

public class RestRelationshipIndexImpl extends RestIndexImpl<RestRelationship> {

	public RestRelationshipIndexImpl(RestGraphDatabase db, RestClient client, String name, IndexData data) {
		super(db, client, name, data);
	}

	@Override
	public void add(RestRelationship entity, String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(RestRelationship entity, String key, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(RestRelationship entity, String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(RestRelationship entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public RestRelationship putIfAbsent(RestRelationship entity, String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<RestRelationship> getEntityType() {
		return RestRelationship.class;
	}

	@Override
	public IndexHits<RestRelationship> get(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<RestRelationship> query(String key, Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<RestRelationship> query(Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
