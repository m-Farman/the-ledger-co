package com.farman.ledgerco.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farman.ledgerco.exception.InvalidLoanParameter;
import com.farman.ledgerco.model.Loan;
import com.farman.ledgerco.repository.LoanRespository;

import lombok.NonNull;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	LoanRespository loanRespository;

	@Autowired
	ValidateService validateService;

	@Override
	public Loan createLoan(@NonNull final String bankName, @NonNull final String borrowerName,
			@NonNull final BigDecimal principal, @NonNull final Double noOfYears, @NonNull final Double interestRate)
			throws InvalidLoanParameter {

		if (validateService.isInvalidLoanArguements(bankName, borrowerName, principal, noOfYears, interestRate)) {
			throw new InvalidLoanParameter();
		}

		final BigDecimal totalInterest = principal.multiply(BigDecimal.valueOf(noOfYears * interestRate))
				.divide(BigDecimal.valueOf(100));
		final BigDecimal totalPayableAmt = principal.add(totalInterest);
		final Long monthlyEmi = totalPayableAmt.divide(BigDecimal.valueOf(noOfYears * 12), 0, RoundingMode.CEILING)
				.longValue();
		final Loan loan = new Loan(bankName, borrowerName, principal, totalPayableAmt, totalInterest, interestRate,
				noOfYears, monthlyEmi);
		loanRespository.saveLoan(loan);
		return loan;
	}

	@Override
	public Loan getLoan(@NonNull final String bankName, @NonNull final String borrowerName) {
		return loanRespository.getLoan(bankName, borrowerName);
	}

}
