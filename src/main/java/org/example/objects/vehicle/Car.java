package org.example.objects.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static org.example.constants.VehicleInformation.*;

@Getter
@Setter
public class Car extends Vehicle {



    private int safetyRating;

    public Car(String brand, String model, BigDecimal value, int safetyRating) {
        super(brand, model, value, BigDecimal.valueOf(CAR_RENTAL_PRICE),
                value.multiply(BigDecimal.valueOf(CAR_INSURANCE)), false);
        this.safetyRating = safetyRating;
    }

    public BigDecimal getInitInsurance() {
        return getValue().multiply(BigDecimal.valueOf(CAR_INSURANCE));
    }

    public void setDiscountRentalCost() {
        setRentalCost(BigDecimal.valueOf(CAR_RENTAL_PRICE_DISCOUNTED));
    }

    public void setModifiedInsurance() {
        setModified(true);
        setInsurance(getInsurance().subtract(getInsuranceModifier()));
    }

    public BigDecimal getInsuranceModifier() {
        return getInitInsurance().multiply(BigDecimal.valueOf(CAR_INSURANCE_DISCOUNT));
    }

}
