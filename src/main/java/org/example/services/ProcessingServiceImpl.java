package org.example.services;

import org.example.objects.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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
    public boolean validateEndAndReturnDates(LocalDate startDate, LocalDate endDate, String endDateInput) {
        // EXP: End and return dates cannot be before start date
        if(!endDate.isBefore(startDate)){
            String[] parts = endDateInput.split("/");
            if (parts.length != 3) return false;
            try {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                // EXP: A days must be between 1 or 31 days included, a month between 1 to 12 included, and the year has to be 2000 to 2099 included
                if (day < 1 || day > 31) return false;
                if (month < 1 || month > 12) return false;
                return year >= 2000 && year <= 2099;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
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
