package org.neo4j.client.traversal;

/**
 * Provides metadata about a traversal.
 * 
 */
public interface TraversalMetadata {

	/**
     * @return number of paths returned up to this point in the traversal.
     */
    int getNumberOfPathsReturned();
    
    /**
     * @return number of relationships traversed up to this point in the traversal.
     * Some relationships in this counter might be unnecessarily traversed relationships,
     * but at the same time it gives an accurate measure of how many relationships are
     * requested from the underlying graph. Useful for comparing and first-level debugging
     * of queries.
     */
    int getNumberOfRelationshipsTraversed();
}
