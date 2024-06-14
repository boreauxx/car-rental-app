package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoVan extends Vehicle {

    private static final double CARGO_VAN_RENTAL_PRICE = 50.00;
    private static final double CARGO_VAN_RENTAL_PRICE_DISCOUNTED = 40.00;
    private static final double CARGO_VAN_INSURANCE = 0.0003;
    private static final double CARGO_VAN_INSURANCE_DISCOUNT = 0.15;

    private int experience;

    public CargoVan(String brand, String model, double value, int experience) {
        super(brand, model, value, CARGO_VAN_RENTAL_PRICE, value * CARGO_VAN_INSURANCE);
        this.experience = experience;
    }

    public double getInitInsurance(){
        return CARGO_VAN_INSURANCE * getValue();
    }

    public void setDiscountRentalCost() {
        setRentalCost(CARGO_VAN_RENTAL_PRICE_DISCOUNTED);
    }

    public void setModifiedInsurance() {
        setInsurance(getInsurance() - getInsuranceModifier());
    }

    public double getInsuranceModifier(){
        return getInsurance() * CARGO_VAN_INSURANCE_DISCOUNT;
    }
}
