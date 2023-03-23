package com.simsdroid.app;

import java.math.BigDecimal;

public class ModelProducts {
    public long id;
    public String name;
    public String barcode;
    public BigDecimal cost;
    public BigDecimal retailPrice;
    public int amountStock;
    public String lastUpdate;

    public ModelProducts(long id, String name, String barcode, BigDecimal cost, BigDecimal retailPrice, int amountStock, String lastUpdate) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", cost=" + cost +
                ", retailPrice=" + retailPrice +
                ", amountStock=" + amountStock +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
