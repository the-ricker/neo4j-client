/**
 * 
 */
package org.neo4j.client.rest.index.impl;

import org.neo4j.client.GraphDatabase;
import org.neo4j.client.index.IndexHits;
import org.neo4j.client.rest.RestPropertyContainer;
import org.neo4j.client.rest.impl.RestGraphDatabaseImpl;
import org.neo4j.client.rest.index.RestIndex;

/**
 * @author Ricker
 *
 */
public class RestIndexImpl<T extends RestPropertyContainer> implements RestIndex<T> {

	private RestGraphDatabaseImpl db;
	private String name;
	
	public RestIndexImpl(RestGraphDatabaseImpl db, String name) {
		this.db = db;
		this.name = name;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GraphDatabase getGraphDatabase() {
		return db;
	}

	@Override
	public String getSelf() {
		// TODO Auto-generated method stub
		return null;
	}

}
