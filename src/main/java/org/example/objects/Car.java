package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car extends Vehicle {

    private static final double CAR_RENTAL_PRICE = 20.00;
    private static final double CAR_RENTAL_PRICE_DISCOUNTED = 15.00;
    private static final double CAR_INSURANCE = 0.0001;
    private static final double CAR_INSURANCE_DISCOUNT = 0.10;

    private int safetyRating;

    public Car(String brand, String model, double value, int safetyRating) {
        super(brand, model, value, CAR_RENTAL_PRICE, value * CAR_INSURANCE);
        this.safetyRating = safetyRating;
    }

    public double getInitInsurance() {
        return CAR_INSURANCE * getValue();
    }

    public void setDiscountRentalCost() {
        setRentalCost(CAR_RENTAL_PRICE_DISCOUNTED);
    }

    public void setModifiedInsurance() {
        setInsurance(getInsurance() - getInsuranceModifier());
    }

    public double getInsuranceModifier() {
        return getInsurance() * CAR_INSURANCE_DISCOUNT;
    }

}
