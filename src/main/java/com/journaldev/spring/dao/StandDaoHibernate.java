package com.journaldev.spring.dao;

import java.util.List;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Region;
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
	public List<Stand> getStandsByTaxi(Long taxiId) {
		return null;
	}

}