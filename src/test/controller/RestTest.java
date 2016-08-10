package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.controller.Controller;
import com.tfg.dao.AddressDao;
import com.tfg.dao.CityDao;
import com.tfg.dao.StandDao;
import com.tfg.dao.util.InstanceNotFoundException;
import com.tfg.model.Address;
import com.tfg.model.City;
import com.tfg.model.Client;
import com.tfg.model.ClientState;
import com.tfg.model.Country;
import com.tfg.model.Region;
import com.tfg.model.Stand;
import com.tfg.model.Taxi;
import com.tfg.model.TaxiState;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "root-test-context.xml" })
@Transactional
public class RestTest {

	Taxi taxi1, taxi2, taxi3, taxi4;
	Client client1, client2, client3;
	Point pavoRealRdaOuteiro, estTrenRtnd, osRosales, matogrande, calleBarcelona;
	double[] pavoRealRdaOuteiroCoords, estTrenRtndCoords, osRosalesCoords, matograndeCoords, calleBarcelonaCoords;
	GeometryFactory geometryFactory;
	List<Client> clients;
	List<Country> countries;
	List<Region> regions;
	List<City> cities;
	List<Address> addresses;
	Stand stand1, stand2, stand3;
	List<Stand> stands;

	@Autowired
	private Controller controller;

	@Autowired
	private StandDao standDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private CityDao cityDao;

