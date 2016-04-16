package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.Taxi;

@Repository("standDao")
public class StandDaoHibernate extends GenericDaoHibernate<Stand, Long>
		implements StandDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Stand> getStands() {
		return getSession().createQuery("FROM Stand").list();
	}

	@Override
	public Integer getNumTaxisStand(Long standId) {
		return (Integer) getSession()
				.createQuery(
						"SELECT COUNT(e.taxi) FROM Stand s JOIN Entry e ON s.standId = e.stand.standId")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stand> getStandsByZone(Long zoneId) {
		return getSession()
				.createQuery(
						"SELECT s FROM Stand s WHERE s.zone.zoneId = :zoneId ORDER BY s.name")
				.setParameter("zoneId", zoneId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stand> getNearestStandsByTaxi(Long taxiId) {
		return getSession()
				.createQuery(
						"SELECT s FROM Stand s JOIN Address a ON s.address.addressId = a.addressId JOIN City c ON a.city.cityId = c.cityId "
						+ "ORDER BY ST_Distance((SELECT position FROM Taxi WHERE taxiId = :taxiId),s.location) ASC")
				.setParameter("taxiId", taxiId).setMaxResults(5).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getTaxisByStand(Long standId) {
		return getSession()
				.createQuery(
						"SELECT e.taxi FROM Stand s JOIN Entry e ON s.standId = e.stand.standId")
				.list();
	}

}