package com.journaldev.spring.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.postgresql.geometric.PGpolygon;

@Entity
public class City {

	private long cityId;
	private String name;
	private PGpolygon location;
	private Region region;
	private Set<Address> addresses;
	
	public City() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
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

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "region")
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@OneToMany(mappedBy = "city")
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

}