	@Before
	public void data() {
		taxi1 = controller.createTaxi();
		taxi2 = controller.createTaxi();
		taxi3 = controller.createTaxi();
		taxi4 = controller.createTaxi();
		taxi1.setActualState(TaxiState.OFF);
		taxi2.setActualState(TaxiState.AVAILABLE);
		taxi3.setActualState(TaxiState.BUSY);
		taxi4.setActualState(TaxiState.INSTAND);
		geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
		pavoRealRdaOuteiroCoords = new double[2];
		pavoRealRdaOuteiroCoords[1] = -8.42469423106243;
		pavoRealRdaOuteiroCoords[0] = 43.3677126761829;
		pavoRealRdaOuteiro = geometryFactory.createPoint(new Coordinate(
				pavoRealRdaOuteiroCoords[1], pavoRealRdaOuteiroCoords[0]));
		try {
			controller.createClient(pavoRealRdaOuteiroCoords);
			controller.createClient(pavoRealRdaOuteiroCoords);
			controller.createClient(pavoRealRdaOuteiroCoords);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		clients = new ArrayList<Client>();
		clients.addAll(controller.getClients());
		clients.get(0).setClientState(ClientState.WAITING);
		clients.get(1).setClientState(ClientState.ASSIGNED);
		clients.get(2).setClientState(ClientState.TRAVELLING);
		taxi1.setPassword("Hola");
		countries = controller.getCountries();
		try {
			regions = controller.getRegions(1l);
			cities = controller.getCities(9l);
			addresses = controller.getAddresses(6944l);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		taxi1.setPosition(pavoRealRdaOuteiro);
		estTrenRtndCoords = new double[2];
		estTrenRtndCoords[1] = -8.4086963;
		estTrenRtndCoords[0] = 43.3531591;
		estTrenRtnd = geometryFactory.createPoint(new Coordinate(
				estTrenRtndCoords[1], estTrenRtndCoords[0]));
		osRosalesCoords = new double[2];
		osRosalesCoords[1] = -8.4134398;
		osRosalesCoords[0] = 43.3448868;
		osRosales = geometryFactory.createPoint(new Coordinate(
				osRosalesCoords[1], osRosalesCoords[0]));
		matograndeCoords = new double[2];
		matograndeCoords[1] = -8.4044273;
		matograndeCoords[0] = 43.3410724;
		matogrande = geometryFactory.createPoint(new Coordinate(
				matograndeCoords[1], matograndeCoords[0]));
		calleBarcelonaCoords = new double[2];
		calleBarcelonaCoords[1] = -8.4228256;
		calleBarcelonaCoords[0] = 43.3631952;
		calleBarcelona = geometryFactory.createPoint(new Coordinate(
				calleBarcelonaCoords[1], calleBarcelonaCoords[0]));
		try {
			stand1 = new Stand();
			stand1.setName("Os Rosales");
			stand1.setLocation(osRosales);
			stand1.setAddress(addressDao.find(2419l));
			standDao.save(stand1);
			stand2 = new Stand();
			stand2.setName("Matogrande");
			stand2.setLocation(matogrande);
			stand2.setAddress(addressDao.find(2397l));
			standDao.save(stand2);
			stand3 = new Stand();
			stand3.setName("Calle Barcelona");
			stand3.setLocation(calleBarcelona);
			stand3.setAddress(addressDao.find(2009l));
			standDao.save(stand3);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		stands = controller.getStands();
		try {
			taxi1.setCity(cityDao.find(6944l));
			taxi2.setCity(cityDao.find(6944l));
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
		taxi2.setPosition(osRosales);
		try {
			controller.locateTaxi(taxi2.getTaxiId());
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetTaxis() {
		assertTrue(controller.getTaxis().size() == 4);
	}

	@Test
	public void testGetTaxi() throws InstanceNotFoundException {
		Taxi taxi = controller.getTaxi(taxi1.getTaxiId());
		assertTrue(controller.getTaxis().get(0) == taxi);
	}

	@Test
	public void testGetOperatingTaxis() {
		assertTrue(controller.getOperatingTaxis().size() == 1);
	}

	@Test
	public void testGetAvailableTaxis() {
		assertTrue(controller.getAvailableTaxis().size() == 0);
	}

	@Test
	public void testGetClients() {
		assertTrue(controller.getClients().size() == 2);
	}

	@Test
	public void testGetClientsWaiting() {
		assertTrue(controller.getClientsWaiting().size() == 1);
	}

	@Test
	public void testCreateTaxi() {
		controller.createTaxi();
		assertTrue(controller.getTaxis().size() == 5);
	}

	@Test
	public void testCreateClient() throws InstanceNotFoundException {
		controller.createClient(pavoRealRdaOuteiroCoords);
		assertTrue(controller.getClients().size() == 3);
	}

	@Test
	public void testLogin() throws InstanceNotFoundException, Exception {
		Taxi taxi = controller.login(taxi1);
		assertTrue(taxi != null);
	}

	@Test
	public void testGetCountries() {
		assertTrue(countries.size() == 1);
	}

	@Test
	public void testGetRegions() {
		assertTrue(regions.size() == 50);
	}

	@Test
	public void testGetCities() {
		assertTrue(cities.size() == 93);
	}

	@Test
	public void testGetAddresses() {
		assertTrue(addresses.size() == 933);
	}

	@Test
	public void testUpdateActualStateTaxi() throws InstanceNotFoundException {
		TaxiState state = taxi1.getActualState();
		assertTrue(state == TaxiState.OFF);
		controller.updateActualStateTaxi(taxi1.getTaxiId(),
				TaxiState.AVAILABLE.toString());
		assertTrue(taxi1.getActualState() == TaxiState.AVAILABLE);
	}

	@Test
	public void testUpdateFutureStateTaxi() throws InstanceNotFoundException {
		TaxiState state = taxi1.getFutureState();
		assertTrue(state == TaxiState.OFF);
		controller.updateFutureStateTaxi(taxi1.getTaxiId(),
				TaxiState.AVAILABLE.toString());
		assertTrue(taxi1.getFutureState() == TaxiState.AVAILABLE);
	}

	@Test
	public void testUpdatePositionTaxi() throws InstanceNotFoundException {
		assertTrue(taxi1.getPosition() == pavoRealRdaOuteiro);
		controller.updatePositionTaxi(taxi1.getTaxiId(), estTrenRtndCoords[0],
				estTrenRtndCoords[1]);
		assertTrue(taxi1.getPosition().compareTo(estTrenRtnd)==0);
	}

	@Test
	public void testGetStands() {
		assertTrue(stands.size() == 3);
	}

	@Test
	public void testGetNearestStandsByTaxi() throws InstanceNotFoundException {
		List<Stand> nearestStands = controller.getNearestStandsByTaxi(taxi1.getTaxiId());
		assertTrue(nearestStands.get(0)==stand3);
		assertTrue(nearestStands.get(1)==stand1);
		assertTrue(nearestStands.get(2)==stand2);
	}

	@Test
	public void testGetTaxisByStand() throws InstanceNotFoundException {
		List<Taxi> taxisByStand1 = controller.getTaxisByStand(stand1.getStandId());
		List<Taxi> taxisByStand2 = controller.getTaxisByStand(stand2.getStandId());
		List<Taxi> taxisByStand3 = controller.getTaxisByStand(stand3.getStandId());
		assertTrue(taxisByStand1.get(0)==taxi2);
		assertTrue(taxisByStand2.size()==0);
		assertTrue(taxisByStand3.size()==0);
	}

	@Test
	public void testGetNumTaxisStand() throws InstanceNotFoundException {
		List<Taxi> taxisByStand1 = controller.getTaxisByStand(stand1.getStandId());
		List<Taxi> taxisByStand2 = controller.getTaxisByStand(stand2.getStandId());
		List<Taxi> taxisByStand3 = controller.getTaxisByStand(stand3.getStandId());
		assertTrue(taxisByStand1.size()==1);
		assertTrue(taxisByStand2.size()==0);
		assertTrue(taxisByStand3.size()==0);
	}

	@Test
	public void testTakeClientTo() {
	}

	@Test
	public void testDestinationReached() {
	}

	@Test
	public void testGetTravels() {
	}

	@Test
	public void testGetFutureTravels() {
	}

	@Test
	public void testCreateFutureTravel() {
	}

	@Test
	public void testCancelFutureTravel() {
	}

	@Test
	public void testCancelTravel() {
	}

	@Test
	public void testTakeClientToFromFutureTravel() {
	}

	@Test
	public void testGetTaxiIdWithTokenAndClient() {
	}

	@Test
	public void testSetTokenToTaxi() {
	}

	@Test
	public void testAssignClientToTaxi() {
	}

	@Test
	public void testDeclineClientToTaxi() {
	}

	@Test
	public void testLocateTaxi() {
	}

	@Test
	public void testChangePassword() {
	}

	@Test
	public void testChangeCity() {
	}

	// TODO Y estas son solo las positivas
}
