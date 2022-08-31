package com.tertandaid.openweather;


public class DataModelMainActivity {

    private int imageAwan;
    private String temperature;
    private String status;
    private String jam;


    public DataModelMainActivity(int imageAwan, String temperature, String status, String jam) {
        this.imageAwan = imageAwan;
        this.temperature = temperature;
        this.status = status;
        this.jam = jam;
    }

    public int getImageAwan() {
        return imageAwan;
    }

    public void setImageAwan(int imageAwan) {
        this.imageAwan = imageAwan;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public boolean isOnline() {
//        return online;
//    }

    public String getJam() {
        return jam;
    }

    public void setJam(String mobile) {
        this.jam = jam;
    }
}