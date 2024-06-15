package org.example;

import org.example.objects.output.Invoice;
import org.example.objects.output.RentAndInsurance;
import org.example.objects.vehicle.Vehicle;
import org.example.services.input.InputService;
import org.example.services.input.InputServiceImpl;
import org.example.services.process.ProcessingService;
import org.example.services.process.ProcessingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        InputService inputService = new InputServiceImpl();

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
            value = inputService.valueCLI();

            discountOrSurchargeElement = inputService.discountOrSurcharge(vehicleType);

            Vehicle vehicle = processingService.createVehicle(vehicleType, brand, model, value, discountOrSurchargeElement);

            System.out.println(STARTING_RENT_DAY_MESSAGE);
            startDateInput = inputService.dateInputCLI();
            startDate = inputService.startDateCLI(startDateInput);

            System.out.println(ENDING_RENT_DAY_MESSAGE);
            endDateInput = inputService.dateInputCLI();
            endDate = inputService.endDateCLI(endDateInput);

            System.out.println(ACTUAL_RETURN_DAY_MESSAGE);
            returnDateInput = inputService.dateInputCLI();
            returnDate = inputService.returnDateCLI(returnDateInput);

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