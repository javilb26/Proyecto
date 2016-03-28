package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Zone;

@Repository("zoneDao")
public class ZoneDaoHibernate extends
		GenericDaoHibernate<Zone, Long> implements ZoneDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Zone> getZones() {
		return getSession().createQuery("FROM Zone").list();
	}

}