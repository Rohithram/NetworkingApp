package io.rohithram.networking;

/**
 * Created by rohithram on 19/5/17.
 */

public class Earthquake {

    private String location;
    private long time;
    private double magnitude;

    public Earthquake(String location, long time, double magnitude){
        this.location = location;
        this.time = time;
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
}
