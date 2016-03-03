package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.City;

public interface CityDao extends GenericDao<City, Long> {
	
	public List<City> getCities(Long regionId);
	
}
