package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Entry;

public interface EntryDao extends GenericDao<Entry, Long> {
	
	public List<Entry> getEntries(Long standId);
	
}
