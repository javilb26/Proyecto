package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Country;

@Repository("CountryDao")
public class CountryDaoHibernate extends
		GenericDaoHibernate<Country, Long> implements CountryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Country> getCountries() {
		return getSession().createQuery("FROM Country").list();
	}

}