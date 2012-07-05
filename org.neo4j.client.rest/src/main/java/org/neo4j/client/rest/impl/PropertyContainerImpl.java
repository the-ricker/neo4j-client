/**
 * 
 */
package org.neo4j.client.rest.impl;

import java.util.Map;

import org.neo4j.client.PropertyContainer;
import org.neo4j.client.rest.RestGraphDatabase;

/**
 * @author Ricker
 * 
 */
public abstract class PropertyContainerImpl implements PropertyContainer {

	protected RestGraphDatabaseImpl graphDatabase;
	
	private boolean dirty;

	public PropertyContainerImpl(RestGraphDatabaseImpl graphDatabase) {
		this.graphDatabase = graphDatabase;
	}

	@Override
	public RestGraphDatabase getGraphDatabase() {
		return graphDatabase;
	}

	@Override
	public boolean hasProperty(String key) {
		return getData().containsKey(key);
	}

	@Override
	public Object getProperty(String key) {
		return getData().get(key);
	}

	@Override
	public Object getProperty(String key, Object defaultValue) {
		Object val = getData().get(key);
		if (val != null) {
			return val;
		}
		return defaultValue;
	}

	@Override
	public void setProperty(String key, Object value) {
		getData().put(key, value);
		setDirty(true);
	}

	@Override
	public Object removeProperty(String key) {
		setDirty(true);
		return getData().remove(key);
	}

	@Override
	public Iterable<String> getPropertyKeys() {
		return getData().keySet();
	}

	/**
	 * Must not return null
	 * 
	 * @return
	 */
	protected abstract Map<String, Object> getData();

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	

}
