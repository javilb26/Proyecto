package com.journaldev.spring.service;

import java.util.Calendar;
import java.util.List;

import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.journaldev.spring.dao.AddressDao;
import com.journaldev.spring.dao.CityDao;
import com.journaldev.spring.dao.ClientDao;
import com.journaldev.spring.dao.CountryDao;
import com.journaldev.spring.dao.RegionDao;
//import com.journaldev.spring.dao.StateDao;
import com.journaldev.spring.dao.TaxiDao;
import com.journaldev.spring.dao.FutureTravelDao;
import com.journaldev.spring.dao.TravelDao;
import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.FutureTravel;

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
	private FutureTravelDao travelDao;

	@Autowired
	private TravelDao travelMadeDao;

	@Override
	public void createTaxi(Taxi taxi) {
		this.taxiDao.save(taxi);
	}

	@Override
	@Transactional(readOnly = true)
	public Taxi login(Long id, String password, boolean passwordIsEncrypted)
			throws InstanceNotFoundException, IncorrectPasswordException {
		System.out.println("Dentro del servicio");
		Taxi taxi = taxiDao.find(id);
		String storedPassword = taxi.getPassword();
		if(password.compareTo(taxi.getPassword())==0) {
			System.out.println("Devuelve taxi correcto");
			return taxi;
		}
		if (passwordIsEncrypted) {
			if (!password.equals(storedPassword)) {
				System.out.println("Incorrect encrypted password exception");
				throw new IncorrectPasswordException(id);
			}
		} else {
			if (!PasswordEncrypter.isClearPasswordCorrect(password,
					storedPassword)) {
				System.out.println("Incorrect password exception");
				throw new IncorrectPasswordException(id);
			}
		}
		System.out.println("Devuelve taxi correcto");
		return taxi;
	}

	@Override
	public void updateActualStateTaxi(Long id, State actualState)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(id);
		taxi.setActualState(actualState);
		this.taxiDao.save(taxi);
	}
	
	@Override
	public void updateFutureStateTaxi(Long id, State futureState)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(id);
		taxi.setFutureState(futureState);
		this.taxiDao.save(taxi);
	}

	@Override
	public void updatePositionTaxi(Long id, PGpoint position)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(id);
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
	public Taxi getTaxiById(Long id) throws InstanceNotFoundException {
		return this.taxiDao.find(id);
	}

	@Override
	public void removeTaxi(Long id) throws InstanceNotFoundException {
		this.taxiDao.remove(id);
	}

	@Override
	public void changePassword(Long id, String oldClearPassword,
			String newClearPassword) throws IncorrectPasswordException,
			InstanceNotFoundException {
		Taxi taxi = taxiDao.find(id);
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
	public void takeClientTo(Long id, Long clientId)
			throws InstanceNotFoundException {
		Taxi taxi = taxiDao.find(id);
		Client client = clientDao.find(clientId);
		FutureTravel travel = new FutureTravel(Calendar.getInstance(), client.getCountry(),
				client.getRegion(), client.getCity(), client.getAddress(),
				client.getDestinationCountry(), client.getDestinationRegion(),
				client.getDestinationCity(), client.getDestinationAddress(),
				taxi);
		this.travelDao.save(travel);
	}

	@Override
	//Añadir parametros una vez llegado al sitio
	public void destinationReached(Long travelId)
			throws InstanceNotFoundException {
		FutureTravel travel = travelDao.find(travelId);
		Taxi taxi = taxiDao.find(travel.getTaxi().getTaxiId());
		taxi.setActualState(State.AVAILABLE);
		this.taxiDao.save(taxi);
		clientDao.remove(taxi.getClient().getClientId());
		// Guardar el travelMade
	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<Stand> getNearbyStands(Long id)
//			throws InstanceNotFoundException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	// @Override
	// public void changeActualState(Long id, Long stateId)
	// throws InstanceNotFoundException {
	// Taxi taxi = taxiDao.find(id);
	// State actualState = stateDao.find(stateId);
	// taxi.setActualState(actualState);
	// this.taxiDao.save(taxi);
	// }
	//
	// @Override
	// public void changeFutureState(Long id, Long stateId)
	// throws InstanceNotFoundException {
	// Taxi taxi = taxiDao.find(id);
	// State futureState = stateDao.find(stateId);
	// taxi.setFutureState(futureState);
	// this.taxiDao.save(taxi);
	// }

}
