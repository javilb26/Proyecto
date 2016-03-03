package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Taxi;

public interface TaxiDao extends GenericDao<Taxi, Long> {
	
	public List<Taxi> getTaxis();
	
}
