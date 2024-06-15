package org.example.services.process;

import org.example.objects.output.RentAndInsurance;
import org.example.objects.vehicle.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProcessingService {

    Vehicle createVehicle(String type, String brand, String model, BigDecimal value, int factor);

    RentAndInsurance calculateRentAndInsurance(long discountedDays, long daysRentedFor, BigDecimal rentalCost, BigDecimal insurance);

    void chooseMessageViaVehicleType(String vehicleType, String invalidSafetyRatingMessage, String invalidAgeMessage, String invalidExperienceMessage);

    void checkForRentalDiscount(long daysRentedFor, Vehicle vehicle);

    void checkForFactorDiscountOrSurcharge(String vehicleType,int discountOrSurchargeElement, Vehicle vehicle);


}
