
package com.tertandaid.openweather.Model.Threeday;

//import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class ThreeDayList {

    @SerializedName("dt")
    @Expose
    private Integer dt;
    @SerializedName("main")
    @Expose
    private Main main;
    @SerializedName("weather")
    @Expose
    private java.util.List<Weather> weather = null;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("visibility")
    @Expose
    private Integer visibility;
    @SerializedName("pop")
    @Expose
    private Double pop;
    @SerializedName("sys")
    @Expose
    private Sys sys;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public ThreeDayList withDt(Integer dt) {
        this.dt = dt;
        return this;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public ThreeDayList withMain(Main main) {
        this.main = main;
        return this;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public ThreeDayList withWeather(java.util.List<Weather> weather) {
        this.weather = weather;
        return this;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public ThreeDayList withClouds(Clouds clouds) {
        this.clouds = clouds;
        return this;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public ThreeDayList withWind(Wind wind) {
        this.wind = wind;
        return this;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public ThreeDayList withVisibility(Integer visibility) {
        this.visibility = visibility;
        return this;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public ThreeDayList withPop(Double pop) {
        this.pop = pop;
        return this;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public ThreeDayList withSys(Sys sys) {
        this.sys = sys;
        return this;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    public ThreeDayList withDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
        return this;
    }

}
