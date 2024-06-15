package org.example.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static org.example.constants.VehicleInformation.*;

@Getter
@Setter
public class Motorcycle extends Vehicle {



    private int ageLimit;

    public Motorcycle(String brand, String model, BigDecimal value, int ageLimit) {
        super(brand, model, value, BigDecimal.valueOf(MOTORCYCLE_RENTAL_PRICE),
                value.multiply(BigDecimal.valueOf(MOTORCYCLE_INSURANCE)),
                value.multiply(BigDecimal.valueOf(MOTORCYCLE_INSURANCE)), false);
        this.ageLimit = ageLimit;
    }

    public BigDecimal getInitInsurance(){
        return getValue().multiply(BigDecimal.valueOf(MOTORCYCLE_INSURANCE));
    }

    public void setDiscountRentalCost() {
        setRentalCost(BigDecimal.valueOf(MOTORCYCLE_RENTAL_PRICE_DISCOUNTED));
    }

    public void setModifiedInsurance() {
        setModified(true);
        setInsurance(getInsurance().add(getInsuranceModifier()));
    }

    public BigDecimal getInsuranceModifier(){
        return getInitialInsurance().multiply(BigDecimal.valueOf(MOTORCYCLE_INSURANCE_SURCHARGE));
    }
}
