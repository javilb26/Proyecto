package com.journaldev.spring.dao;

import java.util.List;

import com.journaldev.spring.dao.util.GenericDao;
import com.journaldev.spring.model.Client;

public interface ClientDao extends GenericDao<Client, Long> {
	
	public List<Client> getClients();
	
	public List<Client> getClientsWaiting();

	public Client getFirstClient();
	
}
