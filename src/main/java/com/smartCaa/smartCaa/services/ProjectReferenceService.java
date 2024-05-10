package com.smartCaa.smartCaa.services;

import com.smartCaa.smartCaa.DTOS.CalculatorReturnDto;
import com.smartCaa.smartCaa.models.KeyAssumptions;
import com.smartCaa.smartCaa.models.ProjectReference;
import com.smartCaa.smartCaa.repositories.KeyAssumptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
@Service
public class ProjectReferenceService {

    @Autowired
    private KeyAssumptionsRepository keyAssumptionsRepository;


    public static Double calculateFinanceCharges(Double value1, Double value2, Double value3, Map<String, Double> parameters) {

        Double firstValue = value1 + value2 + value3;

        Double secondValue = 0.0;
        if (parameters.containsKey("prototypes sold")) {
            secondValue = parameters.get("prototypes sold");
        }

        return (firstValue - secondValue) * 0 -3640.0;
    }

    private static double calculateNumberOfYears(LocalDate startDate, LocalDate endDate) {
        long years = ChronoUnit.YEARS.between(startDate, endDate);
        return years + 1;
    }

    public List<ProjectReference> useCalculator(){

        Boolean sameAmortAmountForAllRef = Boolean.valueOf(keyAssumptionsRepository.findByKeyIgnoreCase("sameAmortAmountForAllRef").orElse(null).getValue());
        KeyAssumptions byKeyIgnoreCase = keyAssumptionsRepository.findByKeyIgnoreCase("costOfcapital").orElse(null);
        Double percentage = Double.valueOf(keyAssumptionsRepository.findByKeyIgnoreCase("volumeAdjInPercentage").orElse(null).getValue());


        LocalDate startDate = LocalDate.of(2026, 2, 1); // Será baseado no projeto (table)
        LocalDate endDate = LocalDate.of(2032, 12, 1); // Será baseado no projeto (table)

        double years = calculateNumberOfYears(startDate, endDate);
        double commonAmount = 3.42;
        double costOfCapital = 0.0;

        if(byKeyIgnoreCase != null){
            costOfCapital = Double.parseDouble(byKeyIgnoreCase.getValue());
        }

        List<ProjectReference> items = new ArrayList<>();
        // Irá ter uma table de projeto no banco, a partir dessa table irei pegar os dados e montar um project reference (usado para calular)
        ProjectReference projectReference1 = new ProjectReference(costOfCapital, 83, years, percentage, 564760, "RAD 18SHP_N3&EB2");
        ProjectReference projectReference2 = new ProjectReference(costOfCapital, 83, years, percentage, 108868, "Radiador 21VHP - PSA T3 CC2X");
        ProjectReference projectReference3 = new ProjectReference(costOfCapital, 83, years, percentage, 108868, "CDS 12 SHP+ T3");
        ProjectReference projectReference4 = new ProjectReference(costOfCapital, 83, years, percentage, 108868, "Module T3");
        ProjectReference projectReference5 = new ProjectReference(costOfCapital, 83, years, percentage, 564760, "Module N3/EB2");
        items.add(projectReference1);
        items.add(projectReference2);
        items.add(projectReference3);
        items.add(projectReference4);
        items.add(projectReference5);


        Double maxMonth = items.stream().map(ProjectReference::getMonths).max(Double::compareTo).orElse(0.0);
        Double maxYear = items.stream().map(ProjectReference::getNumberofyears).max(Double::compareTo).orElse(0.0);
        Double totalUnitsForAmort = items.stream().map(ProjectReference::getUnitsforAmort).reduce(Double::sum).orElse(0.0);
        Double adjustedTotalUnitsForAmort = totalUnitsForAmort * percentage;
        Double amountToBeFinancedInKBRL = calculateFinanceCharges(-6385.6, 0.0, 0.0, getFakeMap());


        items.forEach(item -> item.calculate(sameAmortAmountForAllRef, amountToBeFinancedInKBRL, totalUnitsForAmort,-3640, commonAmount));

        Double totalFinanceCharges = items.stream().map(ProjectReference::getFinanceCharges).reduce(Double::sum).orElse(0.0); //Wrong
        Double totalAnnualVolume = items.stream().map(ProjectReference::getAvgAnnualVolume).reduce(Double::sum).orElse(0.0);  //Wrong

        ProjectReference totalValue = new ProjectReference(costOfCapital, maxMonth.intValue(), maxYear, percentage, totalUnitsForAmort , "Total");
        totalValue.setAdjustedVolumeAmort(adjustedTotalUnitsForAmort);
        totalValue.setAmortToBeFinancedInKBRL(amountToBeFinancedInKBRL);
        totalValue.setAvgAnnualVolume(totalAnnualVolume);
        totalValue.setFinanceCharges(totalFinanceCharges);
        totalValue.setAmortizationPiecePrice(totalValue.calculateAmortizationPiecePriceRd(commonAmount,totalUnitsForAmort, totalFinanceCharges, sameAmortAmountForAllRef ));
        items.add(totalValue);

        return items;

    }



//    public static void main(String[] args) {
//
//        Boolean sameAmortAmountForAllRef = true;
//        double costOfcapital = 0.095;
//        double commonAmount = 3.42;
//
//        LocalDate startDate = LocalDate.of(2026, 2, 1); //Pode ser independente
//        LocalDate endDate = LocalDate.of(2032, 12, 1); //Pode ser independente
//
//
//        double volumeAdjInPercentage = 1.0; //equivalent to 100%
//
//
//        List<ProjectReference> items = new ArrayList<>();
//
//        double years = calculateNumberOfYears(startDate, endDate);
//
//        ProjectReference projectReference1 = new ProjectReference(costOfcapital, 83,years, volumeAdjInPercentage, 564760);
//        ProjectReference projectReference2 = new ProjectReference(costOfcapital, 83,years, volumeAdjInPercentage, 108868);
//        ProjectReference projectReference3 = new ProjectReference(costOfcapital, 83,years, volumeAdjInPercentage, 108868);
//        ProjectReference projectReference4 = new ProjectReference(costOfcapital, 83,years, volumeAdjInPercentage, 108868);
//        ProjectReference projectReference5 = new ProjectReference(costOfcapital, 83,years, volumeAdjInPercentage, 564760);
//
//        items.add(projectReference1);
//        items.add(projectReference2);
//        items.add(projectReference3);
//        items.add(projectReference4);
//        items.add(projectReference5);
//
//        Double totalAnnualVolume = items.stream().map(ProjectReference::getAvgAnnualVolume).reduce(Double::sum).orElse(0.0);
//        Double maxMonth = items.stream().map(ProjectReference::getMonths).max(Double::compareTo).orElse(0.0);
//        Double maxYear = items.stream().map(ProjectReference::getNumberofyears).max(Double::compareTo).orElse(0.0);
//        Double totalUnitsForAmort = items.stream().map(ProjectReference::getUnitsforAmort).reduce(Double::sum).orElse(0.0);
//        Double adjustedTotalUnitsForAmort = totalUnitsForAmort * volumeAdjInPercentage;
//        Double amountToBeFinancedInKBRL = calculateFinanceCharges(-6385.6, 0.0, 0.0, getFakeMap());
//
//        items.forEach(item -> item.calculate(sameAmortAmountForAllRef, amountToBeFinancedInKBRL, totalUnitsForAmort,-3640, commonAmount));
//
//        System.out.println("totalAnnualVolume = " + totalAnnualVolume);
//        System.out.println("maxMonth = " + maxMonth);
//        System.out.println("maxYear = " + maxYear);
//        System.out.println("adjustedTotalUnitsForAmort = " + adjustedTotalUnitsForAmort);
//        items.forEach(System.out::println);
//
//    }

