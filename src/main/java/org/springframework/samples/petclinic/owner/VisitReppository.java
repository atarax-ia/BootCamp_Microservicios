package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitReppository extends Repository<Visit, Integer> {

	void save(Visit v);

	@Query("SELECT v FROM Visit v ORDER BY v.date DESC")
	List<Visit> findAllInDescendingOrder();

	//@Query("SELECT v FROM Visit v, Pet p WHERE v.id = p.visits AND p.id = :id")
	//List<Visit> findVisitsByPetId(@Param("id") Integer id);
}
