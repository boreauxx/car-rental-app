package org.example.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class Car extends Vehicle {

    private static final double CAR_RENTAL_PRICE = 20.00;
    private static final double CAR_RENTAL_PRICE_DISCOUNTED = 15.00;
    private static final double CAR_INSURANCE = 0.0001;
    private static final double CAR_INSURANCE_DISCOUNT = 0.10;

    private int safetyRating;

    public Car(String brand, String model, BigDecimal value, int safetyRating) {
        super(brand, model, value, BigDecimal.valueOf(CAR_RENTAL_PRICE),
                value.multiply(BigDecimal.valueOf(CAR_INSURANCE)), false);
        this.safetyRating = safetyRating;
    }

    public BigDecimal getInitInsurance() {
        return getValue().multiply(BigDecimal.valueOf(CAR_INSURANCE)).setScale(1, RoundingMode.HALF_UP);
    }

    public void setDiscountRentalCost() {
        setRentalCost(BigDecimal.valueOf(CAR_RENTAL_PRICE_DISCOUNTED));
    }

    public void setModifiedInsurance() {
        setModified(true);
        setInsurance(getInsurance().subtract(getInsuranceModifier()));
    }

    public BigDecimal getInsuranceModifier() {
        return getInsurance().multiply(BigDecimal.valueOf(CAR_INSURANCE_DISCOUNT)).setScale(1, RoundingMode.HALF_UP);
    }

}
