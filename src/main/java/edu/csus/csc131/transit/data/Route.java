/**
 * Represents a transit route.
 * Each route has a unique identifier (id), a short name, and a long name.
 *
 * @author Jalen Grant Hall
 * @date 07/24/2023
 */
package edu.csus.csc131.transit.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Route {
	
	@Id
	// Fields
	private String id;
	private String shortName;
	private String longName;
	
	public Route() {
		super();
	}

	public Route(String id, String shortName, String longName) {
		this.id = id;
		this.shortName = shortName;
		this.longName = longName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}
	
	@Override
    public String toString() {
        return "Route [id=" + id + ", shortName=" + shortName + ", longName=" + longName + "]";
    }
}
