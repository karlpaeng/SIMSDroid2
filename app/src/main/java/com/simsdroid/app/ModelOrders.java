package com.simsdroid.app;

import java.math.BigDecimal;

public class ModelOrders {
    public long orderNumber;
    public String productName;
    public long productId;
    public BigDecimal retailPrice;
    public int amount;
    public BigDecimal amountXprice;

    public ModelOrders(long orderNumber, String productName, long productId, BigDecimal retailPrice, int amount, BigDecimal amountXprice) {
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.productId = productId;
        this.retailPrice = retailPrice;
        this.amount = amount;
        this.amountXprice = amountXprice;
    }

    public ModelOrders() {
    }

    @Override
    public String toString() {
        return "ModelOrders{" +
                "orderNumber=" + orderNumber +
                ", productName='" + productName + '\'' +
                ", productId=" + productId +
                ", retailPrice=" + retailPrice +
                ", amount=" + amount +
                ", amountXprice=" + amountXprice +
                '}';
    }
}
