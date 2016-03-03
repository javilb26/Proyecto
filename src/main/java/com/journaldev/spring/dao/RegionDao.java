package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Region;

public interface RegionDao extends GenericDao<Region, Long> {
	
	public List<Region> getRegions(Long countryId);
	
}
