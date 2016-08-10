package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.controller.Controller;
import com.tfg.model.Taxi;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "root-test-context.xml" })
@Transactional
public class RestTest {

	@Autowired
	private Controller controller;
	
	@Before
	public void data() {
		Taxi taxi1 = controller.createTaxi();
		Taxi taxi2 = controller.createTaxi();
		Taxi taxi3 = controller.createTaxi();
	}
	
	@Test
	public void testCreateTaxi() {
		assertTrue(controller.getTaxis().size()==3);
	}

}
