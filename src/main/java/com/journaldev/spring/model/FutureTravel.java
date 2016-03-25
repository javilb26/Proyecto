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

import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class FutureTravel {

	private long futureTravelId;
	private Calendar date;
	@JsonBackReference
	private Country originCountry;
	@JsonBackReference
	private Region originRegion;
	@JsonBackReference
	private City originCity;
	@JsonBackReference
	private Address originAddress;
	@JsonBackReference
	private Country destinationCountry;
	@JsonBackReference
	private Region destinationRegion;
	@JsonBackReference
	private City destinationCity;
	@JsonBackReference
	private Address destinationAddress;
	private Taxi taxi;
	
	public FutureTravel() {
	}

	public FutureTravel(Calendar date, Country originCountry, Region originRegion,
			City originCity, Address originAddress, Country destinationCountry,
			Region destinationRegion, City destinationCity,
			Address destinationAddress, Taxi taxi) {
		this.date = date;
		this.originCountry = originCountry;
		this.originRegion = originRegion;
		this.originCity = originCity;
		this.originAddress = originAddress;
		this.destinationCountry = destinationCountry;
		this.destinationRegion = destinationRegion;
		this.destinationCity = destinationCity;
		this.destinationAddress = destinationAddress;
		this.taxi = taxi;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getFutureTravelId() {
		return futureTravelId;
	}

	public void setFutureTravelId(long futureTravelId) {
		this.futureTravelId = futureTravelId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
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

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "taxi")
	public Taxi getTaxi() {
		return taxi;
	}

	public void setTaxi(Taxi taxi) {
		this.taxi = taxi;
	}
	
}
