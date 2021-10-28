package com.farman.ledgerco.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Payment {

	private final String bankName;
	private final String borrowerName;
	private BigDecimal lumSumAmt;
	private Integer emiNo;
	
	 
}
