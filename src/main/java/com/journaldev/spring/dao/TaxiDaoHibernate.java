package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Taxi;

@Repository("personDao")
public class TaxiDaoHibernate extends
		GenericDaoHibernate<Taxi, Long> implements TaxiDao {
	
	@SuppressWarnings("unchecked")
	public List<Taxi> getTaxis() {
		return getSession().createQuery("FROM Taxi").list();
	}

}