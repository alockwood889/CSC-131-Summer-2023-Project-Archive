/**
 * Represents a transit stop.
 * Each stop has a unique identifier (id), a name, and latitude and longitude coordinates.
 * 
 * @author Jalen Grant Hall
 * @date 07/26/2023
 */
package edu.csus.csc131.transit.data;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Stop {
	
	@Id
	private String id;
	private String name;
	private double lat;
	private double lon;
	
	public Stop() {
		super();
	}
	
	public Stop(String id, String name, double lat, double lon) {
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	@Override
    public String toString() {
        return "Stop [id=" + id + ", name=" + name + ", lat=" + lat + ", lon=" + lon + "]";
    }
}


