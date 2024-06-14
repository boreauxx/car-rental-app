package org.example;

import org.example.objects.*;

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
    private static final String STARTING_RENT_DAY_MESSAGE = "Please enter the rent starting day in the format dd/mm/yyyy:";
    private static final String ENDING_RENT_DAY_MESSAGE = "Please enter the rent ending day in the format dd/mm/yyyy:";
    private static final String ACTUAL_RETURN_DAY_MESSAGE = "Please enter the day you returned the vehicle in the format dd/mm/yyyy:";
    private static final String INVALID_TYPE = "Please choose a valid vehicle type!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(CUSTOMER_NAME_MESSAGE);
        String customerName = scanner.nextLine();

        System.out.println(CHOOSE_VEHICLE_TYPE_MESSAGE);
        String vehicleType = scanner.nextLine();

        // TODO: FIX
        if (!ALLOWED_VEHICLES.contains(vehicleType)) {
            System.out.println(INVALID_TYPE);
            vehicleType = scanner.nextLine();
        }

        System.out.println(CHOOSE_BRAND_MESSAGE);
        String brand = scanner.nextLine();

        System.out.println(CHOOSE_MODEL_MESSAGE);
        String model = scanner.nextLine();

        System.out.println(CHOOSE_VALUE_MESSAGE);
        double value = Double.parseDouble(scanner.nextLine());

        int discountOrSurchargeElement =
                switch (vehicleType) {
                    case "car" -> {
                        System.out.println(CHOOSE_CAR_SAFETY_MESSAGE);
                        yield Integer.parseInt(scanner.nextLine());
                    }
                    case "motorcycle" -> {
                        System.out.println(CHOOSE_AGE_MESSAGE);
                        yield Integer.parseInt(scanner.nextLine());
                    }
                    case "cargo van" -> {
                        System.out.println(CHOOSE_EXPERIENCE_MESSAGE);
                        yield Integer.parseInt(scanner.nextLine());
                    }
                    default -> 0;
                };

        Vehicle vehicle = createVehicle(vehicleType, brand, model, value, discountOrSurchargeElement);

        System.out.println(STARTING_RENT_DAY_MESSAGE);
        String startDate = scanner.nextLine();
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(ENDING_RENT_DAY_MESSAGE);
        String endDate = scanner.nextLine();
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println(ACTUAL_RETURN_DAY_MESSAGE);
        String returnDate = scanner.nextLine();
        LocalDate actual = LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long daysRentedFor = ChronoUnit.DAYS.between(start, end);

        if (daysRentedFor > 7L) {
            vehicle.setDiscountRentalCost();
        }

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

        long daysUsedFor = ChronoUnit.DAYS.between(start, actual);
        long discountedDays = daysRentedFor - daysUsedFor;

        double rentalCost = vehicle.getRentalCost();
        double insurance = vehicle.getInsurance();

        RentAndInsurance rentAndInsurance = calculateRentAndInsurance(discountedDays, daysRentedFor, rentalCost, insurance);

        Invoice invoice = new Invoice(customerName, vehicle, startDate, endDate, returnDate, daysRentedFor, daysUsedFor, rentAndInsurance);

        System.out.println(invoice.formatInvoice(discountedDays));
    }

    private static Vehicle createVehicle(String type, String brand, String model, double value, int factor) {
        return switch (type) {
            case "car" -> new Car(brand, model, value, factor);
            case "motorcycle" -> new Motorcycle(brand, model, value, factor);
            case "cargo van" -> new CargoVan(brand, model, value, factor);
            default -> throw new IllegalArgumentException("Invalid vehicle type");
        };
    }

    private static RentAndInsurance calculateRentAndInsurance(long discountedDays, long daysRentedFor, double rentalCost, double insurance){
        if (discountedDays >= 1) {
            long fullyChargedDays = daysRentedFor - discountedDays;
            double fullyChargedRent = rentalCost * fullyChargedDays;
            double fullyChargedInsurance = insurance * fullyChargedDays;

            double discountedRent = (rentalCost / 2) * discountedDays;
            double discountedInsurance = 0;

            double totalRentPaid = fullyChargedRent + discountedRent;
            double totalInsurancePaid = fullyChargedInsurance + discountedInsurance;
            double total = totalInsurancePaid + totalRentPaid;
            return new RentAndInsurance(fullyChargedRent, fullyChargedInsurance, discountedRent, discountedInsurance, totalRentPaid, totalInsurancePaid, total);
        }
        else {
            double totalRentPaid = rentalCost * daysRentedFor;
            double totalInsurancePaid = insurance * daysRentedFor;
            double total = totalRentPaid + totalInsurancePaid;
            return new RentAndInsurance(0.00, 0.00, 0.00, 0.00, totalRentPaid, totalInsurancePaid, total);
        }
    }

}