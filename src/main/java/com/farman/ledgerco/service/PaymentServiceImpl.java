package com.farman.ledgerco.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farman.ledgerco.exception.InvalidPaymentParameter;
import com.farman.ledgerco.model.Payment;
import com.farman.ledgerco.repository.PaymentRepository;

import lombok.NonNull;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	ValidateService validateService;

	@Override
	public Payment acceptPayment(@NonNull final String bankName, @NonNull final String borrowerName,
			@NonNull final BigDecimal lumSumAmt, @NonNull final Integer emiNo) throws InvalidPaymentParameter {

		if (validateService.isInvalidPaymentArguements(bankName, borrowerName, lumSumAmt, emiNo)) {
			throw new InvalidPaymentParameter();
		}
		final Payment payment = new Payment(bankName, borrowerName, lumSumAmt, emiNo);
		paymentRepository.addPayment(payment);
		return payment;
	}

	@Override
	public BigDecimal getLumSumPaymentUntil(String bankName, String borrowerName, Integer emiNo) {
		List<Payment> allPayments = paymentRepository.getPayments(bankName, borrowerName);
		if (Objects.isNull(allPayments)|| allPayments.isEmpty()) {
			return BigDecimal.ZERO;
		}
		
		return allPayments.stream().filter(p -> p.getEmiNo() <= emiNo).map(Payment::getLumSumAmt)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

	}

}
