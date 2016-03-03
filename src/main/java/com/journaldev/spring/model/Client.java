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

@Entity
@Immutable
public class Client {

	private long clientId;
	private Country country;
	private Region region;
	private City city;
	private Address address;
	private Country destinationCountry;
	private Region destinationRegion;
	private City destinationCity;
	private Address destinationAddress;
	private Calendar entry;
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
	@JoinColumn(name = "country")
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "region")
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "city")
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "address")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "destinationCountry")
	public Country getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(Country destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "destinationRegion")
	public Region getDestinationRegion() {
		return destinationRegion;
	}

	public void setDestinationRegion(Region destinationRegion) {
		this.destinationRegion = destinationRegion;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "destinationCity")
	public City getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(City destinationCity) {
		this.destinationCity = destinationCity;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "destinationAddress")
	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(Address destinationAddress) {
		this.destinationAddress = destinationAddress;
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
