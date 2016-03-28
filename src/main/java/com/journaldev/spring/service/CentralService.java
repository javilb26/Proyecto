package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Client;
import com.vividsolutions.jts.geom.Point;

public interface CentralService {

	public Client createClient(Long country, Long region, Long city, Long address,
			Point location) throws InstanceNotFoundException;

	public void removeClient(Long clientId) throws InstanceNotFoundException;
	
	public Client getClient(Long clientId) throws InstanceNotFoundException;
	
	public List<Client> getClients();

}
