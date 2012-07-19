/**
 * 
 */
package org.neo4j.client.rest.index.impl;

import org.neo4j.client.GraphDatabase;
import org.neo4j.client.index.IndexHits;
import org.neo4j.client.rest.RestPropertyContainer;
import org.neo4j.client.rest.client.IndexData;
import org.neo4j.client.rest.index.RestIndex;

/**
 * @author Ricker
 *
 */
public class RestIndexImpl<T extends RestPropertyContainer> implements RestIndex<T> {

	private RestIndexManagerImpl manager;
	private String name;
	private IndexData data;
	
	public RestIndexImpl(RestIndexManagerImpl manager, String name, IndexData data) {
		this.manager = manager;
		this.name = name;
		this.data = data;
	}
	
	@Override
	public void add(T entity, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(T entity, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(T entity, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T putIfAbsent(T entity, String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<T> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<T> get(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<T> query(String key, Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IndexHits<T> query(Object queryOrQueryObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWriteable() {
		return true;
	}

	@Override
	public GraphDatabase getGraphDatabase() {
		return manager.getDatabase();
	}

	@Override
	public String getSelf() {
		return data.getSelf();
	}

}
