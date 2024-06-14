package org.example.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class CargoVan extends Vehicle {

    private static final double CARGO_VAN_RENTAL_PRICE = 50.00;
    private static final double CARGO_VAN_RENTAL_PRICE_DISCOUNTED = 40.00;
    private static final double CARGO_VAN_INSURANCE = 0.0003;
    private static final double CARGO_VAN_INSURANCE_DISCOUNT = 0.15;

    private int experience;

    public CargoVan(String brand, String model, BigDecimal value, int experience) {
        super(brand, model, value, BigDecimal.valueOf(CARGO_VAN_RENTAL_PRICE),
                value.multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE)).setScale(1, RoundingMode.HALF_UP), false);
        this.experience = experience;
    }

    public BigDecimal getInitInsurance(){
        return getValue().multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE)).setScale(1, RoundingMode.HALF_UP);
    }

    public void setDiscountRentalCost() {
        setRentalCost(BigDecimal.valueOf(CARGO_VAN_RENTAL_PRICE_DISCOUNTED));
    }

    public void setModifiedInsurance() {
        setModified(true);
        setInsurance(getInsurance().subtract(getInsuranceModifier()));
    }

    public BigDecimal getInsuranceModifier(){
        return getInsurance().multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE_DISCOUNT)).setScale(1, RoundingMode.HALF_UP);
    }
}
