package com.lendico.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Guseva Maria
 */
public class PlanElement {
    private static final int NUM_MONTHS = 12;
    private static final int NUM_DAYS = 30;

    private Date date;
    private double annuity;
    private double principal;
    private double interest;
    private double initialOutstandingPrincipal;
    private double remainingOutstandingPrincipal;

    public PlanElement(Date date, double initialOutstandingPrincipal, double interestRate,
                       int numPeriods) {
        this.date = date;
        this.initialOutstandingPrincipal = initialOutstandingPrincipal;
        this.annuity = calculateAnnuity(initialOutstandingPrincipal, interestRate, numPeriods);
        this.interest = calculateInterest(interestRate, initialOutstandingPrincipal);
        this.principal = calculatePrincipal(this.annuity, this.interest);
        this.remainingOutstandingPrincipal = calculateRemainingPrincipal(this.initialOutstandingPrincipal, this.principal);
    }

    public double getRemainingOutstandingPrincipal() {
        return remainingOutstandingPrincipal;
    }

    private static double calculateAnnuity(double initialOutstandingPrincipal, double interestRate, int numPeriods){
        double monthlyRate = interestRate/(NUM_MONTHS * 100);
        double numerator = monthlyRate * initialOutstandingPrincipal;
        double denominator = 1 - Math.pow(1 + monthlyRate, -numPeriods);
        double payment = numerator / denominator;

        return (Math.round(payment*100.0)/100.0);
    }

    private static double calculateInterest(double interestRate, double initialOutstandingPrincipal){
        double interest = (interestRate / 100 * NUM_DAYS * initialOutstandingPrincipal) / (NUM_DAYS * NUM_MONTHS);
        return (Math.round(interest*100.0)/100.0);
    }

    private static double calculatePrincipal(double annuity, double interest){
        double principal = annuity - interest;
        return (Math.round(principal*100.0)/100.0);
    }

    private static double calculateRemainingPrincipal(double initialOutstandingPrincipal, double principal){
        double remainingPrincipal = initialOutstandingPrincipal - principal;
        return (Math.round(remainingPrincipal*100.0)/100.0);
    }

    public String toString(SimpleDateFormat dateFormat){
        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(this.date));
        sb.append(',');
        sb.append(this.annuity);
        sb.append(',');
        sb.append(this.principal);
        sb.append(',');
        sb.append(this.interest);
        sb.append(',');
        sb.append(this.initialOutstandingPrincipal);
        sb.append(',');
        sb.append(this.remainingOutstandingPrincipal);
        sb.append('\n');
        return sb.toString();
    }
}
