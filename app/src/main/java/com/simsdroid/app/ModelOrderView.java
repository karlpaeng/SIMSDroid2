package com.simsdroid.app;

public class ModelOrderView {
    public long orderNumber;
    public String date;
    public String time;
    public double total;

    public ModelOrderView(long orderNumber, String date, String time, double total) {
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
