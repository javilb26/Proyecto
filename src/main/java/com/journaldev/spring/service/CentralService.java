package com.journaldev.spring.service;

import java.util.List;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.model.TaxiClientDto;
import com.vividsolutions.jts.geom.Point;

public interface CentralService {

	public Client createClient(Long country, Long region, Long city,
			Long address, Point location) throws InstanceNotFoundException;

	public void removeClient(Long clientId) throws InstanceNotFoundException;

	public Client getClient(Long clientId) throws InstanceNotFoundException;

	public List<Client> getClients();

	public List<Client> getClientsWaiting();

	public void updatePositionTaxi(Long taxiId, Point position)
			throws InstanceNotFoundException;

	public List<Stand> getStands();

	public List<Taxi> getTaxisByStand(Long standId)
			throws InstanceNotFoundException;

	public List<Taxi> getOperatingTaxis();

	public List<Taxi> getAvailableTaxis();

	public long getNearestAddress(Point position);

	public long getCityFromAddress(long addressId)
			throws InstanceNotFoundException;

	public long getRegionFromCity(long cityId) throws InstanceNotFoundException;

	public long getCountryFromRegion(long regionId)
			throws InstanceNotFoundException;

	//public void assignClientsToTaxis();

	public TaxiClientDto getTaxiIdWithTokenAndClient() throws Exception;

	public void setTokenToTaxi(Long taxiId, String token)
			throws InstanceNotFoundException;

	public void assignClientToTaxi(Long taxiId, Long clientId)
			throws InstanceNotFoundException;

	public void setTaxiToOff(Long taxiId)
			throws InstanceNotFoundException;

}
