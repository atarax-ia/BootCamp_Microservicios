package org.springframework.samples.petclinic.pet.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.pet.DTO.Pet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PetRepository extends Repository<Pet, Integer> {

	@Query("SELECT p FROM Pet p WHERE p.id =:id")
	@Transactional(readOnly = true)
	Pet findById(@Param("id") Integer id);

	void save(Pet pet);

	@Query("SELECT p FROM Pet p WHERE (EXTRACT(YEAR FROM p.birthDate) = :year) ORDER BY p.birthDate ASC")
	@Transactional(readOnly = true)
	List<Pet> findByYearOfBirthDate(@Param("year") Integer year);

	// @Query("SELECT v FROM (Visit v, (SELECT p FROM Pet p LEFT JOIN FETCH p.visits))
	// WHERE v.id = p.id AND p.id = :id")
	// Set<Visit> findByVisitsAndPetId(@Param("id") Integer id);

}
