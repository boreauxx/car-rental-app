package org.example.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class Invoice {

    private static final String LAYOUT = "XXXXXXXXXX";
    private static final String START_DATE_MESSAGE = "Reservation start date:";
    private static final String END_DATE_MESSAGE = "Reservation end date:";
    private static final String RESERVED_RENTAL_DAYS_MESSAGE = "Reserved rental days:";
    private static final String ACTUAL_RETURN_DATE_MESSAGE = "Actual return date:";
    private static final String ACTUAL_RENTAL_DAYS_MESSAGE = "Actual rental days:";

    private static final String RENTAL_COST_PER_DAY_MESSAGE = "Rental cost per day:$";
    private static final String INSURANCE_PER_DAY_MESSAGE = "Insurance per day:$";
    private static final String SAVED_RENT_MESSAGE = "Early return discount for rent:$";
    private static final String SAVED_INSURANCE_MESSAGE = "Early return discount for insurance:$";

    private static final String INITIAL_INSURANCE_PER_DAY_MESSAGE = "Initial insurance per day:$";
    private static final String SURCHARGE_PER_DAY_MESSAGE = "Insurance addition pay per day:$";
    private static final String DISCOUNT_PER_DAY_MESSAGE = "Insurance discount per day:$";

    private static final String TOTAL_RENT_MESSAGE = "Total rent:$";
    private static final String TOTAL_INSURANCE_MESSAGE = "Total insurance:$";
    private static final String TOTAL_MESSAGE = "Total:$";

    private String customerName;
    private Vehicle vehicle;

    private String startDate;
    private String endDate;
    private String returnDate;

    private long daysRentedFor;
    private long daysUsedFor;

    private RentAndInsurance rentAndInsurance;

    public String formatInvoice(boolean isDiscount) {
        StringBuilder sb = new StringBuilder();
        sb
                .append(LAYOUT)
                .append(System.lineSeparator())

                .append("Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .append(System.lineSeparator())

                .append("Customer name: ").append(this.customerName)
                .append(System.lineSeparator())
                .append(System.lineSeparator())

                .append(String.format("Rented vehicle: %s %s", this.vehicle.getBrand(), this.vehicle.getModel()))
                .append(System.lineSeparator())
                .append(System.lineSeparator())

                .append(START_DATE_MESSAGE).append(this.startDate)
                .append(System.lineSeparator())

                .append(END_DATE_MESSAGE).append(this.endDate)
                .append(System.lineSeparator())

                .append(RESERVED_RENTAL_DAYS_MESSAGE).append(this.daysRentedFor)
                .append(System.lineSeparator())
                .append(System.lineSeparator())

                .append(ACTUAL_RETURN_DATE_MESSAGE).append(this.returnDate)
                .append(System.lineSeparator())

                .append(ACTUAL_RENTAL_DAYS_MESSAGE).append(this.daysUsedFor)
                .append(System.lineSeparator())
                .append(System.lineSeparator())

                .append(RENTAL_COST_PER_DAY_MESSAGE).append(this.vehicle.getRentalCost())
                .append(System.lineSeparator());

        if (this.vehicle.isModified()) {
            sb
                    .append(INITIAL_INSURANCE_PER_DAY_MESSAGE).append(this.vehicle.getInitInsurance())
                    .append(System.lineSeparator());
            if (isDiscount) {
                sb
                        .append(DISCOUNT_PER_DAY_MESSAGE).append(this.vehicle.getInsuranceModifier())
                        .append(System.lineSeparator());
            } else {
                sb
                        .append(SURCHARGE_PER_DAY_MESSAGE).append(this.vehicle.getInsuranceModifier())
                        .append(System.lineSeparator());
            }
        }

        sb
                .append(INSURANCE_PER_DAY_MESSAGE).append(this.vehicle.getInsurance())
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        if (this.rentAndInsurance.getDiscountedRent().doubleValue()>=1) {
            sb
                    .append(SAVED_RENT_MESSAGE).append(this.rentAndInsurance.getDiscountedRent())
                    .append(System.lineSeparator())
                    .append(SAVED_INSURANCE_MESSAGE).append(this.rentAndInsurance.getFullyChargedInsurance().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP))
                    .append(System.lineSeparator());
        }

        sb
                .append(TOTAL_RENT_MESSAGE).append(this.rentAndInsurance.getTotalRentPaid())
                .append(System.lineSeparator())

                .append(TOTAL_INSURANCE_MESSAGE).append(this.rentAndInsurance.getTotalInsurancePaid())
                .append(System.lineSeparator())

                .append(TOTAL_MESSAGE).append(this.rentAndInsurance.getTotal())
                .append(System.lineSeparator());

        return sb.toString();
    }
}
