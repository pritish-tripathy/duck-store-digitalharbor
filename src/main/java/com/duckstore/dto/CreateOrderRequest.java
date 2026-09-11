package com.duckstore.dto;

import com.duckstore.entity.DuckColor;
import com.duckstore.entity.DuckSize;
import com.duckstore.entity.ShippingMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateOrderRequest {

    @NotNull
    private DuckColor color;

    @NotNull
    private DuckSize size;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotBlank
    private String destinationCountry;

    @NotNull
    private ShippingMode shippingMode;

    public CreateOrderRequest() {
    }

    public DuckColor getColor() {
        return color;
    }

    public void setColor(DuckColor color) {
        this.color = color;
    }

    public DuckSize getSize() {
        return size;
    }

    public void setSize(DuckSize size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public ShippingMode getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(ShippingMode shippingMode) {
        this.shippingMode = shippingMode;
    }
}