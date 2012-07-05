/**
 * 
 */
package org.neo4j.client.rest.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Ricker
 *
 */
public class NewRelationshipData {
	
	private String to;
	
	private String type;
	
	public NewRelationshipData(){}
	
	public NewRelationshipData(String to, String type){
		this.to = to;
		this.type = type;
	}

	@JsonProperty
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@JsonProperty
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
