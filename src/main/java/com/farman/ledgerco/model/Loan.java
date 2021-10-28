package com.farman.ledgerco.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Loan {

	private final String bankName;
	private final String borrowerName;
	private final BigDecimal principalAmt;
	private final BigDecimal totalPayableAmt;
	private final BigDecimal totalInterest;
	private final Double interestRate;
	private final Double tenure;
	private final Long montlyEmi;
	
}
