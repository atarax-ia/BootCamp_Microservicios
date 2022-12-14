/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.visit.Controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.DTO.Owner;
import org.springframework.samples.petclinic.owner.Service.OwnerService;
import org.springframework.samples.petclinic.visit.DTO.Visit;
import org.springframework.samples.petclinic.visit.Service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@RestController
public class VisitController {

	@Autowired
	private VisitService visitServ;

	@Autowired
	private OwnerService owners;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
	 * we always have fresh data - Since we do not use the session scope, make sure that
	 * Pet object always has an id (Even though id is not part of the form fields)
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId,
								  Map<String, Object> model) {
		Visit v = visitServ.findPetWithVisit(ownerId, petId, model);
		return v;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm() {
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@ModelAttribute Owner owner, @PathVariable int petId, @Valid Visit visit,
									  BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}

		Integer ownerId = visitServ.addVisitToPet(owner, petId, visit);
		return "redirect:/owners/{ownerId}";
	}

	@RequestMapping(value = "/visits", method = RequestMethod.GET)
	public List<Visit> findVisits(@RequestParam(name = "filter", defaultValue = "pagadas", required = true) String f) {

		return visitServ.findVisits(f);
	}

}
