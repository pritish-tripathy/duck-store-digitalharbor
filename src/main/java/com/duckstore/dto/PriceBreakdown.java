package com.duckstore.dto;

import java.math.BigDecimal;

public class PriceBreakdown {

    private String description;
    private BigDecimal amount;

    public PriceBreakdown() {
    }

    public PriceBreakdown(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}