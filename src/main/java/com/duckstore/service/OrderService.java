package com.duckstore.service;

import com.duckstore.dto.CreateOrderRequest;
import com.duckstore.dto.OrderResponse;
import com.duckstore.entity.Duck;
import com.duckstore.exception.DuckAvailabilityException;
import com.duckstore.exception.DuckNotFoundException;
import com.duckstore.repository.DuckRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final DuckRepository duckRepository;
    private final PackagingService packagingService;
    private final PricingService pricingService;

    public OrderService(
            DuckRepository duckRepository,
            PackagingService packagingService,
            PricingService pricingService) {

        this.duckRepository = duckRepository;
        this.packagingService = packagingService;
        this.pricingService = pricingService;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        List<Duck> ducks =
                duckRepository.findActiveDucksByColorAndSize(
                        request.getColor(),
                        request.getSize()
                );

        if (ducks.isEmpty()) {
            throw new DuckAvailabilityException(
                    "No active duck available for color: "
                            + request.getColor()
                            + " and size: "
                            + request.getSize()
            );
        }

        Duck duck = ducks.get(0);

        String packageType =
                packagingService.determinePackageType(
                        request.getSize()
                );

        List<String> protectionTypes =
                packagingService.determineProtectionTypes(
                        packageType,
                        request.getShippingMode()
                );

        PricingService.PricingResult pricingResult =
                pricingService.calculatePrice(
                        duck.getPrice(),
                        request.getQuantity(),
                        packageType,
                        request.getDestinationCountry(),
                        request.getShippingMode()
                );

        return new OrderResponse(
                packageType,
                protectionTypes,
                pricingResult.total(),
                pricingResult.breakdown()
        );
    }
}
