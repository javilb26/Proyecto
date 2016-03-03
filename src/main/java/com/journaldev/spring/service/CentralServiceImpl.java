package com.journaldev.spring.service;

import java.util.Calendar;
import java.util.List;

import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.ClientDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;

@Service("clientService")
@Transactional
public class CentralServiceImpl implements CentralService {

	@Autowired
	private ClientDao clientDao;

	@Override
	public void createClient(Client client) {
		this.clientDao.save(client);
	}

	@Override
	public void updateClient(Long id, Country country, Region region,
			City city, Address address, Calendar entry, PGpoint location)
			throws InstanceNotFoundException {
		Client client = clientDao.find(id);
		client.setCountry(country);
		client.setRegion(region);
		client.setCity(city);
		client.setAddress(address);
		client.setEntry(entry);
		client.setLocation(location);
		this.clientDao.save(client);
	}

	@Override
	public void removeClient(Long id) throws InstanceNotFoundException {
		this.clientDao.remove(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client getClientById(Long id) throws InstanceNotFoundException {
		return this.clientDao.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> getClientsWaiting() {
		return this.clientDao.getClients();
	}

}
