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
import com.tfg.dao.util.InstanceNotFoundException;
import com.tfg.model.Address;
import com.tfg.model.City;
import com.tfg.model.Client;
import com.tfg.model.ClientState;
import com.tfg.model.Country;
import com.tfg.model.Region;
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
	Point pavoRealRdaOuteiro;
	double[] pavoRealRdaOuteiroCoords;
	GeometryFactory geometryFactory;
	List<Client> clients;
	List<Country> countries;
	List<Region> regions;
	List<City> cities;
	List<Address> addresses;

	@Autowired
	private Controller controller;

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
				pavoRealRdaOuteiroCoords[0], pavoRealRdaOuteiroCoords[1]));
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
		assertTrue(controller.getOperatingTaxis().size() == 2);
	}

	@Test
	public void testGetAvailableTaxis() {
		assertTrue(controller.getAvailableTaxis().size() == 1);
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
		assertTrue(regions.size() != 0);
	}

	@Test
	public void testGetCities() {
		assertTrue(cities.size() != 0);
	}

	@Test
	public void testGetAddresses() {
		assertTrue(addresses.size() != 0);
	}

	@Test
	public void testUpdateActualStateTaxi() {
	}

	@Test
	public void testUpdateFutureStateTaxi() {
	}

	@Test
	public void testUpdatePositionTaxi() {
	}

	@Test
	public void testGetStands() {
	}

	@Test
	public void testGetNearestStandsByTaxi() {
	}

	@Test
	public void testGetStandsByZone() {
	}

	@Test
	public void testGetTaxisByStand() {
	}

	@Test
	public void testGetNumTaxisStand() {
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
