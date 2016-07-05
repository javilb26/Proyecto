package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Country;

public interface CountryDao extends GenericDao<Country, Long> {

	public List<Country> getCountries();

	public long getCountryFromRegion(long regionId)
			throws InstanceNotFoundException;

}
