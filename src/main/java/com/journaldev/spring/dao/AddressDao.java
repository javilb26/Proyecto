package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Address;
import com.vividsolutions.jts.geom.Point;

public interface AddressDao extends GenericDao<Address, Long> {
	
	public List<Address> getAddresses(Long cityId);

	public long getNearestAddress(Point position);
	
}
