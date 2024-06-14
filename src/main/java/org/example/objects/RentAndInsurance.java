package org.example.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class RentAndInsurance {

    private double fullyChargedRent;
    private double fullyChargedInsurance;

    private double discountedRent;
    private double discountedInsurance;

    private double totalRentPaid;
    private double totalInsurancePaid;
    private double total;
}
