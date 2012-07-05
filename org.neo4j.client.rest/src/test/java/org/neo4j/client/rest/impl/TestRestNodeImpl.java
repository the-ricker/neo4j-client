/**
 * 
 */
package org.neo4j.client.rest.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.client.DynamicRelationshipType;
import org.neo4j.client.rest.RestClientException;
import org.neo4j.client.rest.RestGraphDatabase;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.RestRelationship;

/**
 * 
 * THIS IS AN INTEGRATION TEST.
 * 
 * @author Ricker
 *
 */
public class TestRestNodeImpl {
	
	static RestGraphDatabaseImpl db; 
	
	@BeforeClass
	public static void setUp() throws URISyntaxException {
		db = new RestGraphDatabaseImpl();
		db.setURI(RestGraphDatabase.DEFAULT_URI);
	}
	
	@AfterClass
	public static void tearDown() throws IOException {
		db.close();
	}
	
	@Test
	public void testNodeLifecycle() throws RestClientException {
		RestNode node = db.createNode();
		assertTrue(node.getId() > 0);
		assertFalse(node.isDeleted());
		
		node.setProperty("foo", "bar");

		node.delete();
		assertTrue(node.isDeleted());
	}
	
	@Test 
	public void testNonexistantNode() {
		RestNode node = db.getNodeById(80000);
		assertNotNull(node);
		assertTrue(node.isDeleted());
	}

	@Test
	public void testCreateRelationship() {
		/*
		 * create
		 */
		RestNode start = db.createNode();
		RestNode end = db.createNode();
		RestRelationship relation = (RestRelationship)start.createRelationshipTo(end, DynamicRelationshipType.withName("test"));
		assertNotNull(relation);
		assertEquals(start, relation.getStartNode());
		assertEquals(end, relation.getEndNode());
		assertEquals("test", relation.getType().name());

		assertFalse(relation.isDeleted());

		/*
		 * delete
		 */
		relation.delete();
		assertTrue(relation.isDeleted());
		start.delete();
		end.delete();
	}
	
}
