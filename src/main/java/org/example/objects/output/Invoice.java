package org.example.objects.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.objects.vehicle.Vehicle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.example.constants.InvoiceMessages.*;

@Getter
@Setter
@AllArgsConstructor
public class Invoice {

    private String customerName;
    private Vehicle vehicle;

    private String startDate;
    private String endDate;
    private String returnDate;

    private long daysRentedFor;
    private long daysUsedFor;

    private RentAndInsurance rentAndInsurance;

    public String formatInvoice(boolean isDiscount) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
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

                .append(RENTAL_COST_PER_DAY_MESSAGE).append(this.vehicle.getRentalCost().setScale(2, RoundingMode.HALF_UP))
                .append(System.lineSeparator());

        if (this.vehicle.isModified()) {
            stringBuilder
                    .append(INITIAL_INSURANCE_PER_DAY_MESSAGE).append(this.vehicle.getInitInsurance().setScale(2, RoundingMode.HALF_UP))
                    .append(System.lineSeparator());
            if (isDiscount) {
                stringBuilder
                        .append(DISCOUNT_PER_DAY_MESSAGE).append(this.vehicle.getInsuranceModifier().setScale(2, RoundingMode.HALF_UP))
                        .append(System.lineSeparator());
            } else {
                stringBuilder
                        .append(SURCHARGE_PER_DAY_MESSAGE).append(this.vehicle.getInsuranceModifier().setScale(2, RoundingMode.HALF_UP))
                        .append(System.lineSeparator());
            }
        }

        stringBuilder
                .append(INSURANCE_PER_DAY_MESSAGE).append(this.vehicle.getInsurance().setScale(2, RoundingMode.HALF_UP))
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        if (this.rentAndInsurance.getDiscountedRent().doubleValue()>=1) {
            stringBuilder
                    .append(SAVED_RENT_MESSAGE).append(this.rentAndInsurance.getDiscountedRent().setScale(2, RoundingMode.HALF_UP))
                    .append(System.lineSeparator())
                    .append(SAVED_INSURANCE_MESSAGE).append(this.rentAndInsurance.getFullyChargedInsurance().divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP)
                            .setScale(2, RoundingMode.HALF_UP))
                    .append(System.lineSeparator());
        }

        stringBuilder
                .append(TOTAL_RENT_MESSAGE).append(this.rentAndInsurance.getTotalRentPaid().setScale(2, RoundingMode.HALF_UP))
                .append(System.lineSeparator())

                .append(TOTAL_INSURANCE_MESSAGE).append(this.rentAndInsurance.getTotalInsurancePaid().setScale(2, RoundingMode.HALF_UP))
                .append(System.lineSeparator())

                .append(TOTAL_MESSAGE).append(this.rentAndInsurance.getTotal().setScale(2, RoundingMode.HALF_UP))
                .append(System.lineSeparator());

        return stringBuilder.toString();
    }
}
