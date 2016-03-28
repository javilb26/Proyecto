package com.journaldev.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scaleset.geo.geojson.GeometryDeserializer;
import com.scaleset.geo.geojson.GeometrySerializer;
import com.vividsolutions.jts.geom.Point;

@Entity
public class Taxi {
	
	private long taxiId;
	private String password;
	private Point position;
	private State actualState;
	private State futureState;
	@JsonIgnore
	private Set<Travel> travels = new HashSet<Travel>();
	@JsonIgnore
	private Set<FutureTravel> futureTravels = new HashSet<FutureTravel>();
	//TODO Descomentar cuando necesite
	//private Alert alert;
	private Client client;
	
	public Taxi() {
		this.actualState = State.OFF;
		this.futureState = State.OFF;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(Long taxiId) {
		this.taxiId = taxiId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonSerialize(using = GeometrySerializer.class)
	@JsonDeserialize(using = GeometryDeserializer.class)
	@Column(columnDefinition = "geometry(Point,4326)")
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public State getActualState() {
		return actualState;
	}

	public void setActualState(State actualState) {
		this.actualState = actualState;
	}

	public State getFutureState() {
		return futureState;
	}

	public void setFutureState(State futureState) {
		this.futureState = futureState;
	}

	@OneToMany(mappedBy = "taxi")
	public Set<Travel> getTravels() {
		return travels;
	}

	public void setTravels(Set<Travel> travels) {
		this.travels = travels;
	}

	@OneToMany(mappedBy = "taxi")
	public Set<FutureTravel> getFutureTravels() {
		return futureTravels;
	}

	public void setFutureTravels(Set<FutureTravel> futureTravels) {
		this.futureTravels = futureTravels;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
