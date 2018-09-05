package com.lendico.main;

import com.lendico.models.Loan;
import com.lendico.models.PlanElement;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Guseva Maria
 */
public class Main {
    private static final int NUM_MONTHS = 12;

    public static void main(String[] args) {
        Loan loan = new Loan();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();

        try {
            System.out.print("Enter a duration: ");
            loan.setDuration(scanner.nextInt());
            System.out.print("Enter a nominal interest rate: ");
            loan.setInterestRate(scanner.nextDouble());
            System.out.print("Enter an amount: ");
            loan.setAmount(scanner.nextDouble());
            System.out.print("Enter a date of disbursement: ");
            loan.setStartDate(dateFormat.parse(scanner.next()));
        } catch (Exception e){
            System.out.println("Incorrect input data type");
        }

        Date date = loan.getStartDate();
        double initialOutstandingPrincipal = loan.getAmount();
        int numPeriods = loan.getDuration() * NUM_MONTHS;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("plan.csv"));
            for (int i = 0; i < numPeriods; i++) {
                PlanElement planElement = new PlanElement(date, initialOutstandingPrincipal, loan.getInterestRate(), numPeriods - i);
                initialOutstandingPrincipal = planElement.getRemainingOutstandingPrincipal();
                date = addMonths(calendar, date, 1);
                pw.write(planElement.toString(dateFormat));
            }
        } catch (Exception e){
            System.err.println(e.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
            else {
                System.out.println("PrintWriter wasn't opened");
            }
        }
    }

    private static Date addMonths(Calendar calendar, Date oldDate, int numMonth){
        calendar.setTime(oldDate);
        calendar.add(Calendar.MONTH, numMonth);
        return calendar.getTime();
    }


}
