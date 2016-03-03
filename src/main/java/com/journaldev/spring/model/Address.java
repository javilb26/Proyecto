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

import org.postgresql.geometric.PGline;

@Entity
public class Address {

	private long addressId;
	private String name;
	private PGline location;
	private City city;
	private Set<Stand> stands;
	
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

	public PGline getLocation() {
		return location;
	}

	public void setLocation(PGline location) {
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
