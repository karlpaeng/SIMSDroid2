package com.simsdroid.app;

public class ModelProducts {
    public String name;
    public String barcode;
    public double cost;
    public double retailPrice;
    public int amountStock;
    public String lastUpdate;

    public ModelProducts(String name, String barcode, double cost, double retailPrice, int amountStock, String lastUpdate) {
        this.name = name;
        this.barcode = barcode;
        this.cost = cost;
        this.retailPrice = retailPrice;
        this.amountStock = amountStock;
        this.lastUpdate = lastUpdate;
    }

    public ModelProducts() {
    }

    @Override
    public String toString() {
        return "ModelProducts{" +
                "name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", cost=" + cost +
                ", retailPrice=" + retailPrice +
                ", amountStock=" + amountStock +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
