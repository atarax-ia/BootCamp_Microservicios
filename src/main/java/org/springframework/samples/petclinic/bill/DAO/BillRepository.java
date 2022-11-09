package org.springframework.samples.petclinic.bill.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.bill.DTO.Bill;

import java.util.List;

public interface BillRepository extends Repository<Bill, Integer> {

	void save(Bill b);

	Bill findById(Integer id);

	@Query("SELECT b FROM Bill b WHERE b.bill_date IS NOT NULL")
	List<Bill> getPaidBills();

	@Query("SELECT b FROM Bill b WHERE b.bill_date IS NULL")
	List<Bill> getUnpaidBills();

	List<Bill> findAll();

}
