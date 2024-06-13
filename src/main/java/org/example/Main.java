package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final double CAR_RENTAL_COST = 20.00;
    private static final double MOTORCYCLE_RENTAL_PRICE = 15.00;
    private static final double CARGO_VAN_RENTAL_PRICE = 50.00;

    private static final double CAR_RENTAL_PRICE_DISCOUNTED = 15.00;
    private static final double MOTORCYCLE_RENTAL_PRICE_DISCOUNTED = 10.00;
    private static final double CARGO_VAN_RENTAL_PRICE_DISCOUNTED = 40.00;

    private static final double CAR_INSURANCE = 0.1;
    private static final double MOTORCYCLE_INSURANCE = 0.2;
    private static final double CARGO_VAN_INSURANCE = 0.3;

    private static final double CAR_INSURANCE_DISCOUNT = 0.10;
    private static final double MOTORCYCLE_INSURANCE_SURCHARGE = 0.20;
    private static final double CARGO_VAN_INSURANCE_DISCOUNT = 0.15;

    private static final String FIRST_MESSAGE = "Hello! Please enter your name:";
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(FIRST_MESSAGE);
        String customerName = scanner.nextLine();

        System.out.println(CHOOSE_VEHILE_TYPE_MESSAGE);
        String vehicleType = scanner.nextLine();
        List<String> allowedVehicleTypes = List.of("car", "motorcycle", "cargo van");
        if (!allowedVehicleTypes.contains(vehicleType)) {
            System.out.println(INVALID_TYPE);
        }

        System.out.println(CHOOSE_BRAND_MESSAGE);
        String brand = scanner.nextLine();

        System.out.println(CHOOSE_MODEL_MESSAGE);
        String model = scanner.nextLine();

        System.out.println(CHOOSE_VALUE_MESSAGE);
        double value = Double.parseDouble(scanner.nextLine());

        double initialInsurance = 0.00;
        Vehicle vehicle = new Vehicle();
        Driver driver = new Driver();

        switch (vehicleType) {
            case "car":
                System.out.println(CHOOSE_CAR_SAFETY_MESSAGE);
                int safetyRange = Integer.parseInt(scanner.nextLine());

                initialInsurance = Math.round(value * CAR_INSURANCE);
                vehicle = new Vehicle(brand, model, value, vehicleType, CAR_RENTAL_COST, initialInsurance);
                driver = new Driver(customerName, safetyRange, vehicle);
                break;
            case "motorcycle":
                System.out.println(CHOOSE_AGE_MESSAGE);
                int age = Integer.parseInt(scanner.nextLine());

                initialInsurance = Math.round(value * MOTORCYCLE_INSURANCE);
                vehicle = new Vehicle(brand, model, value, vehicleType, MOTORCYCLE_RENTAL_PRICE, initialInsurance);
                driver = new Driver(customerName, age, vehicle);
                break;
            case "cargo van":
                System.out.println(CHOOSE_EXPERIENCE_MESSAGE);
                int experience = Integer.parseInt(scanner.nextLine());

                initialInsurance = Math.round(value * CARGO_VAN_INSURANCE);
                vehicle = new Vehicle(brand, model, value, vehicleType, CARGO_VAN_RENTAL_PRICE, initialInsurance);
                driver = new Driver(customerName, experience, vehicle);
                break;
        }

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
            switch (vehicleType) {
                case "car":
                    driver.getVehicle().setRentalCost(CAR_RENTAL_PRICE_DISCOUNTED);
                    break;
                case "motorcycle":
                    driver.getVehicle().setRentalCost(MOTORCYCLE_RENTAL_PRICE_DISCOUNTED);
                    break;
                case "cargo van":
                    driver.getVehicle().setRentalCost(CARGO_VAN_RENTAL_PRICE_DISCOUNTED);
                    break;
            }
        }

        double driversInsurance = driver.getVehicle().getInsurance();
        double insurance = 0.00;
        BigDecimal insuranceModifier = new BigDecimal("0");

        switch (vehicleType) {
            case "car":
                if(driver.getDiscountFactor() > 3){
                    insuranceModifier = BigDecimal.valueOf(driversInsurance * CAR_INSURANCE_DISCOUNT);
                    BigDecimal roundedModifier = insuranceModifier.setScale(1, RoundingMode.HALF_UP);
                    insurance = driversInsurance - roundedModifier.doubleValue();
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
            case "motorcycle":
                if(driver.getDiscountFactor() < 25){
                    insuranceModifier = BigDecimal.valueOf(driversInsurance * MOTORCYCLE_INSURANCE_SURCHARGE);
                    BigDecimal roundedModifier = insuranceModifier.setScale(1, RoundingMode.HALF_UP);
                    insurance = driversInsurance + roundedModifier.doubleValue();
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
            case "cargo van":
                if(driver.getDiscountFactor() > 5){
                    insuranceModifier = BigDecimal.valueOf(driversInsurance * CARGO_VAN_INSURANCE_DISCOUNT);
                    BigDecimal roundedModifier = insuranceModifier.setScale(1, RoundingMode.HALF_UP);
                    insurance = driversInsurance - roundedModifier.doubleValue();
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
        }

        long daysUsedFor = ChronoUnit.DAYS.between(start, actual);
        long discountedDays = daysRentedFor - daysUsedFor;

        if(discountedDays > 1){
            long fullyChargedDays = daysRentedFor - discountedDays;
            double rentalCost = driver.getVehicle().getRentalCost();
            insurance = driver.getVehicle().getInsurance();

            double fullyChargedRent = rentalCost * fullyChargedDays;
            double fullyChargedInsurance = insurance * fullyChargedDays;

            double discountedRent = (rentalCost/2) * discountedDays;
            double discountedInsurance = insurance * 0;

            double totalRentPaid = fullyChargedRent + discountedRent;
            double totalInsurancePaid = fullyChargedInsurance + discountedInsurance;
            double total = totalInsurancePaid + totalRentPaid;

            System.out.println("rental cost: "+rentalCost);
            System.out.println("initial insurance: "+initialInsurance);
            System.out.println("insurance discount/surcharge: "+insuranceModifier.setScale(1, RoundingMode.HALF_UP));
            System.out.println("insurance per day: "+insurance);

            System.out.println("early return discount for rent:"+discountedRent);
            System.out.println("early return discount for insurance"+fullyChargedInsurance/2);

            System.out.println("total rent: "+totalRentPaid);
            System.out.println("total insurance: "+totalInsurancePaid);
            System.out.println("total paid: "+total);
        }

    }


}