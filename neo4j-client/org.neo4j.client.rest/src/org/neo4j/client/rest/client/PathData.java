/**
 * 
 */
package org.neo4j.client.rest.client;

/**
 * @author Ricker
 * 
 */
public class PathData {

	/*
	 * { "start" : "http://localhost:7474/db/data/node/9", "nodes" : [
	 * "http://localhost:7474/db/data/node/9",
	 * "http://localhost:7474/db/data/node/8" ], "length" : 1, "relationships" :
	 * [ "http://localhost:7474/db/data/relationship/3" ], "end" :
	 * "http://localhost:7474/db/data/node/8"}
	 */

	private String start;
	private String end;
	private int length;
	private String[] nodes;
	private String[] relationships;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String[] getNodes() {
		return nodes;
	}

	public void setNodes(String[] nodes) {
		this.nodes = nodes;
	}

	public String[] getRelationships() {
		return relationships;
	}

	public void setRelationships(String[] relationships) {
		this.relationships = relationships;
	}

}
