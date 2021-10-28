package com.farman.ledgerco.service;

import java.math.BigDecimal;

import com.farman.ledgerco.exception.InvalidLoanParameter;
import com.farman.ledgerco.exception.LoanNotFoundException;
import com.farman.ledgerco.model.Balance;

public interface BalanceService {

	public Balance getBalance(String bankName, String borrowerName, Integer emiNo) throws InvalidLoanParameter, LoanNotFoundException;
}
