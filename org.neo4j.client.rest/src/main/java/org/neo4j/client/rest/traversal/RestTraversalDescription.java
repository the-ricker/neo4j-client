/**
 * 
 */
package org.neo4j.client.rest.traversal;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.client.Direction;
import org.neo4j.client.Node;
import org.neo4j.client.RelationshipType;
import org.neo4j.client.rest.RestNode;
import org.neo4j.client.rest.traversal.impl.RelationshipFilter;
import org.neo4j.client.traversal.Order;
import org.neo4j.client.traversal.ReturnFilter;
import org.neo4j.client.traversal.ReturnType;
import org.neo4j.client.traversal.Script;
import org.neo4j.client.traversal.TraversalDescription;
import org.neo4j.client.traversal.TraversalException;
import org.neo4j.client.traversal.Traverser;
import org.neo4j.client.traversal.Uniqueness;

/**
 * @author Ricker
 * 
 */
public class RestTraversalDescription implements TraversalDescription {

	private Uniqueness uniqueness;
	private List<RelationshipFilter> relationships;
	private ReturnType returnType;
	private Object returnFilter;
	private Order order;
	private int depth;
	private Script pruneEvaluator;

	public RestTraversalDescription() {
		this.uniqueness = Uniqueness.NONE;
		this.relationships = new ArrayList<RelationshipFilter>();
		this.returnType = ReturnType.PATH;
		this.returnFilter = null;
		this.order = Order.BREADTH_FIRST;
		this.depth = -1;
		this.pruneEvaluator = null;
	}

	private RestTraversalDescription(Uniqueness uniqueness, List<RelationshipFilter> relationships, ReturnType returnType,
			Object returnFilter, Order order, int depth, Script pruneEvaluator) {
		this.uniqueness = uniqueness;
		this.relationships = relationships;
		this.returnType = returnType;
		this.returnFilter = returnFilter;
		this.order = order;
		this.depth = depth;
		this.pruneEvaluator = pruneEvaluator;
	}

	@Override
	public TraversalDescription uniqueness(Uniqueness uniqueness) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
			 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription relationships(RelationshipType type, Direction direction) {
		relationships.add(new RelationshipFilter(type,direction));
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription returnType(ReturnType type) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription returnFilter(ReturnFilter filter) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription returnFilter(Script filter) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription pruneEvaluator(Script evaluator) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription maxDepth(int depth) {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  order,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription depthFirst() {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  Order.DEPTH_FIRST,  depth,  pruneEvaluator);
	}

	@Override
	public TraversalDescription breadthFirst() {
		return new RestTraversalDescription( uniqueness,  relationships,  returnType,
				 returnFilter,  Order.BREADTH_FIRST,  depth,  pruneEvaluator);
	}

	@Override
	public Traverser traverse(Node start) throws TraversalException {
		if (start instanceof RestNode) {
			return ((RestNode)start).getGraphDatabase().traverse(this, (RestNode)start);
		} 
		throw new IllegalArgumentException("Nodes should be instanceof RestNode");
	}

	public Uniqueness getUniqueness() {
		return uniqueness;
	}

	public List<RelationshipFilter> getRelationships() {
		return relationships;
	}

	public ReturnType getReturnType() {
		return returnType;
	}

	public Object getReturnFilter() {
		return returnFilter;
	}

	public Order getOrder() {
		return order;
	}

	public int getDepth() {
		return depth;
	}

	public Script getPruneEvaluator() {
		return pruneEvaluator;
	}

	
	
	
}
