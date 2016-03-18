package com.journaldev.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vividsolutions.jts.geom.MultiLineString;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address {

	private long addressId;
	private String name;
	@JsonIgnore
	private MultiLineString location;
	@JsonManagedReference
	private City city;
	@JsonBackReference
	private Set<Stand> stands = new HashSet<Stand>();
	
	public Address() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultiLineString getLocation() {
		return location;
	}

	public void setLocation(MultiLineString location) {
		this.location = location;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "city")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@OneToMany(mappedBy = "address")
	public Set<Stand> getStands() {
		return stands;
	}

	public void setStands(Set<Stand> stands) {
		this.stands = stands;
	}

}
