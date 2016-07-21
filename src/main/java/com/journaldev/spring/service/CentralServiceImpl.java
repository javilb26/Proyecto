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
import com.journaldev.spring.dao.StandDao;
import com.journaldev.spring.dao.TaxiDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.ClientState;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.TaxiClientDto;
import com.journaldev.spring.model.TaxiState;
import com.vividsolutions.jts.geom.Point;

@Service("centralService")
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

	@Autowired
	private TaxiDao taxiDao;

	@Autowired
	private StandDao standDao;

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
	public Client getClient(Long clientId) throws InstanceNotFoundException {
		return this.clientDao.find(clientId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> getClients() {
		return this.clientDao.getClients();
	}

	@Override
	public void updatePositionTaxi(Long taxiId, Point position)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setPosition(position);
		this.taxiDao.save(taxi);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Stand> getStands() {
		return this.standDao.getStands();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Taxi> getTaxisByStand(Long standId)
			throws InstanceNotFoundException {
		return this.standDao.getTaxisByStand(standId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Taxi> getOperatingTaxis() {
		return this.taxiDao.getOperatingTaxis();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Taxi> getAvailableTaxis() {
		return this.taxiDao.getAvailableTaxis();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> getClientsWaiting() {
		return this.clientDao.getClientsWaiting();
	}

	@Override
	@Transactional(readOnly = true)
	public long getNearestAddress(Point position) {
		return this.addressDao.getNearestAddress(position);
	}

	@Override
	@Transactional(readOnly = true)
	public long getCityFromAddress(long addressId)
			throws InstanceNotFoundException {
		return this.cityDao.getCityFromAddress(addressId);
	}

	@Override
	@Transactional(readOnly = true)
	public long getRegionFromCity(long cityId) throws InstanceNotFoundException {
		return this.regionDao.getRegionFromCity(cityId);
	}

	@Override
	@Transactional(readOnly = true)
	public long getCountryFromRegion(long regionId)
			throws InstanceNotFoundException {
		return this.countryDao.getCountryFromRegion(regionId);
	}

	@Override
	public TaxiClientDto getTaxiIdWithTokenAndClient() throws Exception {
		Client client;
		client = this.clientDao.getFirstClient();
		Taxi taxi;
		taxi = this.taxiDao.getNearestAvailableTaxi(client.getLocation());
		System.out.println("1-" + client.getLocation().toText());
		System.out.println("2-" + taxi.getTaxiId());
		System.out.println("3-" + taxi.getToken());
		System.out.println("4-" + client.getClientId());
		System.out.println("5-" + client.getOriginCountry().getName());
		return new TaxiClientDto(taxi.getTaxiId(), taxi.getToken(),
				client.getClientId(), client.getOriginCountry().getName(),
				client.getOriginRegion().getName(), client.getOriginCity()
						.getName(), client.getOriginAddress().getName());

	}

	@Override
	public void setTokenToTaxi(Long taxiId, String token)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setToken(token);
		taxiDao.save(taxi);
	}

	@Override
	public void assignClientToTaxi(Long taxiId, Long clientId)
			throws InstanceNotFoundException {
		Client client = this.clientDao.find(clientId);
		Taxi taxi = this.taxiDao.find(taxiId);
		if ((client != null) && (taxi != null)) {
			taxi.setClient(client);
			client.setClientState(ClientState.ASSIGNED);
			taxi.setActualState(TaxiState.BUSY);
			this.clientDao.save(client);
			this.taxiDao.save(taxi);
		}
	}

	@Override
	public void setTaxiToOff(Long taxiId) throws InstanceNotFoundException {
		Taxi taxi = this.taxiDao.find(taxiId);
		taxi.setActualState(TaxiState.OFF);
		this.taxiDao.save(taxi);
	}
}
