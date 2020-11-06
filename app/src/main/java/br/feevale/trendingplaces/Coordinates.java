package br.feevale.trendingplaces;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private double latitute;
    private double longitude;

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
