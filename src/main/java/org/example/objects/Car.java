package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car extends Vehicle {

    private static final double CAR_RENTAL_PRICE = 20.00;
    private static final double CAR_RENTAL_PRICE_DISCOUNTED = 15.00;
    private static final double CAR_INSURANCE = 0.1;
    private static final double CAR_INSURANCE_DISCOUNT = 0.10;

    private double rentalCost;
    private double insurance;
    private int safetyRating;

    public Car(String brand, String model, double value, int safetyRating) {
        super(brand, model, value, CAR_RENTAL_PRICE, value * CAR_INSURANCE);
        this.safetyRating = safetyRating;
    }

    public void setDiscountInsurance() {
        this.insurance = this.insurance - this.insurance * CAR_INSURANCE_DISCOUNT;
    }

    public void setDiscountRentalCost() {
        this.rentalCost = CAR_RENTAL_PRICE_DISCOUNTED;
    }

    public double getInitInsurance() {
        return CAR_INSURANCE;
    }

    public double getInsuranceModifier() {
        return 0.0;
    }

}
