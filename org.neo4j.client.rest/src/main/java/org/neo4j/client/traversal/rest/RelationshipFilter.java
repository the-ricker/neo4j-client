/**
 * 
 */
package org.neo4j.client.traversal.rest;

import org.neo4j.client.Direction;
import org.neo4j.client.RelationshipType;

/**
 * @author Ricker
 * 
 */
public class RelationshipFilter {

	private RelationshipType type;

	private Direction direction;

	public RelationshipFilter(RelationshipType type, Direction direction) {
		this.type = type;
		this.direction = direction;
	}

	public RelationshipType getType() {
		return type;
	}

	public Direction getDirection() {
		return direction;
	}

}
