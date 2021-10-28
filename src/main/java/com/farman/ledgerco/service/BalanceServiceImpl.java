package com.farman.ledgerco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farman.ledgerco.exception.InvalidLoanParameter;
import com.farman.ledgerco.exception.LoanNotFoundException;
import com.farman.ledgerco.model.Balance;
import com.farman.ledgerco.model.Loan;

import lombok.NonNull;

@Service
public class BalanceServiceImpl implements BalanceService {

	@Autowired
	LoanService loanService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	ValidateService validateService;

	@Override
	public Balance getBalance(@NonNull final String bankName, @NonNull final String borrowerName,
			@NonNull final Integer emiNo) throws InvalidLoanParameter, LoanNotFoundException {

		if (validateService.isInvalidBalanceArguements(bankName, borrowerName, emiNo)) {
			throw new InvalidLoanParameter();
		}

		final Loan loan = loanService.getLoan(bankName, borrowerName);
		if (Objects.isNull(loan)) {
			throw new LoanNotFoundException();
		}

		Balance balane = new Balance();
		balane.setBankName(bankName);
		balane.setBorrowerName(borrowerName);
		Long totalEmiPaidAmt = loan.getMontlyEmi() * emiNo;
		// incase emi no is greater than tenure then loan is already paid
		if (emiNo > loan.getTenure() * 12) {
			balane.setAmountPaid(loan.getTotalPayableAmt());
			return balane;
		}

		BigDecimal totalLumSumAmt = paymentService.getLumSumPaymentUntil(bankName, borrowerName, emiNo);
		BigDecimal totalAmtPaidTillNow = totalLumSumAmt.add(BigDecimal.valueOf(totalEmiPaidAmt));
		if (loan.getTotalPayableAmt().compareTo(totalAmtPaidTillNow) <= 0) {
			return balane;
		}
		Integer noOfEmiRemaings = loan.getTotalPayableAmt().subtract(totalAmtPaidTillNow)
				.divide(BigDecimal.valueOf(loan.getMontlyEmi()), 0,RoundingMode.CEILING).intValue();

		balane.setAmountPaid(totalAmtPaidTillNow);
		balane.setNoOfEmiLeft(noOfEmiRemaings);
		return balane;
	}

}
