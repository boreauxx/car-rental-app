package org.example;

import org.example.objects.Car;
import org.example.objects.CargoVan;
import org.example.objects.Motorcycle;
import org.example.objects.Vehicle;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<String> ALLOWED_VEHICLES = List.of("CAR", "MOTORCYCLE", "CARGO VAN");

    private static final String CUSOMTER_NAME_MESSAGE = "Hello! Please enter your name:";
    private static final String CHOOSE_VEHILE_TYPE_MESSAGE = "Please choose one of the following options: car, motorcycle, cargo van";
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

    private static final String INVOICE_LAYOUT = "XXXXXXXXXX";
    private static final String INVOICE_START_MESSAGE = "Reservation start date:";
    private static final String INVOICE_END_MESSAGE = "Reservation end date:";
    private static final String INVOICE_RENTAL_DAYS_MESSAGE = "Reserved rental days:";
    private static final String INVOICE_ACTUAL_RETURN_MESSAGE = "Actual return date:";
    private static final String INVOICE_ACTUAL_RENTAL_DAYS_MESSAGE = "Actual rental days:";

    private static final String INVOICE_RENTAL_COST_PER_DAY_MESSAGE = "Rental cost per day:$";
    private static final String INVOICE_INSURANCE_PER_DAY_MESSAGE = "Insurance per day:$";
    private static final String INVOICE_SAVED_RENT_MESSAGE = "Early return discount for rent:$";
    private static final String INVOICE_SAVED_INSURACE_MESSAGE = "Early return discount for insurance:$";

    private static final String INVOICE_INITIAL_INSURANCE_PER_DAY_MESSAGE = "Initial insurance per day:$";
    private static final String INVOICE_SURCHARGE_MESSAGE = "Insurance addition pay per day:$";
    private static final String INVOICE_DISCOUNT_MESSAGE = "Insurance discount per day:$";

    private static final String INVOICE_TOTAL_RENT_MESSAGE = "Total rent:$";
    private static final String INVOICE_TOTAL_INSURANCE_MESSAGE = "Total insurance:$";
    private static final String INVOICE_TOTAL_MESSAGE = "Total:$";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(CUSOMTER_NAME_MESSAGE);
        String customerName = scanner.nextLine();

        System.out.println(CHOOSE_VEHILE_TYPE_MESSAGE);
        String vehicleType = scanner.nextLine();
        List<String> allowedVehicleTypes = ALLOWED_VEHICLES;
        if (!allowedVehicleTypes.contains(vehicleType)) {
            System.out.println(INVALID_TYPE);
        }

        System.out.println(CHOOSE_BRAND_MESSAGE);
        String brand = scanner.nextLine();

        System.out.println(CHOOSE_MODEL_MESSAGE);
        String model = scanner.nextLine();

        System.out.println(CHOOSE_VALUE_MESSAGE);
        double value = Double.parseDouble(scanner.nextLine());

        int discountOrSurchargeElement = switch (vehicleType) {
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
            case "CAR":
                if(discountOrSurchargeElement > 3){
                    vehicle.setDiscountInsurance();
                }
                break;
            case "MOTORCYCLE":
                if(discountOrSurchargeElement < 25){
                    vehicle.setDiscountInsurance();
                }
                break;
            case "CARGO VAN":
                if(discountOrSurchargeElement > 8){
                    vehicle.setDiscountInsurance();
                }
                break;
        }

        long daysUsedFor = ChronoUnit.DAYS.between(start, actual);
        long discountedDays = daysRentedFor - daysUsedFor;

        StringBuilder sb = new StringBuilder();

        if (discountedDays > 1) {
            long fullyChargedDays = daysRentedFor - discountedDays;
            double rentalCost = vehicle.getRentalCost();
            double insurance = vehicle.getInsurance();

            double fullyChargedRent = rentalCost * fullyChargedDays;
            double fullyChargedInsurance = insurance * fullyChargedDays;

            double discountedRent = (rentalCost / 2) * discountedDays;
            double discountedInsurance = insurance * 0;

            double totalRentPaid = fullyChargedRent + discountedRent;
            double totalInsurancePaid = fullyChargedInsurance + discountedInsurance;
            double total = totalInsurancePaid + totalRentPaid;

            sb.append(INVOICE_LAYOUT)
                    .append(System.lineSeparator());
            sb.append("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .append(System.lineSeparator());
            sb.append("Customer name: " + customerName)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(String.format("Rented vehicle: %s %s", vehicle.getBrand(), vehicle.getModel()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_START_MESSAGE + startDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_END_MESSAGE + endDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_RENTAL_DAYS_MESSAGE + daysRentedFor)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_ACTUAL_RETURN_MESSAGE + returnDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_ACTUAL_RENTAL_DAYS_MESSAGE + daysUsedFor)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_RENTAL_COST_PER_DAY_MESSAGE + rentalCost)
                    .append(System.lineSeparator());
            sb.append(INVOICE_INITIAL_INSURANCE_PER_DAY_MESSAGE + vehicle.getInitInsurance())
                    .append(System.lineSeparator());
            sb.append(INVOICE_DISCOUNT_MESSAGE + vehicle.getInsuranceModifier())
                    .append(System.lineSeparator());
            sb.append(INVOICE_INSURANCE_PER_DAY_MESSAGE + insurance)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_SAVED_RENT_MESSAGE + discountedRent)
                    .append(System.lineSeparator());
            sb.append(INVOICE_SAVED_INSURACE_MESSAGE + fullyChargedInsurance / 2)
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_RENT_MESSAGE + totalRentPaid)
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_INSURANCE_MESSAGE + totalInsurancePaid)
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_MESSAGE + total)
                    .append(System.lineSeparator());
        }
        else {
            long fullyChargedDays = daysRentedFor - discountedDays;
            double rentalCost = vehicle.getRentalCost();
            double insurance = vehicle.getInsurance();

            double fullyChargedRent = rentalCost * fullyChargedDays;
            double fullyChargedInsurance = insurance * fullyChargedDays;

            double total = fullyChargedRent + fullyChargedInsurance;

            sb.append(INVOICE_LAYOUT)
                    .append(System.lineSeparator());
            sb.append("Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .append(System.lineSeparator());
            sb.append("Customer name: " + customerName)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(String.format("Rented vehicle: %s %s", vehicle.getBrand(), vehicle.getModel()))
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_START_MESSAGE + startDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_END_MESSAGE + endDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_RENTAL_DAYS_MESSAGE + daysRentedFor)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_ACTUAL_RETURN_MESSAGE + returnDate)
                    .append(System.lineSeparator());
            sb.append(INVOICE_ACTUAL_RENTAL_DAYS_MESSAGE + daysUsedFor)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_RENTAL_COST_PER_DAY_MESSAGE + rentalCost)
                    .append(System.lineSeparator());
            sb.append(INVOICE_INSURANCE_PER_DAY_MESSAGE + insurance)
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_RENT_MESSAGE + fullyChargedRent)
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_INSURANCE_MESSAGE + fullyChargedInsurance)
                    .append(System.lineSeparator());
            sb.append(INVOICE_TOTAL_MESSAGE + total)
                    .append(System.lineSeparator());
        }
        System.out.println(sb.toString());
    }

    private static Vehicle createVehicle(String type, String brand, String model, double value, int factor) {
        switch (type) {
            case "CAR":
                return new Car(brand, model, value, factor);
            case "MOTORCYCLE":
                return new Motorcycle(brand, model, value, factor);
            case "CARGO VAN":
                return new CargoVan(brand, model, value, factor);
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }

    private static void printInvoice(){

    }

}