package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.journaldev.spring.dao.util.InstanceNotFoundException;
import com.journaldev.spring.model.State;
import com.journaldev.spring.model.Taxi;
import com.journaldev.spring.service.IncorrectPasswordException;
import com.journaldev.spring.service.TaxiService;

@RestController
public class TaxiController {
	
	private TaxiService taxiService;
	Taxi taxi = new Taxi();
	
	@Autowired(required=true)
	@Qualifier(value="taxiService")
	public void setTaxiService(TaxiService taxiService){
		this.taxiService = taxiService;
	}
	
//	@RequestMapping(value = "/taxis", method = RequestMethod.GET)
//	public String getTaxis(Model model) {
//		model.addAttribute("taxi", new Taxi());
//		model.addAttribute("getTaxis", this.taxiService.getTaxis());
//		return "taxi";
//	}
//	
//	@RequestMapping(value= "/taxi/add", method = RequestMethod.POST)
//	public String createTaxi(@ModelAttribute("taxi") Taxi taxi) throws InstanceNotFoundException, IncorrectPasswordException{
//		if(taxi.getTaxiId() == 0){
//			if (taxi.getActualState()==null) {
//				taxi.setActualState(State.OFF);
//			}
//			if (taxi.getFutureState()==null) {
//				taxi.setFutureState(State.OFF);
//			}
//			this.taxiService.createTaxi(taxi);
//		}else{
//			this.taxiService.updateActualStateTaxi(taxi.getTaxiId(), taxi.getActualState());
//			this.taxiService.updateFutureStateTaxi(taxi.getTaxiId(), taxi.getFutureState());
//			this.taxiService.changePassword(taxi.getTaxiId(), this.taxiService.getTaxiById(taxi.getTaxiId()).getPassword(), taxi.getPassword());
//		}
//		return "redirect:/taxis";
//	}
//	
//	@RequestMapping("/remove/{id}")
//    public String removeTaxi(@PathVariable("id") Long id) throws InstanceNotFoundException{
//        this.taxiService.removeTaxi(id);
//        return "redirect:/taxis";
//    }
// 
//    @RequestMapping("/edit/{id}")
//    public String editTaxi(@PathVariable("id") Long id, Model model) throws InstanceNotFoundException{
//        model.addAttribute("taxi", this.taxiService.getTaxiById(id));
//        model.addAttribute("getTaxis", this.taxiService.getTaxis());
//        return "taxi";
//    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public Taxi login(@RequestBody Taxi input) throws InstanceNotFoundException, IncorrectPasswordException{
		boolean passwordIsEncrypted = false;
		taxi = this.taxiService.login(input.getTaxiId(), input.getPassword(), passwordIsEncrypted);
      	return taxi;
    }
    
    @RequestMapping(value = "/taxi/{taxiId}/state/{state}", method = RequestMethod.PUT, produces = "application/json")
    public void updateActualStateTaxi(@PathVariable("taxiId") Long taxiId, @PathVariable("state") String state) throws InstanceNotFoundException {
    	this.taxiService.updateActualStateTaxi(taxiId, State.valueOf(state.toUpperCase()));
    }
}
