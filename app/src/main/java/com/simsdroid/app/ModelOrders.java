package com.simsdroid.app;

public class ModelOrders {
    public long orderNumber;
    public String date;
    public String time;
    public String productName;
    public double retailPrice;
    public int amount;
    public double amountXprice;

    public ModelOrders(long orderNumber, String date, String time, String productName, double retailPrice, int amount, double amountXprice) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.time = time;
        this.productName = productName;
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
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", productName='" + productName + '\'' +
                ", retailPrice=" + retailPrice +
                ", amount=" + amount +
                ", amountXprice=" + amountXprice +
                '}';
    }
}
