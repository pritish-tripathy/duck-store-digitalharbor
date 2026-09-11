package com.duckstore.service;

import com.duckstore.entity.DuckSize;
import com.duckstore.entity.ShippingMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackagingService {

    public String determinePackageType(DuckSize size) {

        if (size == DuckSize.XLARGE || size == DuckSize.LARGE) {
            return "WOOD";
        }

        if (size == DuckSize.MEDIUM) {
            return "CARDBOARD";
        }

        return "PLASTIC";
    }

    public List<String> determineProtectionTypes(
            String packageType,
            ShippingMode shippingMode) {

        if (shippingMode == ShippingMode.SEA) {
            return List.of(
                    "MOISTURE_ABSORBING_BEADS",
                    "BUBBLE_WRAP_BAGS"
            );
        }

        if (shippingMode == ShippingMode.LAND) {
            return List.of("POLYSTYRENE_BALLS");
        }

        if (packageType.equals("PLASTIC")) {
            return List.of("BUBBLE_WRAP_BAGS");
        }

        return List.of("POLYSTYRENE_BALLS");
    }
}