/**
 * 
 */
package org.neo4j.client.rest.index.impl;

import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestPropertyContainer;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.client.RestClient;
import org.neo4j.client.rest.index.RestIndex;

/**
 * @author Ricker
 *
 */
public abstract class RestIndexImpl<T extends RestPropertyContainer> implements RestIndex<T> {

	protected RestClient client;
	private RestGraphDatabase db;
	private String name;
	private IndexData data;
	
	public RestIndexImpl(RestGraphDatabase db, RestClient client, String name, IndexData data) {
		this.client = client;
		this.name = name;
		this.data = data;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isWriteable() {
		return true;
	}

	@Override
	public RestGraphDatabase getGraphDatabase() {
		return db;
	}

	@Override
	public String getSelf() {
		return data.getSelf();
	}

}
