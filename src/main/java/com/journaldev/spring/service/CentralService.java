package com.journaldev.spring.service;

import java.util.Calendar;
import java.util.List;

import org.postgresql.geometric.PGpoint;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;

public interface CentralService {

	public void createClient(Client client);

	public void updateClient(Long id, Country originCountry, Region originRegion,
			City originCity, Address originAddress, Calendar entry, PGpoint location)
			throws InstanceNotFoundException;

	public void removeClient(Long id) throws InstanceNotFoundException;
	
	public Client getClientById(Long id) throws InstanceNotFoundException;
	
	public List<Client> getClientsWaiting();

}
