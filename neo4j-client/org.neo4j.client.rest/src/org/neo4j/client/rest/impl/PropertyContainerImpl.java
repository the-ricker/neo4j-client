/**
 * 
 */
package org.neo4j.client.rest.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.client.PropertyContainer;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;

/**
 * @author Ricker
 * 
 */
public abstract class PropertyContainerImpl<D extends PropertyContainerData> implements PropertyContainer {

	protected RestGraphDatabaseImpl graphDatabase;

	private static Log log = LogFactory.getLog(PropertyContainerImpl.class);

	public PropertyContainerImpl(RestGraphDatabaseImpl graphDatabase) {
		this.graphDatabase = graphDatabase;
	}

	@Override
	public RestGraphDatabase getGraphDatabase() {
		return graphDatabase;
	}

	@Override
	public boolean hasProperty(String key) {
		return getData().getData().containsKey(key);
	}

	@Override
	public Object getProperty(String key) {
		return getData().getData().get(key);
	}

	@Override
	public Object getProperty(String key, Object defaultValue) {
		Object val = getData().getData().get(key);
		if (val != null) {
			return val;
		}
		return defaultValue;
	}

	@Override
	public void setProperty(String key, Object value) {
		try {
			graphDatabase.setProperty(this, key, value);
			getData().getData().put(key, value);
		} catch (RestClientException e) {
			log.error("Could not set property", e);
		}
	}

	@Override
	public Object removeProperty(String key) {
		try {
			graphDatabase.removeProperty(this, key);
			return getData().getData().remove(key);
		} catch (RestClientException e) {
			log.error("Could not remove property", e);
		}
		return null;
	}

	@Override
	public Iterable<String> getPropertyKeys() {
		return getData().getData().keySet();
	}

	public abstract void setData(D data);

	public abstract D getData();

}
