package org.example.services.input;

import org.example.services.process.ProcessingService;
import org.example.services.process.ProcessingServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static org.example.constants.Errors.*;
import static org.example.constants.Errors.INVALID_EXPERIENCE_MESSAGE;
import static org.example.constants.Messages.*;

public class InputServiceImpl implements InputService {

    Scanner scanner = new Scanner(System.in);
    ProcessingService processingService = new ProcessingServiceImpl();

    @Override
    public BigDecimal valueCLI() {
        try {
            String valueInput = scanner.nextLine().replace(",", "");
            return BigDecimal.valueOf(Long.parseLong(valueInput));
        } catch (Exception exception) {
            System.out.println(INVALID_VALUE_MESSAGE);
            return valueCLI();
        }
    }

    @Override
    public int discountOrSurcharge(String vehicleType) {
        try {
            processingService.chooseMessageViaVehicleType(vehicleType, CHOOSE_CAR_SAFETY_MESSAGE, CHOOSE_AGE_MESSAGE, CHOOSE_EXPERIENCE_MESSAGE);
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception exception) {
            processingService.chooseMessageViaVehicleType(vehicleType, INVALID_SAFETY_RATING_MESSAGE, INVALID_AGE_MESSAGE, INVALID_EXPERIENCE_MESSAGE);
            return discountOrSurcharge(vehicleType);
        }
    }

    @Override
    public String dateInputCLI() {
        try {
            String dateInput = scanner.nextLine();
            LocalDate date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return dateInput;
        } catch (Exception exception) {
            System.out.println(INVALID_DATE_FORMAT_MESSAGE);
            return dateInputCLI();
        }
    }

    @Override
    public LocalDate startDateCLI(String startDateInput) {
        return LocalDate.parse(startDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public LocalDate endDateCLI(String endDateInput) {
        return LocalDate.parse(endDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


    @Override
    public LocalDate returnDateCLI(String returnDateInput) {
        return LocalDate.parse(returnDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


}
