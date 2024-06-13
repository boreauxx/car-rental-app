package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final double CAR_RENTAL_COST = 20.00;
    private static final double MOTORCYCLE_RENTAL_PRICE = 15.00;
    private static final double CARGO_VAN_RENTAL_PRICE = 50.00;

    private static final double CAR_RENTAL_PRICE_DISCOUNT = 15.00;
    private static final double MOTORCYCLE_RENTAL_PRICE_DISCOUNT = 10.00;
    private static final double CARGO_VAN_RENTAL_PRICE_DISCOUNT = 40.00;

    private static final double CAR_INSURANCE = 0.01;
    private static final double MOTORCYCLE_INSURANCE = 0.02;
    private static final double CARGO_VAN_INSURANCE = 0.03;

    private static final double CAR_INSURANCE_DISCOUNT = 0.10;
    private static final double MOTORCYCLE_INSURANCE_SURCHARGE = 0.20;
    private static final double CARGO_VAN_INSURANCE_DISCOUNT = 0.15;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! Please enter your name:");
        String customerName = scanner.nextLine();

        System.out.println("Please choose one of the following options: car, motorcycle, cargo van");
        String vehicleType = scanner.nextLine();
        List<String> allowedVehicleTypes = List.of("car", "motorcycle", "cargo van");
        if (!allowedVehicleTypes.contains(vehicleType)) {
            System.out.println("please choose a valid vehicle type!");
        }

        System.out.println("Please submit the brand:");
        String brand = scanner.nextLine();

        System.out.println("Please submit the model:");
        String model = scanner.nextLine();

        System.out.println("Please submit the value of the vehicle:");
        double value = Double.parseDouble(scanner.nextLine());;

        double insurance = 0.00;
        Vehicle vehicle = new Vehicle();
        Driver driver = new Driver();

        switch (vehicleType) {
            case "car":
                System.out.println("Please submit the car's safety range");
                int safetyRange = Integer.parseInt(scanner.nextLine());
                insurance = value * CAR_INSURANCE;
                vehicle = new Vehicle(brand, model, value, vehicleType, CAR_RENTAL_COST, insurance);
                driver = new Driver(customerName, safetyRange, vehicle);
                break;
            case "motorcycle":
                System.out.println("Please submit your age:");
                int age = Integer.parseInt(scanner.nextLine());
                insurance = value * MOTORCYCLE_INSURANCE;
                vehicle = new Vehicle(brand, model, value, vehicleType, MOTORCYCLE_RENTAL_PRICE, insurance);
                driver = new Driver(customerName, age, vehicle);
                break;
            case "cargo van":
                System.out.println("Please submit your driver's experience");
                int experience = Integer.parseInt(scanner.nextLine());
                insurance = value * CARGO_VAN_INSURANCE;
                vehicle = new Vehicle(brand, model, value, vehicleType, CARGO_VAN_RENTAL_PRICE, insurance);
                driver = new Driver(customerName, experience, vehicle);
                break;
        }

        System.out.println("Please enter the rent starting day in the format dd/mm/yyyy:");
        String startDate = scanner.nextLine();
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Please enter the rent ending day in the format dd/mm/yyyy:");
        String endDate = scanner.nextLine();
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.println("Please enter the day you returned the vehicle in the format dd/mm/yyyy:");
        String returnDate = scanner.nextLine();
        LocalDate actual = LocalDate.parse(returnDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long daysRented = ChronoUnit.DAYS.between(start, end);

        if (daysRented > 7L) {
            switch (vehicleType) {
                case "car":
                    driver.getVehicle().setRentalCost(CAR_RENTAL_PRICE_DISCOUNT);
                    break;
                case "motorcycle":
                    driver.getVehicle().setRentalCost(MOTORCYCLE_RENTAL_PRICE_DISCOUNT);
                    break;
                case "cargo van":
                    driver.getVehicle().setRentalCost(CARGO_VAN_RENTAL_PRICE_DISCOUNT);
                    break;
            }
        }

        double driversInsurance = driver.getVehicle().getInsurance();

        switch (vehicleType) {
            case "car":
                if(driver.getDiscountFactor() > 3){
                    insurance = driversInsurance - driversInsurance * CAR_INSURANCE_DISCOUNT;
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
            case "motorcycle":
                if(driver.getDiscountFactor() < 25){
                    insurance = driversInsurance + driversInsurance * MOTORCYCLE_INSURANCE_SURCHARGE;
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
            case "cargo van":
                if(driver.getDiscountFactor() > 5){
                    insurance = driversInsurance - driversInsurance * CARGO_VAN_INSURANCE_DISCOUNT;
                    driver.getVehicle().setInsurance(insurance);
                }
                break;
        }

        long actualRent = ChronoUnit.DAYS.between(start, actual);

        long discountedRentDays = daysRented - actualRent;
        if(discountedRentDays > 1){
            long fullChargeRentDays = daysRented - discountedRentDays;

        }

    }
}