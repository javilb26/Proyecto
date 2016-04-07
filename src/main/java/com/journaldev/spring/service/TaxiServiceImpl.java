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
import com.journaldev.spring.dao.FutureTravelDao;
import com.journaldev.spring.dao.RegionDao;
import com.journaldev.spring.dao.StandDao;
//import com.journaldev.spring.dao.StateDao;
import com.journaldev.spring.dao.TaxiDao;
import com.journaldev.spring.dao.TravelDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.Travel;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

@Service("taxiService")
@Transactional
public class TaxiServiceImpl implements TaxiService {

	@Autowired
	private TaxiDao taxiDao;

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
	private FutureTravelDao futureTravelDao;

	@Autowired
	private TravelDao travelDao;

	@Autowired
	private StandDao standDao;

	@Override
	public Taxi createTaxi() {
		Taxi taxi = new Taxi();
		this.taxiDao.save(taxi);
		return taxi;
	}

	@Override
	@Transactional(readOnly = true)
	public Taxi login(Long taxiId, String password)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		if (password.compareTo(taxi.getPassword()) != 0) {
			System.out.println("IncorrectPasswordException");
			return null;
		}
		return taxi;
	}

	@Override
	public void updateActualStateTaxi(Long taxiId, State actualState)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setActualState(actualState);
		// TODO no habria que hacerlo al arreglar el flush
		this.taxiDao.save(taxi);
	}

	@Override
	public void updateFutureStateTaxi(Long taxiId, State futureState)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setFutureState(futureState);
		this.taxiDao.save(taxi);
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
	public List<Taxi> getTaxis() {
		return this.taxiDao.getTaxis();
	}

	@Override
	@Transactional(readOnly = true)
	public Taxi getTaxi(Long taxiId) throws InstanceNotFoundException {
		return this.taxiDao.find(taxiId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Country> getCountries() {
		return this.countryDao.getCountries();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> getRegions(Long countryId)
			throws InstanceNotFoundException {
		return this.regionDao.getRegions(countryId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<City> getCities(Long regionId) throws InstanceNotFoundException {
		return this.cityDao.getCities(regionId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Address> getAddresses(Long cityId)
			throws InstanceNotFoundException {
		return this.addressDao.getAddresses(cityId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Stand> getStandsByZone(Long zoneId)
			throws InstanceNotFoundException {
		return this.standDao.getStandsByZone(zoneId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Stand> getNearestStandsByTaxi(Long taxiId)
			throws InstanceNotFoundException {
		return this.standDao.getNearestStandsByTaxi(taxiId);
	}

	@Override
	public Long takeClientTo(Long taxiId, Long clientId, Long countryId,
			Long regionId, Long cityId, Long addressId)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		Client client = clientDao.find(clientId);
		Calendar now = Calendar.getInstance();
		Country country = countryDao.find(countryId);
		Region region = regionDao.find(regionId);
		City city = cityDao.find(cityId);
		Address address = addressDao.find(addressId);
		Travel travel = new Travel(now, client.getOriginCountry(),
				client.getOriginRegion(), client.getOriginCity(),
				client.getOriginAddress(), country, region, city, address, taxi);
		this.travelDao.save(travel);
		taxi.setActualState(State.BUSY);
		taxi.setClient(client);
		this.taxiDao.save(taxi);
		return travel.getTravelId();
	}

	@Override
	public void destinationReached(Long travelId, double distance,
			Point originPoint, Point destinationPoint, MultiLineString path)
			throws InstanceNotFoundException {
		Travel travel = travelDao.find(travelId);
		Taxi taxi = travel.getTaxi();
		taxi.setActualState(taxi.getFutureState());
		travel.setDistance(distance);
		travel.setOriginPoint(originPoint);
		travel.setDestinationPoint(destinationPoint);
		travel.setPath(path);
		this.travelDao.save(travel);
		clientDao.remove(taxi.getClient().getClientId());
		taxi.setClient(null);
		this.taxiDao.save(taxi);
	}

	@Override
	public void receivePositionTaxi(Long taxiId, Point position)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setPosition(position);
		this.taxiDao.save(taxi);
	}

}
