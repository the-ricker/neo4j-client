package org.neo4j.client.rest.index;

import org.neo4j.client.index.Index;
import org.neo4j.client.rest.RestPropertyContainer;

public interface RestIndex<T extends RestPropertyContainer> extends Index<T> {
	
	/**
	 * The URI of this index
	 * @return
	 */
	public String getSelf();

}
