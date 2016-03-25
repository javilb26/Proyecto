package com.journaldev.spring.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;
import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Immutable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Client {

	private long clientId;
	@JsonBackReference
	private Country originCountry;
	@JsonBackReference
	private Region originRegion;
	@JsonBackReference
	private City originCity;
	@JsonBackReference
	private Address originAddress;
	private Calendar entry;
	@JsonIgnore
	private PGpoint location;

	public Client() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "originCountry")
	public Country getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "originRegion")
	public Region getOriginRegion() {
		return originRegion;
	}

	public void setOriginRegion(Region originRegion) {
		this.originRegion = originRegion;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "originCity")
	public City getOriginCity() {
		return originCity;
	}

	public void setOriginCity(City originCity) {
		this.originCity = originCity;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "originAddress")
	public Address getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(Address originAddress) {
		this.originAddress = originAddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getEntry() {
		return entry;
	}

	public void setEntry(Calendar entry) {
		this.entry = entry;
	}

	public PGpoint getLocation() {
		return location;
	}

	public void setLocation(PGpoint location) {
		this.location = location;
	}

}
