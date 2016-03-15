package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Stand;

@Repository("standDao")
public class StandDaoHibernate extends GenericDaoHibernate<Stand, Long>
		implements StandDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Stand> getStandsByZone(Long zoneId) {
		return getSession()
				.createQuery(
						"SELECT s FROM Stand s WHERE s.zone = :zoneId ORDER BY s.name")
				.setParameter("zoneId", zoneId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stand> getNearestStandsByTaxi(Long taxiId) {
		return getSession()
				.createQuery(
						"SELECT s FROM Stand s JOIN Address a ON s.address = a.addressId JOIN City c ON a.city = c.cityId ORDER BY ST_Distance((SELECT position FROM Taxi WHERE taxiId = :taxiId),s.location) ASC LIMIT 5")
				.setParameter("taxiId", taxiId).list();
	}

}