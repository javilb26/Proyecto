package com.journaldev.spring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.Address;
import com.journaldev.spring.model.City;
import com.journaldev.spring.model.Client;
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.service.CentralService;
import com.journaldev.spring.service.TaxiService;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

@RestController
public class TaxiController {

	private TaxiService taxiService;
	private CentralService centralService;
	GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),
			4326);

	Logger LOG = LoggerFactory.getLogger(TaxiController.class);

	@Autowired(required = true)
	@Qualifier(value = "taxiService")
	public void setTaxiService(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	@Autowired(required = true)
	@Qualifier(value = "centralService")
	public void setCentralService(CentralService centralService) {
		this.centralService = centralService;
	}

	@RequestMapping(value = "/taxi", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Taxi> getTaxis() {
		return this.taxiService.getTaxis();
	}

	@RequestMapping(value = "/client", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Client> getClients() {
		return this.centralService.getClients();
	}

	@RequestMapping(value = "/addtaxi", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Taxi createTaxi() {
		return this.taxiService.createTaxi();
	}

	@RequestMapping(value = "/addclient", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public void createClient(@RequestBody Client input)
			throws InstanceNotFoundException {
		this.centralService.createClient(input.getOriginCountry()
				.getCountryId(), input.getOriginRegion().getRegionId(), input
				.getOriginCity().getCityId(), input.getOriginAddress()
				.getAddressId(), //TODO mirar como traer un punto en un post
		// geometryFactory.createPoint(new Coordinate(y, x))
				null);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Taxi login(@RequestBody Taxi input) throws InstanceNotFoundException {
		Taxi taxi = this.taxiService.login(input.getTaxiId(),
				input.getPassword());
		return taxi;
	}

	@RequestMapping(value = "/country", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Country> getCountries() {
		return this.taxiService.getCountries();
	}

	@RequestMapping(value = "/country/{countryId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Region> getRegions(
			@PathVariable("countryId") Long countryId)
			throws InstanceNotFoundException {
		return this.taxiService.getRegions(countryId);
	}

	@RequestMapping(value = "/region/{regionId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<City> getCities(
			@PathVariable("regionId") Long regionId)
			throws InstanceNotFoundException {
		return this.taxiService.getCities(regionId);
	}

	@RequestMapping(value = "/city/{cityId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Address> getAddresses(
			@PathVariable("cityId") Long cityId)
			throws InstanceNotFoundException {
		return this.taxiService.getAddresses(cityId);
	}

	@RequestMapping(value = "/taxi/{taxiId}/actualstate/{state}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public void updateActualStateTaxi(@PathVariable("taxiId") Long taxiId,
			@PathVariable("state") String state)
			throws InstanceNotFoundException {
		this.taxiService.updateActualStateTaxi(taxiId,
				State.valueOf(state.toUpperCase()));
	}

	@RequestMapping(value = "/taxi/{taxiId}/futurestate/{state}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public void updateFutureStateTaxi(@PathVariable("taxiId") Long taxiId,
			@PathVariable("state") String state)
			throws InstanceNotFoundException {
		this.taxiService.updateFutureStateTaxi(taxiId,
				State.valueOf(state.toUpperCase()));
	}

	@RequestMapping(value = "/taxi/{taxiId}/position/{x}/{y}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public void receivePositionTaxi(@PathVariable("taxiId") Long taxiId,
			@PathVariable("x") Double x, @PathVariable("y") Double y)
			throws InstanceNotFoundException {
		this.taxiService.receivePositionTaxi(taxiId,
				geometryFactory.createPoint(new Coordinate(y, x)));
	}

	@RequestMapping(value = "/taxi/{taxiId}/standstaxi", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Stand> getNearestStandsByTaxi(
			@PathVariable("taxiId") Long taxiId)
			throws InstanceNotFoundException {
		return this.taxiService.getNearestStandsByTaxi(taxiId);
	}

	@RequestMapping(value = "/taxi/{taxiId}/standszone", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Stand> getStandsByZone(
			@PathVariable("zoneId") Long zoneId)
			throws InstanceNotFoundException {
		return this.taxiService.getStandsByZone(zoneId);
	}

	@RequestMapping(value = "/taxi/{taxiId}/client/{clientId}/country/{countryId}/region/{regionId}/city/{cityId}/address/{addressId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody Long takeClientTo(@PathVariable("taxiId") Long taxiId,
			@PathVariable("clientId") Long clientId,
			@PathVariable("countryId") Long countryId,
			@PathVariable("regionId") Long regionId,
			@PathVariable("cityId") Long cityId,
			@PathVariable("addressId") Long addressId)
			throws InstanceNotFoundException {
		return this.taxiService.takeClientTo(taxiId, clientId, countryId,
				regionId, cityId, addressId);
	}
	//TODO Pasar esto a POST
	@RequestMapping(value = "/arrival/{travelId}/distance/{distance}/originpoint/{ox}/{oy}/destinationpoint/{dx}/{dy}/path/{path}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public void destinationReached(@PathVariable("travelId") Long travelId,
			@PathVariable("distance") double distance,
			@PathVariable("ox") double ox, @PathVariable("oy") double oy,
			@PathVariable("dx") double dx, @PathVariable("dy") double dy,// TODO
																			// Mirar
																			// como
																			// recibir
																			// el
																			// multilinestring
			@PathVariable("path")/* MultiLine */String path)
			throws InstanceNotFoundException {
		Point originPoint = geometryFactory.createPoint(new Coordinate(oy, ox));
		Point destinationPoint = geometryFactory.createPoint(new Coordinate(dy,
				dx));
		this.taxiService.destinationReached(travelId, distance, originPoint,
				destinationPoint, null);
	}

}
