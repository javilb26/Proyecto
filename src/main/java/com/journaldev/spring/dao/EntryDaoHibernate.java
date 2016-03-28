package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Entry;

@Repository("EntryDao")
public class EntryDaoHibernate extends GenericDaoHibernate<Entry, Long>
		implements EntryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Entry> getEntries(Long standId) {
		return getSession()
				.createQuery(
						"SELECT e FROM Entry e WHERE e.stand = :standId ORDER BY e.arrival")
				.setParameter("standId", standId).list();
	}

}