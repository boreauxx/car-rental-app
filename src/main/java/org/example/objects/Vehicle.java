package org.example.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Vehicle {

    private String brand;
    private String model;
    private double value;
    private double rentalCost;
    private double insurance;

    public abstract double getInitInsurance();

    public abstract void setDiscountRentalCost();

    public abstract void setModifiedInsurance();

    public abstract double getInsuranceModifier();
}
