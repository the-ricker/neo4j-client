/**
 * 
 */
package org.neo4j.client.traversal;

/**
 * 
 * @author Ricker
 *
 */
public interface Script {
	
	/**
	 * The language, eg. JavaScript
	 * @return
	 */
	public String getLanguage();
	
	/**
	 * The script code
	 * @return
	 */
	public String getBody();

}
