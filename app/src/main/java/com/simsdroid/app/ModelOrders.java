package com.simsdroid.app;

public class ModelOrders {
    public long orderNumber;
    public String productName;
    public long productId;
    public double retailPrice;
    public int amount;
    public double amountXprice;

    public ModelOrders(long orderNumber, String productName, long productId, double retailPrice, int amount, double amountXprice) {
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
