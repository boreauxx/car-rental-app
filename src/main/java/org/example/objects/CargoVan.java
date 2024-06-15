package org.example.objects;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static org.example.constants.VehicleInformation.*;

@Getter
@Setter
public class CargoVan extends Vehicle {



    private int experience;

    public CargoVan(String brand, String model, BigDecimal value, int experience) {
        super(brand, model, value, BigDecimal.valueOf(CARGO_VAN_RENTAL_PRICE),
                value.multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE)),
                value.multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE)), false);
        this.experience = experience;
    }

    public BigDecimal getInitInsurance(){
        return getValue().multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE));
    }

    public void setDiscountRentalCost() {
        setRentalCost(BigDecimal.valueOf(CARGO_VAN_RENTAL_PRICE_DISCOUNTED));
    }

    public void setModifiedInsurance() {
        setModified(true);
        setInsurance(getInsurance().subtract(getInsuranceModifier()));
    }

    public BigDecimal getInsuranceModifier(){
        return getInitialInsurance().multiply(BigDecimal.valueOf(CARGO_VAN_INSURANCE_DISCOUNT));
    }
}
