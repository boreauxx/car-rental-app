package org.example.services;

import org.example.objects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProcessingServiceImpl implements ProcessingService{

    @Override
    public Vehicle createVehicle(String type, String brand, String model, BigDecimal value, int factor) {
        return switch (type) {
            case "car" -> new Car(brand, model, value, factor);
            case "motorcycle" -> new Motorcycle(brand, model, value, factor);
            case "cargo van" -> new CargoVan(brand, model, value, factor);
            default -> throw new IllegalArgumentException("Invalid vehicle type");
        };
    }

    @Override
    public RentAndInsurance calculateRentAndInsurance(long discountedDays, long daysRentedFor, BigDecimal rentalCost, BigDecimal insurance) {
        if (discountedDays >= 1) {
            long fullyChargedDays = daysRentedFor - discountedDays;
            BigDecimal fullyChargedRent = rentalCost.multiply(BigDecimal.valueOf(fullyChargedDays)) ;
            BigDecimal fullyChargedInsurance = insurance.multiply(BigDecimal.valueOf(fullyChargedDays));

            BigDecimal discountedRent = rentalCost.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(discountedDays));
            BigDecimal discountedInsurance = insurance.multiply(BigDecimal.ZERO);

            BigDecimal totalRentPaid = fullyChargedRent.add(discountedRent);
            BigDecimal totalInsurancePaid = fullyChargedInsurance.add(discountedInsurance);
            BigDecimal total = totalInsurancePaid.add(totalRentPaid);
            return new RentAndInsurance(fullyChargedRent, fullyChargedInsurance, discountedRent, discountedInsurance, totalRentPaid, totalInsurancePaid, total);
        }
        else {
            BigDecimal totalRentPaid = rentalCost.multiply(BigDecimal.valueOf(daysRentedFor));
            BigDecimal totalInsurancePaid = insurance.multiply(BigDecimal.valueOf(daysRentedFor));
            BigDecimal total = totalRentPaid.add(totalInsurancePaid);
            return new RentAndInsurance(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, totalRentPaid, totalInsurancePaid, total);
        }
    }

    @Override
    public void chooseMessageViaVehicleType(String vehicleType, String invalidSafetyRatingMessage, String invalidAgeMessage, String invalidExperienceMessage) {
        switch (vehicleType) {
            case "car":
                System.out.println(invalidSafetyRatingMessage);
                break;
            case "motorcycle":
                System.out.println(invalidAgeMessage);
                break;
            case "cargo van":
                System.out.println(invalidExperienceMessage);
                break;
        }
    }

    @Override
    public void checkForRentalDiscount(long daysRentedFor, Vehicle vehicle) {
        if (daysRentedFor > 7L) {
            vehicle.setDiscountRentalCost();
        }
    }

    @Override
    public void checkForFactorDiscountOrSurcharge(String vehicleType,int discountOrSurchargeElement, Vehicle vehicle) {
        switch (vehicleType) {
            case "car":
                if (discountOrSurchargeElement > 3) {
                    vehicle.setModifiedInsurance();
                }
                break;
            case "motorcycle":
                if (discountOrSurchargeElement < 25) {
                    vehicle.setModifiedInsurance();
                }
                break;
            case "cargo van":
                if (discountOrSurchargeElement > 5) {
                    vehicle.setModifiedInsurance();
                }
                break;
        }
    }


}
