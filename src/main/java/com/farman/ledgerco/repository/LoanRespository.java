package com.farman.ledgerco.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.farman.ledgerco.model.Loan;

import lombok.NonNull;

@Component
public class LoanRespository {

	private final Map<String, Map<String, Loan>> loanDB;

	public LoanRespository() {
		loanDB = new ConcurrentHashMap<>();
	}

	public Loan getLoan(@NonNull String bankName, @NonNull String borrowerName) {
		Map<String, Loan> bankLoans = loanDB.get(bankName);
		if (bankLoans == null) {
			return null;
		}

		return bankLoans.get(borrowerName);
	}

	public void saveLoan(@NonNull final Loan loan) {
		Map<String, Loan> bankLoans = loanDB.get(loan.getBankName());
		if (bankLoans == null) {
			synchronized (this) { // restrict single map creation
				if (bankLoans == null) {
					bankLoans = new ConcurrentHashMap<String, Loan>();
					loanDB.put(loan.getBankName(), bankLoans);
				}
			}
		}

		bankLoans.put(loan.getBorrowerName(), loan);
	}

}
