package com.journaldev.spring;

import java.util.List;

import org.postgresql.geometric.PGline;
import org.postgresql.geometric.PGpoint;
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
import com.journaldev.spring.model.Country;
import com.journaldev.spring.model.Region;
import com.journaldev.spring.model.Stand;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.service.IncorrectPasswordException;
import com.journaldev.spring.service.TaxiService;

@RestController
public class TaxiController {

	private TaxiService taxiService;
	Taxi taxi = new Taxi();

	Logger LOG = LoggerFactory.getLogger(TaxiController.class);

	@Autowired(required = true)
	@Qualifier(value = "taxiService")
	public void setTaxiService(TaxiService taxiService) {
		this.taxiService = taxiService;
	}

	@RequestMapping(value = "/taxi", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public @ResponseBody List<Taxi> getTaxis() {
		return this.taxiService.getTaxis();
	}

	//
	// @RequestMapping(value= "/taxi/add", method = RequestMethod.POST)
	// public String createTaxi(@ModelAttribute("taxi") Taxi taxi) throws
	// InstanceNotFoundException, IncorrectPasswordException{
	// if(taxi.getTaxiId() == 0){
	// if (taxi.getActualState()==null) {
	// taxi.setActualState(State.OFF);
	// }
	// if (taxi.getFutureState()==null) {
	// taxi.setFutureState(State.OFF);
	// }
	// this.taxiService.createTaxi(taxi);
	// }else{
	// this.taxiService.updateActualStateTaxi(taxi.getTaxiId(),
	// taxi.getActualState());
	// this.taxiService.updateFutureStateTaxi(taxi.getTaxiId(),
	// taxi.getFutureState());
	// this.taxiService.changePassword(taxi.getTaxiId(),
	// this.taxiService.getTaxiById(taxi.getTaxiId()).getPassword(),
	// taxi.getPassword());
	// }
	// return "redirect:/taxis";
	// }
	//
	// @RequestMapping("/remove/{id}")
	// public String removeTaxi(@PathVariable("id") Long id) throws
	// InstanceNotFoundException{
	// this.taxiService.removeTaxi(id);
	// return "redirect:/taxis";
	// }
	//
	// @RequestMapping("/edit/{id}")
	// public String editTaxi(@PathVariable("id") Long id, Model model) throws
	// InstanceNotFoundException{
	// model.addAttribute("taxi", this.taxiService.getTaxiById(id));
	// model.addAttribute("getTaxis", this.taxiService.getTaxis());
	// return "taxi";
	// }

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Taxi login(@RequestBody Taxi input)
			throws InstanceNotFoundException, IncorrectPasswordException {
		boolean passwordIsEncrypted = false;
		taxi = this.taxiService.login(input.getTaxiId(), input.getPassword(),
				passwordIsEncrypted);
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
	public void takeClientTo(@PathVariable("taxiId") Long taxiId,
			@PathVariable("clientId") Long clientId,
			@PathVariable("countryId") Long countryId,
			@PathVariable("regionId") Long regionId,
			@PathVariable("cityId") Long cityId,
			@PathVariable("addressId") Long addressId)
			throws InstanceNotFoundException {
		this.taxiService.takeClientTo(taxiId, clientId, countryId, regionId,
				cityId, addressId);
	}

	@RequestMapping(value = "/arrival/{futureTravelId}", method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public void destinationReached(
			@PathVariable("futureTravelId") Long futureTravelId,
			@PathVariable("distance") double distance,
			@PathVariable("originPoint") PGpoint originPoint,
			@PathVariable("destinationPoint") PGpoint destinationPoint,
			@PathVariable("path") PGline path) throws InstanceNotFoundException {
		this.taxiService.destinationReached(futureTravelId, distance,
				originPoint, destinationPoint, path);
	}

}
