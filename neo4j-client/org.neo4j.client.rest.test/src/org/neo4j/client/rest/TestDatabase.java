/**
 * 
 */
package org.neo4j.client.rest;

import java.net.URI;

import org.junit.Test;
import org.neo4j.client.DynamicRelationshipType;
import org.neo4j.client.Node;
import org.neo4j.client.Relationship;

/**
 * @author e523171
 *
 */
public class TestDatabase {
	
	@Test
	public void testConnection() throws Exception {
		RestGraphDatabaseFactory factory = new RestGraphDatabaseFactory();
		RestGraphDatabase db = factory.createGraphDatabase(new URI("http://localhost:7474/db/data/"));
		Node root = db.getReferenceNode();
		Node node = db.createNode();
		node.setProperty("foo", "bar");
		Relationship rel  = root.createRelationshipTo(node, DynamicRelationshipType.withName("test"));
	}

}
