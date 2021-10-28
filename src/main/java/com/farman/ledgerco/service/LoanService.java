package com.farman.ledgerco.service;

import java.math.BigDecimal;

import com.farman.ledgerco.exception.InvalidLoanParameter;
import com.farman.ledgerco.model.Loan;

public interface LoanService {

	public Loan createLoan(String bankName, String borrowerName, BigDecimal principal, Double noOfYears,
			Double interestRate) throws InvalidLoanParameter;
	
	public Loan getLoan(String bankName, String borrowerName);
}
