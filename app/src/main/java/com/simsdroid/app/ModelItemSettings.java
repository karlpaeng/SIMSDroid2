package com.simsdroid.app;

public class ModelItemSettings {
    public String text;
    public int id;

    public ModelItemSettings(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public ModelItemSettings() {
    }

    @Override
    public String toString() {
        return "ModelItemSettings{" +
                "text='" + text + '\'' +
                ", id=" + id +
                '}';
    }
}
