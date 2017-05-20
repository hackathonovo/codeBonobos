package io.codebonobos.entities;

/**
 * Created by afilakovic on 20.05.17..
 */
public class HgssLocation {
    private String lat;
    private String lng;

    public HgssLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
