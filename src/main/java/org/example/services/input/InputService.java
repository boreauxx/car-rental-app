package org.example.services.input;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface InputService {
    BigDecimal valueCLI();
    int discountOrSurcharge(String vehicleType);
    String dateInputCLI();
    LocalDate startDateCLI(String startDateInput);
    LocalDate endDateCLI(String endDateInput);
    LocalDate returnDateCLI(String returnDateInput);

}
