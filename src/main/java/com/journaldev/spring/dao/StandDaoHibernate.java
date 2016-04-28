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
	public Long getNumTaxisStand(Long standId) {
		return (Long) getSession()
				.createQuery(
						"SELECT COUNT(t) FROM Stand s JOIN Entry e ON s.standId = e.stand.standId JOIN Taxi t ON e.taxi.taxiId = t.taxiId WHERE s.standId = :standId")
				.setParameter("standId", standId).uniqueResult();
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
						"SELECT t FROM Stand s JOIN Entry e ON s.standId = e.stand.standId JOIN Taxi t ON e.taxi.taxiId = t.taxiId WHERE s.standId = :standId")
				.setParameter("standId", standId).list();
	}

}