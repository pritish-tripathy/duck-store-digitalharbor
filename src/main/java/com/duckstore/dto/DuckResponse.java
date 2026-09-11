package com.duckstore.dto;

import com.duckstore.entity.DuckColor;
import com.duckstore.entity.DuckSize;

import java.math.BigDecimal;

public class DuckResponse {

    private Integer id;
    private DuckColor color;
    private DuckSize size;
    private BigDecimal price;
    private Integer quantity;

    public DuckResponse() {
    }

    public DuckResponse(
            Integer id,
            DuckColor color,
            DuckSize size,
            BigDecimal price,
            Integer quantity) {

        this.id = id;
        this.color = color;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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