package org.springframework.samples.petclinic.bill.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.bill.DAO.BillRepository;
import org.springframework.samples.petclinic.bill.DTO.Bill;
import org.springframework.samples.petclinic.visit.DAO.VisitRepository;
import org.springframework.samples.petclinic.visit.DTO.Visit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BillService {

	@Autowired
	public BillRepository bills;

	@Autowired
	public VisitRepository visits;

	public List<Bill> findBills(String f) {
		List<Bill> billsResult = new ArrayList<>();
		switch (f.trim().toLowerCase(Locale.ROOT)) {
		case "pagadas":
			billsResult = bills.getPaidBills();
			break;
		case "no_pagadas":
			billsResult = bills.getUnpaidBills();
			break;
		}
		return billsResult;
	}

	public Visit showVisitDetails(Integer billId, Integer visitId) {
		Bill b = bills.findById(billId);
		Visit v = visits.findById(visitId);
		if (b.equals(v.getBill())) {
			return v;
		}
		return null;
	}

	public Visit updateVisitDetails(Integer billId, Integer visitId) {
		Bill b = bills.findById(billId);
		Visit v = visits.findById(visitId);
		if (!(b.getId().equals(v.getBill().getId()))) {
			v.setBill(b);
			visits.save(v);
			v = visits.findById(visitId);
		}
		return v;
	}

}
