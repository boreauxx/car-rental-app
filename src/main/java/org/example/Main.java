package org.example;

import org.example.objects.Invoice;
import org.example.objects.RentAndInsurance;
import org.example.objects.Vehicle;
import org.example.services.ProcessingService;
import org.example.services.ProcessingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import static org.example.constants.Errors.*;
import static org.example.constants.Messages.*;

public class Main {

    private static final List<String> ALLOWED_VEHICLES = List.of("car", "motorcycle", "cargo van");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProcessingService processingService = new ProcessingServiceImpl();

        String customerName;
        String vehicleType;
        String brand;
        String model;
        BigDecimal value = BigDecimal.ZERO;
        int discountOrSurchargeElement = 0;
        String startDateInput = "";
        LocalDate startDate = LocalDate.now();
        String endDateInput = "";
        LocalDate endDate = LocalDate.now();
        String returnDateInput = "";
        LocalDate returnDate = LocalDate.now();
        boolean running = true;
        boolean inputState = false;

        while (running) {
            System.out.println(CUSTOMER_NAME_MESSAGE);
            customerName = scanner.nextLine();

            System.out.println(CHOOSE_VEHICLE_TYPE_MESSAGE);
            vehicleType = scanner.nextLine();

            while (!ALLOWED_VEHICLES.contains(vehicleType)) {
                System.out.println(INVALID_TYPE+CHOOSE_VEHICLE_TYPE_MESSAGE);
                vehicleType = scanner.nextLine();
            }

            System.out.println(CHOOSE_BRAND_MESSAGE);
            brand = scanner.nextLine();

            System.out.println(CHOOSE_MODEL_MESSAGE);
            model = scanner.nextLine();

            System.out.println(CHOOSE_VALUE_MESSAGE);

            while (!inputState) {
                try {
                    String valueInput = scanner.nextLine().replace(",", "");
                    value = BigDecimal.valueOf(Long.parseLong(valueInput));
                    inputState = true;
                } catch (Exception exception) {
                    System.out.println(INVALID_VALUE_MESSAGE);
                }
            }

            while (inputState) {
                try {
                    processingService.chooseMessageViaVehicleType(vehicleType, CHOOSE_CAR_SAFETY_MESSAGE, CHOOSE_AGE_MESSAGE, CHOOSE_EXPERIENCE_MESSAGE);
                    discountOrSurchargeElement = Integer.parseInt(scanner.nextLine());
                    inputState = false;
                } catch (Exception exception) {
                    processingService.chooseMessageViaVehicleType(vehicleType, INVALID_SAFETY_RATING_MESSAGE, INVALID_AGE_MESSAGE, INVALID_EXPERIENCE_MESSAGE);
                }
            }

            Vehicle vehicle = processingService.createVehicle(vehicleType, brand, model, value, discountOrSurchargeElement);

            processingService.setDatesViaInput(false, startDateInput, startDate, endDateInput, endDate, returnDateInput, returnDate);

            long daysRentedFor = ChronoUnit.DAYS.between(startDate, endDate);

            processingService.checkForRentalDiscount(daysRentedFor, vehicle);

            processingService.checkForFactorDiscountOrSurcharge(vehicleType, discountOrSurchargeElement, vehicle);

            long daysUsedFor = ChronoUnit.DAYS.between(startDate, returnDate);
            long discountedDays = daysRentedFor - daysUsedFor;
            BigDecimal rentalCost = vehicle.getRentalCost();
            BigDecimal insurance = vehicle.getInsurance();

            RentAndInsurance rentAndInsurance = processingService.calculateRentAndInsurance(discountedDays, daysRentedFor, rentalCost, insurance);

            Invoice invoice = new Invoice(customerName, vehicle, startDateInput, endDateInput, returnDateInput, daysRentedFor, daysUsedFor, rentAndInsurance);

            System.out.println(invoice.formatInvoice(!vehicleType.equals("motorcycle")));

            running = false;
            scanner.close();
        }

    }

}