package com.journaldev.spring.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.postgresql.geometric.PGpolygon;

@Entity
public class Country {

	private long countryId;
	private String name;
	private PGpolygon location;
	private Set<Region> regions;
	
	public Country() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
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

	@OneToMany(mappedBy = "country")
	public Set<Region> getRegions() {
		return regions;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
	}

}
