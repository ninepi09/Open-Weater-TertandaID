
package com.tertandaid.openweather.Model.Threeday;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Coord {

    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lon")
    @Expose
    private Integer lon;

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Coord withLat(Integer lat) {
        this.lat = lat;
        return this;
    }

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public Coord withLon(Integer lon) {
        this.lon = lon;
        return this;
    }

}