    public static Map<String, Double> getFakeMap() {

        Map<String, Double> fakeMap = new HashMap<String, Double>();
        fakeMap.put("LS workload", -2199.5);
        fakeMap.put("LS workload", -186.0);
        fakeMap.put("Prototypes Sold", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("Other", 0.0);
        fakeMap.put("Other", -28.0);
        fakeMap.put("Other", -84.0);
        fakeMap.put("Other", -123.6);
        fakeMap.put("Other", -509.5);
        fakeMap.put("Other", -338.2);
        fakeMap.put("Other", -100.0);
        fakeMap.put("Other", 0.0);
        fakeMap.put("Other", 0.0);
        fakeMap.put("Other", -66.0);
        fakeMap.put("Other", -52.0);
        fakeMap.put("Other", -8.0);
        fakeMap.put("Other", -30.0);
        fakeMap.put("Other", -48.9);
        fakeMap.put("Other", -25.0);
        fakeMap.put("Other", -35.0);
        fakeMap.put("Other", -20.0);
        fakeMap.put("Other", -15.0);
        fakeMap.put("Other", -75.0);
        fakeMap.put("Other", -30.0);
        fakeMap.put("Other", -135.0);
        fakeMap.put("Other", -393.6);
        fakeMap.put("Interco Header&Cover", -59.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("Other", -667.3);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("Other", -700.0);
        fakeMap.put("Other", -157.0);
        fakeMap.put("Other", -300.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);
        fakeMap.put("", 0.0);

        return fakeMap;
    }

}
