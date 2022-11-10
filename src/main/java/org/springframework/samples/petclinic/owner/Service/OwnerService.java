package org.springframework.samples.petclinic.owner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.owner.DTO.Owner;
import org.springframework.samples.petclinic.owner.DAO.OwnerRepository;
import org.springframework.samples.petclinic.pet.DTO.PetType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Service
public class OwnerService {

	@Autowired
	private OwnerRepository owners;

	private String addPaginationModel(int page, Model model, Page<Owner> paginated) {
		model.addAttribute("listOwners", paginated);
		List<Owner> listOwners = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listOwners", listOwners);
		return "owners/ownersList";
	}

	private Page<Owner> findPaginatedForOwnersLastName(int page, String lastname) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return owners.findByLastName(lastname, pageable);
	}

	public Owner findById(Integer id) {
		return this.owners.findById(id);
	}

	public Owner getOwner(Integer ownerId) {
		return ownerId == null ? new Owner() : this.owners.findById(ownerId);
	}

	public Owner createNewOwner() {
		return new Owner();
	}

	public Owner saveOwner(Owner o) {
		this.owners.save(o);
		return o;
	}

	public Integer updateOwner(Owner o, Integer id) {
		o.setId(id);
		this.owners.save(o);
		return id;
	}

	public ModelAndView showOwner(Integer id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Owner owner = this.owners.findById(id);
		mav.addObject(owner);
		return mav;
	}

	public String findOwnersForForm(int page, Owner o, BindingResult r, Model m) {
		if (o.getLastName() == null) {
			o.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Page<Owner> ownersResults = findPaginatedForOwnersLastName(page, o.getLastName());
		if (ownersResults.isEmpty()) {
			// no owners found
			r.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}

		if (ownersResults.getTotalElements() == 1) {
			// 1 owner found
			o = ownersResults.iterator().next();
			return "redirect:/owners/" + o.getId();
		}
		return addPaginationModel(page, m, ownersResults);
	}

	public Collection<PetType> findPetTypes() {
		return this.owners.findPetTypes();
	}

    public void save(Owner owner) {
		owners.save(owner);
    }
}
