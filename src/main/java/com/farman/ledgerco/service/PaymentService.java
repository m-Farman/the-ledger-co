package com.farman.ledgerco.service;

import java.math.BigDecimal;

import com.farman.ledgerco.exception.InvalidPaymentParameter;
import com.farman.ledgerco.model.Payment;

public interface PaymentService {

	public Payment acceptPayment(String bankName, String borrowerName, BigDecimal lumSumAmt, Integer emiNo) throws InvalidPaymentParameter;
	
	public BigDecimal getLumSumPaymentUntil(String bankName, String borrowerName,Integer emiNo);

}
