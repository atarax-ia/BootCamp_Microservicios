package org.springframework.samples.petclinic.bill.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.bill.DTO.Bill;
import org.springframework.samples.petclinic.bill.Service.BillService;
import org.springframework.samples.petclinic.visit.DTO.Visit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {

	@Autowired
	private BillService billServ;

	@RequestMapping(value = "/bills", method = RequestMethod.GET)
	@ResponseBody
	public List<Bill> findBills(@RequestParam(name = "filter", defaultValue = "pagadas", required = true) String f) {

		return billServ.findBills(f);
	}

	@GetMapping("/bills/{idBill}/visit/{idVisit}")
	@ResponseBody
	public Visit showVisitDetails(@PathVariable("idBill") Integer billId, @PathVariable("idVisit") Integer visitId) {
		return billServ.showVisitDetails(billId, visitId);
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/bills/{idBill}/visit/{idVisit}")
	@ResponseBody
	public Visit updateVisitDetails(@PathVariable("idBill") Integer billId, @PathVariable("idVisit") Integer visitId) {
		return billServ.updateVisitDetails(billId, visitId);
	}

}
