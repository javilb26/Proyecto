package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.Taxi;

public interface StandDao extends GenericDao<Stand, Long> {

	public List<Stand> getStands();

	public List<Stand> getStandsByZone(Long zoneId);

	public List<Stand> getNearestStandsByTaxi(Long taxiId);

	public Long getNumTaxisStand(Long standId);
	
	public List<Taxi> getTaxisByStand(Long standId);

}
