package com.duckstore.dto;

import com.duckstore.entity.DuckColor;
import com.duckstore.entity.DuckSize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class AddDuckRequest {

    @NotNull
    private DuckColor color;

    @NotNull
    private DuckSize size;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull
    @Min(1)
    private Integer quantity;

    public AddDuckRequest() {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}