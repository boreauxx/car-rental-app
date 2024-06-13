package org.example.objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CargoVan extends Vehicle {

    private static final double CARGO_VAN_RENTAL_PRICE = 50.00;
    private static final double CARGO_VAN_RENTAL_PRICE_DISCOUNTED = 40.00;
    private static final double CARGO_VAN_INSURANCE = 0.3;
    private static final double CARGO_VAN_INSURANCE_DISCOUNT = 0.15;

    private double rentalCost;
    private double insurance;
    private int experience;

    public CargoVan(String brand, String model, double value, int experience) {
        super(brand, model, value, CARGO_VAN_RENTAL_PRICE, value * CARGO_VAN_INSURANCE);
        this.experience = experience;
    }

    public void setDiscountInsurance() {
        this.insurance = this.insurance - this.insurance * CARGO_VAN_INSURANCE_DISCOUNT;
    }

    public void setDiscountRentalCost() {
        this.rentalCost = CARGO_VAN_RENTAL_PRICE_DISCOUNTED;
    }

    public double getInitInsurance(){
        return CARGO_VAN_INSURANCE;
    }

    public double getInsuranceModifier(){
        return 0.0;
    }
}
