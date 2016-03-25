package com.journaldev.spring.service;

import java.util.Calendar;
import java.util.List;

import org.postgresql.geometric.PGline;
import org.postgresql.geometric.PGpoint;
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
import com.journaldev.spring.model.FutureTravel;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.Travel;

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
	public void createTaxi(Taxi taxi) {
		this.taxiDao.save(taxi);
	}

	@Override
	@Transactional(readOnly = true)
	public Taxi login(Long taxiId, String password, boolean passwordIsEncrypted)
			throws InstanceNotFoundException, IncorrectPasswordException {
		System.out.println("Dentro del servicio");
		Taxi taxi = taxiDao.find(taxiId);
		String storedPassword = taxi.getPassword();
		if (password.compareTo(taxi.getPassword()) == 0) {
			System.out.println("Devuelve taxi correcto");
			return taxi;
		}
		if (passwordIsEncrypted) {
			if (!password.equals(storedPassword)) {
				System.out.println("Incorrect encrypted password exception");
				throw new IncorrectPasswordException(taxiId);
			}
		} else {
			if (!PasswordEncrypter.isClearPasswordCorrect(password,
					storedPassword)) {
				System.out.println("Incorrect password exception");
				throw new IncorrectPasswordException(taxiId);
			}
		}
		System.out.println("Devuelve taxi correcto");
		return taxi;
	}

	@Override
	public void updateActualStateTaxi(Long taxiId, State actualState)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		taxi.setActualState(actualState);
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
	public void updatePositionTaxi(Long taxiId, PGpoint position)
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
	public Taxi getTaxiById(Long taxiId) throws InstanceNotFoundException {
		return this.taxiDao.find(taxiId);
	}

	@Override
	public void removeTaxi(Long taxiId) throws InstanceNotFoundException {
		this.taxiDao.remove(taxiId);
	}

	@Override
	public void changePassword(Long taxiId, String oldClearPassword,
			String newClearPassword) throws IncorrectPasswordException,
			InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		String storedPassword = taxi.getPassword();
		if (!PasswordEncrypter.isClearPasswordCorrect(oldClearPassword,
				storedPassword)) {
			throw new IncorrectPasswordException(taxi.getTaxiId());
		}
		taxi.setPassword(PasswordEncrypter.crypt(newClearPassword));
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
	public void takeClientTo(Long taxiId, Long clientId, Long countryId,
			Long regionId, Long cityId, Long addressId)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(taxiId);
		Client client = clientDao.find(clientId);
		Calendar now = Calendar.getInstance();
		Country country = countryDao.find(countryId);
		Region region = regionDao.find(regionId);
		City city = cityDao.find(cityId);
		Address address = addressDao.find(addressId);
		FutureTravel travel = new FutureTravel(now, client.getOriginCountry(),
				client.getOriginRegion(), client.getOriginCity(),
				client.getOriginAddress(), country, region, city, address, taxi);
		this.futureTravelDao.save(travel);
		
		//TODO no setea el cliente
		taxi.setActualState(State.BUSY);
		taxi.setClient(client);
		this.taxiDao.save(taxi);
	}

	@Override
	public void destinationReached(Long futureTravelId, double distance)
			//PGpoint originPoint, PGpoint destinationPoint, PGline path)
			throws InstanceNotFoundException {
		
		FutureTravel futureTravel = futureTravelDao.find(futureTravelId);
		Taxi taxi = taxiDao.find(futureTravel.getTaxi().getTaxiId());
		taxi.setActualState(State.AVAILABLE);
		Client client = taxi.getClient();
		Travel travel = new Travel(futureTravel.getDate(),
				futureTravel.getOriginCountry(), futureTravel.getOriginRegion(),
				futureTravel.getOriginCity(), futureTravel.getOriginAddress(),
				futureTravel.getDestinationCountry(), futureTravel.getDestinationRegion(),
				futureTravel.getDestinationCity(), futureTravel.getDestinationAddress(),
				distance, taxi);
		travelDao.save(travel);
		clientDao.remove(client.getClientId());
		taxi.setClient(null);
		this.taxiDao.save(taxi);
		
	}

}
