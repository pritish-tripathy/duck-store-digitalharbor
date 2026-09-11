package com.duckstore.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

    private String packageType;
    private List<String> protectionTypes;
    private BigDecimal total;
    private List<PriceBreakdown> priceBreakdown;

    public OrderResponse() {
    }

    public OrderResponse(
            String packageType,
            List<String> protectionTypes,
            BigDecimal total,
            List<PriceBreakdown> priceBreakdown) {

        this.packageType = packageType;
        this.protectionTypes = protectionTypes;
        this.total = total;
        this.priceBreakdown = priceBreakdown;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public List<String> getProtectionTypes() {
        return protectionTypes;
    }

    public void setProtectionTypes(List<String> protectionTypes) {
        this.protectionTypes = protectionTypes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<PriceBreakdown> getPriceBreakdown() {
        return priceBreakdown;
    }

    public void setPriceBreakdown(List<PriceBreakdown> priceBreakdown) {
        this.priceBreakdown = priceBreakdown;
    }
}