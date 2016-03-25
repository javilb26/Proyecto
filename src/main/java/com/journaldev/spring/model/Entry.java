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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Immutable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Entry {

	private long entryId;
	@JsonManagedReference
	private Taxi taxi;
	@JsonManagedReference
	private Stand stand;
	private Calendar arrival;
	
	public Entry() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "taxi")
	public Taxi getTaxi() {
		return taxi;
	}

	public void setTaxi(Taxi taxi) {
		this.taxi = taxi;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name = "stand")
	public Stand getStand() {
		return stand;
	}

	public void setStand(Stand stand) {
		this.stand = stand;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getArrival() {
		return arrival;
	}

	public void setArrival(Calendar arrival) {
		this.arrival = arrival;
	}

}