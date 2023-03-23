package com.simsdroid.app;

import java.math.BigDecimal;

public class ModelOrderView {
    public long orderNumber;
    public String date;
    public String time;
    public BigDecimal total;

    public ModelOrderView(long orderNumber, String date, String time, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.date = date;
        this.time = time;
        this.total = total;
    }

    public ModelOrderView() {
    }

    @Override
    public String toString() {
        return "ModelOrderView{" +
                "orderNumber=" + orderNumber +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", total=" + total +
                '}';
    }
}
