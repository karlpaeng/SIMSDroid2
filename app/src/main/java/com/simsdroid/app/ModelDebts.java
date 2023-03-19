package com.simsdroid.app;

public class ModelDebts {
    public long orderNumber;
    public String customerName;
    public String customerContact;
    public String dateCheckout;
    public String datePaid;

    public ModelDebts(long orderNumber, String customerName, String customerContact, String dateCheckout, String datePaid) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.dateCheckout = dateCheckout;
        this.datePaid = datePaid;
    }

    public ModelDebts() {
    }

    @Override
    public String toString() {
        return "ModelDebts{" +
                "orderNumber=" + orderNumber +
                ", customerName='" + customerName + '\'' +
                ", customerContact='" + customerContact + '\'' +
                ", dateCheckout='" + dateCheckout + '\'' +
                ", datePaid='" + datePaid + '\'' +
                '}';
    }
}
