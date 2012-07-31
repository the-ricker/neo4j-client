/**
 * 
 */
package org.neo4j.client.rest.index.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.index.IndexHits;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.client.RestClient;

/**
 * @author Ricker
 * 
 */
public class RestNodeIndexImpl extends RestIndexImpl<RestNode> {

	private static Log log = LogFactory.getLog(RestNodeIndexImpl.class);

	public RestNodeIndexImpl(RestGraphDatabase db, RestClient client, String name, IndexData data) {
		super(db, client, name, data);
	}

	@Override
	public void add(RestNode entity, String key, Object value) {
		try {
			client.addNodeIndex(getName(), entity.getSelf(), key, value);
		} catch (RestClientException e) {
			log.error(e);
		}
	}

	@Override
	public void remove(RestNode entity, String key, Object value) {
		try {
			client.removeNodeIndex(getName(), entity.getId(), key, value);
		} catch (RestClientException e) {
			log.error(e);
		}
	}

	@Override
	public void remove(RestNode entity, String key) {
		remove(entity, key, null);

	}

	@Override
	public void remove(RestNode entity) {
		remove(entity, null, null);
	}

	@Override
	public void delete() {
		try {
			client.deleteNodeIndex(getName());
		} catch (RestClientException e) {
			log.error(e);
		}
	}

	@Override
	public RestNode putIfAbsent(RestNode entity, String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<RestNode> getEntityType() {
		return RestNode.class;
	}

	@Override
	public IndexHits<RestNode> get(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<RestNode> query(String key, Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<RestNode> query(Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
