package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Taxi;

@Repository("taxiDao")
public class TaxiDaoHibernate extends
		GenericDaoHibernate<Taxi, Long> implements TaxiDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getTaxis() {
		return getSession().createQuery("FROM Taxi").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getOperatingTaxis() {
		return getSession().createQuery("SELECT t FROM Taxi t WHERE t.actualState <> 0").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Taxi> getAvailableTaxis() {
		return getSession().createQuery("SELECT t FROM Taxi t WHERE t.actualState = 1").list();
	}

}