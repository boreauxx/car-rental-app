package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Motorcycle extends Vehicle {

    private static final double MOTORCYCLE_RENTAL_PRICE = 15.00;
    private static final double MOTORCYCLE_RENTAL_PRICE_DISCOUNTED = 10.00;
    private static final double MOTORCYCLE_INSURANCE = 0.2;
    private static final double MOTORCYCLE_INSURANCE_SURCHARGE = 0.20;

    private double rentalCost;
    private double insurance;
    private int ageLimit;

    public Motorcycle(String brand, String model, double value, int ageLimit) {
        super(brand, model, value, MOTORCYCLE_RENTAL_PRICE, value * MOTORCYCLE_INSURANCE);
        this.ageLimit = ageLimit;
    }

    public void setDiscountInsurance() {
        this.insurance = this.insurance + this.insurance * MOTORCYCLE_INSURANCE_SURCHARGE;
    }

    public void setDiscountRentalCost() {
        this.rentalCost = MOTORCYCLE_RENTAL_PRICE_DISCOUNTED;
    }

    public double getInitInsurance(){
        return MOTORCYCLE_INSURANCE;
    }

    public double getInsuranceModifier(){
        return 0.0;
    }
}
