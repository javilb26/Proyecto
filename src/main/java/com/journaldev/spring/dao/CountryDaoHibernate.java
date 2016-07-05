package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Country;

@Repository("countryDao")
public class CountryDaoHibernate extends GenericDaoHibernate<Country, Long>
		implements CountryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountries() {
		return getSession().createQuery("FROM Country").list();
	}

	@Override
	public long getCountryFromRegion(long regionId)
			throws InstanceNotFoundException {
		return (Long) getSession()
				.createQuery(
						"SELECT r.country.countryId FROM Region r WHERE r.regionId = :regionId")
				.setParameter("regionId", regionId).uniqueResult();
	}

}