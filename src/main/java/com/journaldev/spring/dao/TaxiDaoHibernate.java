package com.journaldev.spring.dao;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Entry;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.TaxiState;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;

@Repository("taxiDao")
public class TaxiDaoHibernate extends GenericDaoHibernate<Taxi, Long> implements
		TaxiDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getTaxis() {
		return getSession().createQuery("FROM Taxi").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getOperatingTaxis() {
		return getSession().createQuery(
				"SELECT t FROM Taxi t WHERE t.actualState <> 0").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getAvailableTaxis() {
		return getSession().createQuery(
				"SELECT t FROM Taxi t WHERE t.actualState = 1").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Taxi getNearestAvailableTaxi(Point position) throws Exception {
		City c = (City) getSession().createQuery("SELECT c FROM City c WHERE ST_Contains(c.location, :position) = true").setParameter("position", position).uniqueResult();
		System.out.println("Ciudad 1: " + c.getCityId());
		MultiPolygon location = c.getLocation();
		System.out.println("Ciudad 1 Location: " + c.getLocation().toText());
		Taxi t = (Taxi) getSession()
				.createQuery(
						"SELECT t FROM Taxi t WHERE t.actualState = 1 AND ST_Contains(:location, t.position) = true ORDER BY ST_Distance(:position, t.position)").setMaxResults(1)
				.setParameter("position", position).setParameter("location", location).uniqueResult();
		Double distanceToTaxi = (double) 999999999;
		if (t!=null) {
			Long taxiId = t.getTaxiId();
			System.out.println("Taxi: " + t.getTaxiId());
			distanceToTaxi = (Double) getSession()
					.createSQLQuery(
							"SELECT ST_Distance(:position, t.position) as distance FROM Taxi t WHERE t.taxiId = :taxiId").addScalar("distance").setParameter("position", position).setParameter("taxiId", taxiId).uniqueResult();
			System.out.println("Distance to taxi: " + distanceToTaxi);
		}
		
		Long cityId = c.getCityId();
		System.out.println("Ciudad 2: " + cityId);
		List<Stand> stands = (List<Stand>) getSession()
				.createQuery(
						"SELECT s FROM Stand s JOIN Address a ON s.address.addressId = a.addressId JOIN City c ON a.city.cityId = c.cityId "
								+ "WHERE c.cityId = :cityId ORDER BY ST_Distance(:position, s.location) ASC")
				.setParameter("position", position).setParameter("cityId", cityId).list();
		Long standId = (long) 0;
		for (Stand aux: stands) {
			if (aux.getEntries().isEmpty()) {
			} else {
				Set<Entry> entries = aux.getEntries();
				for (Entry entry: entries) {
					if (entry.getTaxi().getActualState().compareTo(TaxiState.INSTAND)==0) {
						standId = aux.getStandId();
						System.out.println("Stand: " + standId);
					}
				}
			}
		}
		Double distanceToStand;
		if (standId==0) {
			System.out.println("No taxi in any stand");
			distanceToStand = (double) 999999999;
			System.out.println("distanceToStand: "+distanceToStand);
		} else {
			distanceToStand = (Double) getSession()
					.createSQLQuery(
							"SELECT ST_Distance(:position, s.location) as distance FROM Stand s WHERE s.standId = :standId").addScalar("distance").setParameter("position", position).setParameter("standId", standId).uniqueResult();
			System.out.println("Distance to stand: " + distanceToStand);
		}
		if (distanceToTaxi < distanceToStand) {
			System.out.println("Entro por 1");
			return t;
		} else  if (distanceToTaxi > distanceToStand){
			System.out.println("Entro por 2");
			Taxi taxiFromStand = (Taxi) getSession()
			.createQuery(
					"SELECT e.taxi FROM Entry e WHERE e.stand.standId = :standId").setParameter("standId", standId).setMaxResults(1).uniqueResult();
			return taxiFromStand;
		} else {
			System.out.println("Entro por 3");
			throw new Exception("No taxi available and no taxi in stand");
		}
	}

}