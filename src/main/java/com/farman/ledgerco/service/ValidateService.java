package com.farman.ledgerco.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ValidateService {

	public boolean isInvalidLoanArguements(String bankName, String borrowerName, BigDecimal principal, Double noOfYears,
			Double interestRate) {
		if (bankName.isEmpty() || borrowerName.isEmpty()) {
			return true;
		}
		if (noOfYears < 0 || interestRate < 0 || principal.doubleValue() < 0) {
			return true;
		}
		return false;
	}

	public boolean isInvalidPaymentArguements(String bankName, String borrowerName, BigDecimal lumSumAmt, Integer emiNo) {
		if (bankName.isEmpty() || borrowerName.isEmpty()) {
			return true;
		}
		if (lumSumAmt.doubleValue() < 0 || emiNo < 0) {
			return true;
		}
		return false;
	}
	
	public boolean isInvalidBalanceArguements(String bankName, String borrowerName, Integer emiNo) {
		if (bankName.isEmpty() || borrowerName.isEmpty()) {
			return true;
		}
		if (emiNo < 0) {
			return true;
		}
		return false;
	}
}
