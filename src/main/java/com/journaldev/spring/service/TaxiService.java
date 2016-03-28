package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

public interface TaxiService {

	public Taxi createTaxi();

	public Taxi login(Long taxiId, String password)
			throws InstanceNotFoundException;

	public void updateActualStateTaxi(Long taxiId, State actualState)
			throws InstanceNotFoundException;

	public void updateFutureStateTaxi(Long taxiId, State futureState)
			throws InstanceNotFoundException;

	public List<Taxi> getTaxis();

	public Taxi getTaxi(Long taxiId) throws InstanceNotFoundException;

	public void updatePositionTaxi(Long taxiId, Point position)
			throws InstanceNotFoundException;

	public List<Country> getCountries();

	public List<Region> getRegions(Long countryId)
			throws InstanceNotFoundException;

	public List<City> getCities(Long regionId) throws InstanceNotFoundException;

	public List<Address> getAddresses(Long cityId)
			throws InstanceNotFoundException;

	public List<Stand> getStandsByZone(Long zoneId)
			throws InstanceNotFoundException;

	public List<Stand> getNearestStandsByTaxi(Long taxiId)
			throws InstanceNotFoundException;

	public void takeClientTo(Long taxiId, Long clientId, Long countryId,
			Long regionId, Long cityId, Long addressId)
			throws InstanceNotFoundException;

	public void destinationReached(Long futureTravelId, double distance,
			Point originPoint, Point destinationPoint, MultiLineString path)
			throws InstanceNotFoundException;

}
