package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Motorcycle extends Vehicle {

    private static final double MOTORCYCLE_RENTAL_PRICE = 15.00;
    private static final double MOTORCYCLE_RENTAL_PRICE_DISCOUNTED = 10.00;
    private static final double MOTORCYCLE_INSURANCE = 0.0002;
    private static final double MOTORCYCLE_INSURANCE_SURCHARGE = 0.20;

    private int ageLimit;

    public Motorcycle(String brand, String model, double value, int ageLimit) {
        super(brand, model, value, MOTORCYCLE_RENTAL_PRICE, value * MOTORCYCLE_INSURANCE);
        this.ageLimit = ageLimit;
    }

    public double getInitInsurance(){
        return MOTORCYCLE_INSURANCE * getValue();
    }

    public void setDiscountRentalCost() {
        setRentalCost(MOTORCYCLE_RENTAL_PRICE_DISCOUNTED);
    }

    public void setModifiedInsurance() {
        setInsurance(getInsurance() + getInsuranceModifier());
    }

    public double getInsuranceModifier(){
        return getInsurance() * MOTORCYCLE_INSURANCE_SURCHARGE;
    }
}
