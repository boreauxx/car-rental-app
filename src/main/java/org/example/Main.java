package org.example;

import org.example.objects.*;
import org.example.services.ProcessingService;
import org.example.services.ProcessingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<String> ALLOWED_VEHICLES = List.of("car", "motorcycle", "cargo van");
    private static final String CUSTOMER_NAME_MESSAGE = "Hello! Please enter your name:";
    private static final String CHOOSE_VEHICLE_TYPE_MESSAGE = "Please choose one of the following options: car, motorcycle, cargo van";
    private static final String CHOOSE_BRAND_MESSAGE = "Please submit the brand:";
    private static final String CHOOSE_MODEL_MESSAGE = "Please submit the model:";
    private static final String CHOOSE_VALUE_MESSAGE = "Please submit the value of the vehicle:";

    private static final String CHOOSE_CAR_SAFETY_MESSAGE = "Please submit the car's safety range";
    private static final String CHOOSE_AGE_MESSAGE = "Please submit your age:";
    private static final String CHOOSE_EXPERIENCE_MESSAGE = "Please submit your driver's experience";

    private static final String STARTING_RENT_DAY_MESSAGE = "Please enter the rent starting day in the format - dd/mm/yyyy:";
    private static final String ENDING_RENT_DAY_MESSAGE = "Please enter the rent ending day in the format - dd/mm/yyyy:";
    private static final String ACTUAL_RETURN_DAY_MESSAGE = "Please enter the day you returned the vehicle in the format - dd/mm/yyyy:";

    private static final String INVALID_TYPE = "Please choose a valid vehicle type!";
    private static final String INVALID_VALUE_MESSAGE = "Please enter the sum in the following format - 5,000/20,000/35,000";
    private static final String INVALID_SAFETY_RATING_MESSAGE = "Please submit a valid rating, 1 to 5 included!";
    private static final String INVALID_AGE_MESSAGE = "Please submit a valid age! 18+";
    private static final String INVALID_EXPERIENCE_MESSAGE = "Please submit valid experience in years!";
    private static final String INVALID_DATE_FORMAT_MESSAGE = "Please submit a valid date in the format - dd/mm/yyyy";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProcessingService processingService = new ProcessingServiceImpl();

        System.out.println(CUSTOMER_NAME_MESSAGE);
        String customerName = scanner.nextLine();

        System.out.println(CHOOSE_VEHICLE_TYPE_MESSAGE);
        String vehicleType = scanner.nextLine();

        while (!ALLOWED_VEHICLES.contains(vehicleType)) {
            System.out.println(INVALID_TYPE);
            vehicleType = scanner.nextLine();
        }

        System.out.println(CHOOSE_BRAND_MESSAGE);
        String brand = scanner.nextLine();

        System.out.println(CHOOSE_MODEL_MESSAGE);
        String model = scanner.nextLine();

        System.out.println(CHOOSE_VALUE_MESSAGE);
        boolean validInput = false;
        BigDecimal value = BigDecimal.ZERO;

        while (!validInput) {
            try {
                String valueInput = scanner.nextLine().replace(",", "");
                value = BigDecimal.valueOf(Long.parseLong(valueInput));
                validInput = true;
            } catch (Exception exception) {
                System.out.println(INVALID_VALUE_MESSAGE);
            }
        }

        int discountOrSurchargeElement = 0;

        while (validInput) {
            try {
                processingService.chooseMessageViaVehicleType(vehicleType, CHOOSE_CAR_SAFETY_MESSAGE, CHOOSE_AGE_MESSAGE, CHOOSE_EXPERIENCE_MESSAGE);
                discountOrSurchargeElement = Integer.parseInt(scanner.nextLine());
                validInput = false;
            } catch (Exception exception) {
                processingService.chooseMessageViaVehicleType(vehicleType, INVALID_SAFETY_RATING_MESSAGE, INVALID_AGE_MESSAGE, INVALID_EXPERIENCE_MESSAGE);
            }
        }

        Vehicle vehicle = processingService.createVehicle(vehicleType, brand, model, value, discountOrSurchargeElement);

        System.out.println(STARTING_RENT_DAY_MESSAGE);
        String startDateInput = "";
        LocalDate startDate = LocalDate.now();
        while(!validInput){
            try{
                startDateInput = scanner.nextLine();
                startDate = LocalDate.parse(startDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                validInput = true;
            }
            catch (Exception exception){
                System.out.println(INVALID_DATE_FORMAT_MESSAGE);
            }
        }
        System.out.println(ENDING_RENT_DAY_MESSAGE);
        String endDateInput = "";
        LocalDate endDate = LocalDate.now();

        while(validInput){
            try {
                endDateInput = scanner.nextLine();
                endDate = LocalDate.parse(endDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                boolean endDateIsValid = processingService.validateEndAndReturnDates(startDate, endDate, endDateInput);
                if(!endDateIsValid){
                    throw new Exception();
                }
                validInput = false;
            }
            catch (Exception exception){
                System.out.println(INVALID_DATE_FORMAT_MESSAGE);
            }
        }

        System.out.println(ACTUAL_RETURN_DAY_MESSAGE);
        String returnDateInput = "";
        LocalDate returnDate = LocalDate.now();

        while(!validInput){
            try {
                returnDateInput = scanner.nextLine();
                returnDate = LocalDate.parse(returnDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                boolean returnDateIsValid = processingService.validateEndAndReturnDates(startDate, returnDate, returnDateInput);
                if(!returnDateIsValid){
                    throw new Exception();
                }
                validInput = true;
            }
            catch (Exception exception){
                System.out.println(INVALID_DATE_FORMAT_MESSAGE);
            }
        }

        long daysRentedFor = ChronoUnit.DAYS.between(startDate, endDate);

        processingService.checkForRentalDiscount(daysRentedFor, vehicle);

        processingService.checkForFactorDiscountOrSurcharge(vehicleType,discountOrSurchargeElement, vehicle);

        long daysUsedFor = ChronoUnit.DAYS.between(startDate, returnDate);
        long discountedDays = daysRentedFor - daysUsedFor;
        BigDecimal rentalCost = vehicle.getRentalCost();
        BigDecimal insurance = vehicle.getInsurance();

        RentAndInsurance rentAndInsurance = processingService.calculateRentAndInsurance(discountedDays, daysRentedFor, rentalCost, insurance);

        Invoice invoice = new Invoice(customerName, vehicle, startDateInput, endDateInput, returnDateInput, daysRentedFor, daysUsedFor, rentAndInsurance);

        System.out.println(invoice.formatInvoice(!vehicleType.equals("motorcycle")));
    }




}