package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.City;

@Repository("cityDao")
public class CityDaoHibernate extends GenericDaoHibernate<City, Long> implements
		CityDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<City> getCities(Long regionId) {
		return getSession()
				.createQuery(
						"SELECT c FROM City c WHERE c.region.regionId = :regionId ORDER BY c.name")
				.setParameter("regionId", regionId).list();
	}

	@Override
	public long getCityFromAddress(long addressId)
			throws InstanceNotFoundException {
		return (Long) getSession()
				.createQuery(
						"SELECT a.city.cityId FROM Address a WHERE a.addressId = :addressId")
				.setParameter("addressId", addressId).uniqueResult();
	}

}