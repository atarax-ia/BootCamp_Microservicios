package org.springframework.samples.petclinic.visit.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.visit.DTO.Visit;

import java.util.List;

public interface VisitReppository extends Repository<Visit, Integer> {

	void save(Visit v);

	@Query("SELECT v FROM Visit v ORDER BY v.date DESC")
	List<Visit> findAllInDescendingOrder();

}
