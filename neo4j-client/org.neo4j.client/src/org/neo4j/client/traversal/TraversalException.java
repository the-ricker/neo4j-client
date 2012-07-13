/**
 * 
 */
package org.neo4j.client.traversal;

/**
 * @author Ricker
 *
 */
public class TraversalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8022168887066526584L;

	/**
	 * 
	 */
	public TraversalException() {
	}

	/**
	 * @param message
	 */
	public TraversalException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TraversalException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TraversalException(String message, Throwable cause) {
		super(message, cause);
	}

	
}
