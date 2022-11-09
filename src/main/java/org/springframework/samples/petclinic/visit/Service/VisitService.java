package org.springframework.samples.petclinic.visit.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.DAO.OwnerRepository;
import org.springframework.samples.petclinic.owner.DTO.Owner;
import org.springframework.samples.petclinic.pet.DTO.Pet;
import org.springframework.samples.petclinic.visit.DTO.Visit;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VisitService {

	@Autowired
	private final OwnerRepository owners;

	public VisitService(OwnerRepository owners) {
		this.owners = owners;
	}

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

}