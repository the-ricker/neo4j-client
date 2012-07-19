/**
 * 
 */
package org.neo4j.client.rest.client;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ricker
 *
 */
public class NodeData extends PropertyContainerData {
	
	private String all_relationships;
	private String create_relationship;
	
	/**
	 * Read but do not write in JSON
	 * @return
	 */
	@JsonIgnore
	public String getAll_relationships() {
		return all_relationships;
	}

	@JsonProperty
	public void setAll_relationships(String path) {
		this.all_relationships = path;
	}
	
	@JsonIgnore
	public String getCreate_relationship() {
		return create_relationship;
	}
	
	@JsonProperty
	public void setCreate_relationship(String path){
		this.create_relationship = path;
	}

}
