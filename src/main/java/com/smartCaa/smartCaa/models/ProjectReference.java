package com.smartCaa.smartCaa.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProjectReference {

    private static final int MONTHS_IN_YEAR = 12;
    private double avgAnnualVolume;
    private double numberofyears;
    private double months;
    private double costOfCapital;
    private double unitsforAmort;
    private double volumeAdjinPercentage;
    private double adjustedVolumeAmort;
    private double amortToBeFinancedInKBRL;
    private double financeCharges;
    private double amortizationPiecePrice;
    private String projectName;

    public ProjectReference(double costOfCapital, Integer months, Double numberofyears,
                          double volumeAdjinPercentage, double unitsforAmort, String projectName) {

        this.volumeAdjinPercentage = volumeAdjinPercentage;
        this.unitsforAmort = unitsforAmort;
        this.costOfCapital = costOfCapital;
        this.months = months;
        this.numberofyears = numberofyears;
        this.projectName = projectName;
    }

    public void calculate(Boolean sameAmortAmountForAllRef, double totalAmortToBeFinancedInKBRL, double totalUnitsforAmort, double totalFinanceCharges, double commonAmount){

        this.avgAnnualVolume = calculateAvgAnnualVolume(unitsforAmort, numberofyears);
        this.adjustedVolumeAmort = Math.floor(calculateAdjustedVolumeRdAmort(unitsforAmort,volumeAdjinPercentage));
        this.amortToBeFinancedInKBRL = Math.floor(calculateRdAmountFinanced(totalAmortToBeFinancedInKBRL , unitsforAmort, totalUnitsforAmort));
        this.financeCharges = Math.floor(calculateTotalRdFinanceCharges(costOfCapital, months, this.amortToBeFinancedInKBRL));
        this.amortizationPiecePrice = calculateAmortizationPiecePriceRd(commonAmount, totalUnitsforAmort, totalFinanceCharges, sameAmortAmountForAllRef);

    }

    private double calculateAvgAnnualVolume(double totalUnitsForAmort, double numberOfYears) {
        return totalUnitsForAmort == 0 ? 0 : totalUnitsForAmort / numberOfYears;
    }


    private static double calculateRdAmountFinanced( double rdAmount, double volumeRdAmort, double totalVolumeForAmort) {
        return rdAmount * (volumeRdAmort / totalVolumeForAmort);
    }

    private double calculateTotalRdFinanceCharges(double costOfCapital, double months, double rdAmountFinanced) {
        double monthlyInterestRate = costOfCapital / MONTHS_IN_YEAR;
        double pmt = rdAmountFinanced * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -months));
        return Double.isNaN(pmt) || Double.isInfinite(pmt) ? 0 : pmt * months;
    }

    public double calculateAmortizationPiecePriceRd(double commonAmount, double units,
                                                     double totalRdFinanceCharges, boolean sameAmortAmountForAllRef) {
        return units == 0 ? 0 :
                sameAmortAmountForAllRef ? commonAmount :
                        (-totalRdFinanceCharges / units) * 1000;
    }



    private static double calculateAdjustedVolumeRdAmort(double totalUnitsForAmort, double percentage) {
        return totalUnitsForAmort * percentage;
    }

}

