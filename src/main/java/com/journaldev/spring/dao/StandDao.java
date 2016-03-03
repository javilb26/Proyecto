package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Stand;

public interface StandDao extends GenericDao<Stand, Long> {
	
	public List<Stand> getStandsByZone(Long zoneId);
	
	public List<Stand> getStandsByTaxi(Long taxiId);
	
}
