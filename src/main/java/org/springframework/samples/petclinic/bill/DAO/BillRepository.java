package org.springframework.samples.petclinic.bill.DAO;

import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.bill.DTO.Bill;

import java.util.List;

public interface BillRepository extends Repository<Bill, Integer> {

	void save(Bill b);

}
