package org.springframework.samples.petclinic.visit.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.bill.DAO.BillRepository;
import org.springframework.samples.petclinic.owner.DAO.OwnerRepository;
import org.springframework.samples.petclinic.owner.DTO.Owner;
import org.springframework.samples.petclinic.pet.DTO.Pet;
import org.springframework.samples.petclinic.visit.DAO.VisitRepository;
import org.springframework.samples.petclinic.visit.DTO.Visit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VisitService {

	@Autowired
	private OwnerRepository owners;

	@Autowired
	private VisitRepository visits;

	@Autowired
	private BillRepository bills;

	public Visit findPetWithVisit(int ownerId, int petId, Map<String, Object> model) {
		Owner owner = this.owners.findById(ownerId);

		Pet pet = owner.getPet(petId);
		model.put("pet", pet);
		model.put("owner", owner);

		Visit visit = new Visit();
		pet.addVisit(visit);
		return visit;
	}

	public Integer addVisitToPet(Owner owner, Integer petId, Visit v) {
		owner.addVisit(petId, v);
		this.owners.save(owner);
		return owner.getId();
	}

	public List<Visit> findVisits(String f) {
		List<Visit> visitsResult = new ArrayList<>();
		switch (f) {
		case "pagadas":
			for (Visit v : visits.findAll()) {
				if (v.getBill().getBill_date() != null) {
					visitsResult.add(v);
				}
			}
			break;
		case "no_pagadas":
			for (Visit v : visits.findAll()) {
				if (v.getBill().getBill_date() == null) {
					visitsResult.add(v);
				}
			}
		}
		return visitsResult;
	}

}
