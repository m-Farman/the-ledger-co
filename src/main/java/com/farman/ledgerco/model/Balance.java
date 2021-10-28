package com.farman.ledgerco.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Balance {
	
	private String bankName;
	private String borrowerName;
	private BigDecimal amountPaid;
	private Integer noOfEmiLeft;

	@Override
	public String toString() {
		return bankName+" "+borrowerName+" "+amountPaid+" "+noOfEmiLeft;
	}
}

