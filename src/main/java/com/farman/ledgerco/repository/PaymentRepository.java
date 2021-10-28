package com.farman.ledgerco.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.farman.ledgerco.model.Payment;

import lombok.NonNull;

@Component
public class PaymentRepository {

	private final Map<String, Map<String, List<Payment>>> paymentDB;

	public PaymentRepository() {
		this.paymentDB = new ConcurrentHashMap<>();
	}

	public void addPayment(@NonNull final Payment payment) {
		Map<String, List<Payment>> bankPayments = paymentDB.get(payment.getBankName());
		if (bankPayments == null) {
			synchronized (this) { // prevent multiple thread of creating map
				if (bankPayments == null) {
					bankPayments = new ConcurrentHashMap<String, List<Payment>>();
					paymentDB.put(payment.getBankName(), bankPayments);
				}		
			}	
		}
		
		List<Payment> borrowerPayments = bankPayments.get(payment.getBorrowerName());
		if (borrowerPayments == null) {
			synchronized (this) { // prevent multiple thread of creating list
				if (borrowerPayments == null) { 
					borrowerPayments = Collections.synchronizedList(new ArrayList<>());
					bankPayments.put(payment.getBorrowerName(), borrowerPayments);
				}
			}	
		}
		borrowerPayments.add(payment);
	}

	public List<Payment> getPayments(@NonNull String bankName, @NonNull String borrowerName) {
		Map<String, List<Payment>> bankPayments = paymentDB.get(bankName);
		if (bankPayments == null) {
			return null;
		}
		// return a copy of payment so as to prevent modification of payments array
		return new ArrayList<>(bankPayments.get(borrowerName)); 
	}

}
