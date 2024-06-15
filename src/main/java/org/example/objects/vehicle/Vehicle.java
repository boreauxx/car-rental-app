package org.example.objects.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public abstract class Vehicle {

    private String brand;
    private String model;
    private BigDecimal value;
    private BigDecimal rentalCost;
    private BigDecimal insurance;
    private boolean modified = false;

    public abstract void setDiscountRentalCost();

    public abstract BigDecimal getInitInsurance();

    public abstract void setModifiedInsurance();

    public abstract BigDecimal getInsuranceModifier();

}
