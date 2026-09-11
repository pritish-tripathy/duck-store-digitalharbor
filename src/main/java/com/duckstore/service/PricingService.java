package com.duckstore.service;

import com.duckstore.dto.PriceBreakdown;
import com.duckstore.entity.Country;
import com.duckstore.entity.ShippingMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class PricingService {

    public PricingResult calculatePrice(
            BigDecimal price,
            Integer quantity,
            String packageType,
            String destinationCountry,
            ShippingMode shippingMode) {

        BigDecimal total =
                price.multiply(BigDecimal.valueOf(quantity));

        List<PriceBreakdown> breakdown = new ArrayList<>();

        breakdown.add(
                new PriceBreakdown(
                        "Base price",
                        total
                )
        );

        // 1. Bulk discount
        if (quantity > 100) {

            BigDecimal discount =
                    calculatePercentage(total, "0.20");

            total = total.subtract(discount);

            breakdown.add(
                    new PriceBreakdown(
                            "20% bulk discount",
                            discount.negate()
                    )
            );
        }

        // 2. Packaging adjustment
        if (packageType.equals("WOOD")) {

            BigDecimal adjustment =
                    calculatePercentage(total, "0.05");

            total = total.add(adjustment);

            breakdown.add(
                    new PriceBreakdown(
                            "Wood packaging +5%",
                            adjustment
                    )
            );

        } else if (packageType.equals("PLASTIC")) {

            BigDecimal adjustment =
                    calculatePercentage(total, "0.10");

            total = total.add(adjustment);

            breakdown.add(
                    new PriceBreakdown(
                            "Plastic packaging +10%",
                            adjustment
                    )
            );

        } else if (packageType.equals("CARDBOARD")) {

            BigDecimal adjustment =
                    calculatePercentage(total, "0.01");

            total = total.subtract(adjustment);

            breakdown.add(
                    new PriceBreakdown(
                            "Cardboard packaging -1%",
                            adjustment.negate()
                    )
            );
        }

        // 3. Country adjustment

        Country country =
                resolveCountry(destinationCountry);

        BigDecimal countryAdjustment;

        if (country == Country.USA) {

            countryAdjustment =
                    calculatePercentage(total, "0.18");

            breakdown.add(
                    new PriceBreakdown(
                            "USA destination +18%",
                            countryAdjustment
                    )
            );

        } else if (country == Country.BOLIVIA) {

            countryAdjustment =
                    calculatePercentage(total, "0.13");

            breakdown.add(
                    new PriceBreakdown(
                            "Bolivia destination +13%",
                            countryAdjustment
                    )
            );

        } else if (country == Country.INDIA) {

            countryAdjustment =
                    calculatePercentage(total, "0.19");

            breakdown.add(
                    new PriceBreakdown(
                            "India destination +19%",
                            countryAdjustment
                    )
            );

        } else {

            countryAdjustment =
                    calculatePercentage(total, "0.15");

            breakdown.add(
                    new PriceBreakdown(
                            "Other destination +15%",
                            countryAdjustment
                    )
            );
        }

        total = total.add(countryAdjustment);

        // 4. Shipping adjustment

        if (shippingMode == ShippingMode.SEA) {

            BigDecimal shippingCharge =
                    new BigDecimal("400.00");

            total = total.add(shippingCharge);

            breakdown.add(
                    new PriceBreakdown(
                            "Sea shipping +$400",
                            shippingCharge
                    )
            );

        } else if (shippingMode == ShippingMode.LAND) {

            BigDecimal shippingCharge =
                    new BigDecimal("10.00")
                            .multiply(BigDecimal.valueOf(quantity))
                            .setScale(2, RoundingMode.HALF_UP);

            total = total.add(shippingCharge);

            breakdown.add(
                    new PriceBreakdown(
                            "Land shipping +$10/unit",
                            shippingCharge
                    )
            );

        } else if (shippingMode == ShippingMode.AIR) {

            BigDecimal shippingCharge =
                    new BigDecimal("30.00")
                            .multiply(BigDecimal.valueOf(quantity))
                            .setScale(2, RoundingMode.HALF_UP);

            if (quantity > 1000) {

                BigDecimal discount =
                        calculatePercentage(
                                shippingCharge,
                                "0.15"
                        );

                shippingCharge =
                        shippingCharge.subtract(discount);

                breakdown.add(
                        new PriceBreakdown(
                                "Air shipping 15% reduction for quantity > 1000",
                                discount.negate()
                        )
                );
            }

            total = total.add(shippingCharge);

            breakdown.add(
                    new PriceBreakdown(
                            "Air shipping +$30/unit",
                            shippingCharge
                    )
            );
        }

        return new PricingResult(total, breakdown);
    }

    private Country resolveCountry(String destinationCountry) {

        if (destinationCountry.equalsIgnoreCase("USA")) {
            return Country.USA;
        }

        if (destinationCountry.equalsIgnoreCase("Bolivia")) {
            return Country.BOLIVIA;
        }

        if (destinationCountry.equalsIgnoreCase("India")) {
            return Country.INDIA;
        }

        return Country.OTHER;
    }

    private BigDecimal calculatePercentage(
            BigDecimal amount,
            String percentage) {

        return amount
                .multiply(new BigDecimal(percentage))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public record PricingResult(
            BigDecimal total,
            List<PriceBreakdown> breakdown) {
    }
}
