package org.example.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter @AllArgsConstructor
public class RentAndInsurance {

    private BigDecimal fullyChargedRent;
    private BigDecimal fullyChargedInsurance;

    private BigDecimal discountedRent;
    private BigDecimal discountedInsurance;

    private BigDecimal totalRentPaid;
    private BigDecimal totalInsurancePaid;
    private BigDecimal total;
}
