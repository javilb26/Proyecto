package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Client;

@Repository("clientDao")
public class ClientDaoHibernate extends
		GenericDaoHibernate<Client, Long> implements ClientDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getClients() {
		return getSession().createQuery("FROM Client").list();
	}

}