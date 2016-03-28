package com.journaldev.spring.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.AddressDao;
import com.journaldev.spring.dao.CityDao;
import com.journaldev.spring.dao.ClientDao;
import com.journaldev.spring.dao.CountryDao;
import com.journaldev.spring.dao.RegionDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.vividsolutions.jts.geom.Point;

@Service("CentralService")
@Transactional
public class CentralServiceImpl implements CentralService {

	@Autowired
	private ClientDao clientDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private RegionDao regionDao;

	@Autowired
	private CityDao cityDao;

	@Autowired
	private AddressDao addressDao;

	@Override
	public Client createClient(Long country, Long region, Long city,
			Long address, Point location) throws InstanceNotFoundException {
		Country originCountry = countryDao.find(country);
		Region originRegion = regionDao.find(region);
		City originCity = cityDao.find(city);
		Address originAddress = addressDao.find(address);
		Client client = new Client(originCountry, originRegion, originCity,
				originAddress, Calendar.getInstance(), location);
		this.clientDao.save(client);
		return client;
	}

	@Override
	public void removeClient(Long id) throws InstanceNotFoundException {
		this.clientDao.remove(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client getClient(Long id) throws InstanceNotFoundException {
		return this.clientDao.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> getClients() {
		return this.clientDao.getClients();
	}

}
