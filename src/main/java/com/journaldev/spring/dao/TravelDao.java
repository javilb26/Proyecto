package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Travel;

public interface TravelDao extends GenericDao<Travel, Long> {
	
	public List<Travel> getTravels(Long taxiId);
	
}
