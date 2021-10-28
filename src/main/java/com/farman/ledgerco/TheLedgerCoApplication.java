package com.farman.ledgerco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.farman.ledgerco.constant.CommandName;
import com.farman.ledgerco.model.Balance;
import com.farman.ledgerco.service.BalanceService;
import com.farman.ledgerco.service.LoanService;
import com.farman.ledgerco.service.PaymentService;

@SpringBootApplication
public class TheLedgerCoApplication implements CommandLineRunner {

	@Autowired
	BalanceService balanceService;
	@Autowired
	LoanService loanService;
	@Autowired
	PaymentService paymentService;

	public static void main(String[] args) {
		SpringApplication.run(TheLedgerCoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Please enter the path of input file");
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		String filePath = consoleInput.readLine();
		while (filePath == null || "".equals(filePath)) {
			System.out.println("Please enter correct file path");
			filePath = consoleInput.readLine();
		}
		consoleInput.close();

		BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			String[] commandInput = inputLine.split("\\s+");
			switch (CommandName.valueOf(commandInput[0].toUpperCase())) {
			case LOAN:
				if (commandInput.length != 6) {
					System.out.println("Invalid Input for Loan " + inputLine);
					break;
				}
				processLoan(commandInput);
				break;
			case PAYMENT:
				if (commandInput.length != 5) {
					System.out.println("Invalid Input for Payment " + inputLine);
					break;
				}
				processPayment(commandInput);
				break;
			case BALANCE:
				if (commandInput.length != 4) {
					System.out.println("Invalid Input for Balance " + inputLine);
					break;
				}
				processBalance(commandInput);
				break;
			default:
				System.out.println("Invalid Input " + inputLine);
				break;
			}
		}

	}

	private void processLoan(String[] commandInput) {
		String bankName = commandInput[1];
		String borrowerName = commandInput[2];
		BigDecimal principal = new BigDecimal(commandInput[3]);
		Double noOfYears = Double.parseDouble(commandInput[4]);
		Double interestRate = Double.parseDouble(commandInput[5]);

		try {
			loanService.createLoan(bankName, borrowerName, principal, noOfYears, interestRate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void processPayment(String[] commandInput) {
		String bankName = commandInput[1];
		String borrowerName = commandInput[2];
		BigDecimal lumSumAmt = new BigDecimal(commandInput[3]);
		Integer emiNo = Integer.parseInt(commandInput[4]);

		try {
			paymentService.acceptPayment(bankName, borrowerName, lumSumAmt, emiNo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private void processBalance(String[] commandInput) {
		String bankName = commandInput[1];
		String borrowerName = commandInput[2];
		Integer emiNo = Integer.parseInt(commandInput[3]);

		Balance outputBalance = null;

		try {
			outputBalance = balanceService.getBalance(bankName, borrowerName, emiNo);
			System.out.println(outputBalance);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
