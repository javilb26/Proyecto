package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Travel;

@Repository("TravelDao")
public class TravelDaoHibernate extends
		GenericDaoHibernate<Travel, Long> implements TravelDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Travel> getTravels(Long taxiId) {
		return getSession()
				.createQuery(
						"SELECT t FROM Travel t WHERE t.taxi = :taxiId ORDER BY t.time")
				.setParameter("taxiId", taxiId).list();
	}

}