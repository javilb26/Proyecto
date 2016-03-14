package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Region;

@Repository("regionDao")
public class RegionDaoHibernate extends GenericDaoHibernate<Region, Long>
		implements RegionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Region> getRegions(Long countryId) {
		return getSession()
				.createQuery(
						"SELECT r FROM Region r WHERE r.country.countryId = :countryId ORDER BY r.name")
				.setParameter("countryId", countryId).list();
	}

}