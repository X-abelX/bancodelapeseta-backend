package com.banco.services;
import com.banco.entities.Loan;
import com.banco.entities.LoanPayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculateAmortizationService {

    public List<LoanPayment> calculateAmortization(Loan loan) {
        List<LoanPayment> payments = new ArrayList();
        BigDecimal remainingBalance = loan.getAmount();
        BigDecimal monthlyInterestRate = loan.getInterestRate().divide(BigDecimal.valueOf(12), 7, RoundingMode.HALF_UP);
        int totalPayments = loan.getLoanNumberPayments();
        BigDecimal monthlyPayment = this.calculateMonthlyPayment(loan.getAmount(), monthlyInterestRate, totalPayments);

        for(int i = 0; i < totalPayments; ++i) {
            BigDecimal interestPayment = remainingBalance.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principalPayment = monthlyPayment.subtract(interestPayment).setScale(2, RoundingMode.HALF_UP);
            remainingBalance = remainingBalance.subtract(principalPayment).setScale(2, RoundingMode.HALF_UP);
            LoanPayment payment = new LoanPayment();
            payment.setPaymentNumber(i + 1);
            payment.setPaymentAmount(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            payment.setInterestPaid(interestPayment);
            payment.setAmortization(principalPayment);
            payment.setRemainingBalance(remainingBalance);
            payments.add(payment);
        }

        return payments;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal monthlyInterestRate, int totalPayments) {
        BigDecimal numerator = loanAmount.multiply(monthlyInterestRate).multiply((BigDecimal.ONE.add(monthlyInterestRate)).pow(totalPayments));
        BigDecimal denominator = ((BigDecimal.ONE.add(monthlyInterestRate)).pow(totalPayments)).subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 7, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }
}
