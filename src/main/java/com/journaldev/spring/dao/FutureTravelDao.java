package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.FutureTravel;

public interface FutureTravelDao extends GenericDao<FutureTravel, Long> {
	
	public List<FutureTravel> getFutureTravels(Long taxiId);
	
}
