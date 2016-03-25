package com.journaldev.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.postgresql.geometric.PGpoint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Taxi {
	
	private long taxiId;
	private String password;
	private PGpoint position;
	private State actualState;
	private State futureState;
	private Set<Travel> travels = new HashSet<Travel>();
	private Set<FutureTravel> futureTravels = new HashSet<FutureTravel>();
	//private Alert alert;
	@JsonManagedReference
	private Client client;
	
	public Taxi() {
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

	public PGpoint getPosition() {
		return position;
	}

	public void setPosition(PGpoint position) {
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
