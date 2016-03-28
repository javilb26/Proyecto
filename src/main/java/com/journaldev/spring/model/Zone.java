package com.journaldev.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scaleset.geo.geojson.GeometryDeserializer;
import com.scaleset.geo.geojson.GeometrySerializer;
import com.vividsolutions.jts.geom.MultiPolygon;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Zone {

	private long zoneId;
	private String name;
	private MultiPolygon location;
	@JsonIgnore
	private Set<Stand> stands = new HashSet<Stand>();
	
	public Zone() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getZoneId() {
		return zoneId;
	}

	public void setZoneId(long zoneId) {
		this.zoneId = zoneId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	@Column(columnDefinition = "geometry(MultiPolygon,4326)")
	public MultiPolygon getLocation() {
		return location;
	}

	public void setLocation(MultiPolygon location) {
		this.location = location;
	}

	@OneToMany(mappedBy = "zone")
	public Set<Stand> getStands() {
		return stands;
	}

	public void setStands(Set<Stand> stands) {
		this.stands = stands;
	}

}
