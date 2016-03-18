package com.journaldev.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.postgresql.geometric.PGpolygon;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Zone {

	private long zoneId;
	private String name;
	private PGpolygon location;
	@JsonBackReference
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

	public PGpolygon getLocation() {
		return location;
	}

	public void setLocation(PGpolygon location) {
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
