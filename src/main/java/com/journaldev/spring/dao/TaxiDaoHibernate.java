package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.Zone;
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
	public Taxi getNearestAvailableTaxi(Point position) {
		/*return (Taxi) getSession()
				.createQuery(
						"SELECT t FROM Taxi t WHERE t.actualState = 1 ORDER BY ST_Distance(:position, t.position)").setMaxResults(1)
				.setParameter("position", position).uniqueResult();*/
		Zone z = (Zone) getSession().createQuery("SELECT z FROM Zone z WHERE ST_Contains(z.location, :position) = true").setParameter("position", position).uniqueResult();
		MultiPolygon location = z.getLocation();
		return (Taxi) getSession()
				.createQuery(
						"SELECT t FROM Taxi t WHERE t.actualState = 1 AND ST_Contains(:location, t.position) = true ORDER BY ST_Distance(:position, t.position)").setMaxResults(1)
				.setParameter("position", position).setParameter("location", location).uniqueResult();
	}

}