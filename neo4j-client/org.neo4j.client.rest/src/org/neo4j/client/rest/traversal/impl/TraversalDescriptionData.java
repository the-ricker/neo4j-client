/**
 * 
 */
package org.neo4j.client.rest.traversal.impl;

import java.util.List;

import org.neo4j.client.rest.traversal.RestTraversalDescription;
import org.neo4j.client.traversal.Order;
import org.neo4j.client.traversal.Script;
import org.neo4j.client.traversal.Uniqueness;

/**
 * @author Ricker
 * 
 */
public class TraversalDescriptionData {

	private Uniqueness uniqueness;
	private List<RelationshipFilter> relationships;
	private Object returnFilter;
	private Order order;
	private int depth;
	private Script pruneEvaluator;

	public TraversalDescriptionData(Uniqueness uniqueness, List<RelationshipFilter> relationships, Object returnFilter,
			Order order, int depth, Script pruneEvaluator) {
		this.uniqueness = uniqueness;
		this.relationships = relationships;
		this.returnFilter = returnFilter;
		this.order = order;
		this.depth = depth;
		this.pruneEvaluator = pruneEvaluator;
	}

	public TraversalDescriptionData(RestTraversalDescription description) {
		this.uniqueness = description.getUniqueness();
		this.relationships = description.getRelationships();
		this.returnFilter = description.getReturnFilter();
		this.order = description.getOrder();
		this.depth = description.getDepth();
		this.pruneEvaluator = description.getPruneEvaluator();
	}
	
	public String getUniqueness() {
		return uniqueness.name().toLowerCase();
	}

	public List<RelationshipFilter> getRelationships() {
		return relationships;
	}

	public Object getReturnFilter() {
		return returnFilter;
	}

	public String getOrder() {
		return order.name().toLowerCase();
	}

	public int getDepth() {
		return depth;
	}

	public Script getPruneEvaluator() {
		return pruneEvaluator;
	}
}
