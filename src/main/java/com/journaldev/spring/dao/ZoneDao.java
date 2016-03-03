package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Zone;

public interface ZoneDao extends GenericDao<Zone, Long> {
	
	public List<Zone> getZones();
	
}
