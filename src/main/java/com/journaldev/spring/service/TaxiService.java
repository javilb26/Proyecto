package com.journaldev.spring.service;

import java.util.List;

import org.postgresql.geometric.PGpoint;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;

public interface TaxiService {

	public void createTaxi(Taxi taxi);

	public Taxi login(Long id, String password, boolean passwordIsEncrypted)
			throws InstanceNotFoundException, IncorrectPasswordException;

	public void updateActualStateTaxi(Long id, State actualState)
			throws InstanceNotFoundException;

	public void updateFutureStateTaxi(Long id, State futureState)
			throws InstanceNotFoundException;
	
	public List<Taxi> getTaxis();

	public Taxi getTaxiById(Long id) throws InstanceNotFoundException;

	public void removeTaxi(Long id) throws InstanceNotFoundException;

	public void changePassword(Long id, String oldClearPassword,
			String newClearPassword) throws IncorrectPasswordException,
			InstanceNotFoundException;

	public void updatePositionTaxi(Long id, PGpoint position)
			throws InstanceNotFoundException;
	
	public List<Country> getCountries();
	
	public List<Region> getRegions(Long countryId) throws InstanceNotFoundException;
	
	public List<City> getCities(Long regionId) throws InstanceNotFoundException;
	
	public List<Address> getAddresses(Long cityId) throws InstanceNotFoundException;
	
	public void takeClientTo(Long id, Long clientId) throws InstanceNotFoundException;
	
	public void destinationReached(Long travelId) throws InstanceNotFoundException;
	
	//public List<Stand> getNearbyStands(Long id) throws InstanceNotFoundException;
	
}
