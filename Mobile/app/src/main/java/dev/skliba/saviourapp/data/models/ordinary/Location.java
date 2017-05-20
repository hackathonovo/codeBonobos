package dev.skliba.saviourapp.data.models.ordinary;


import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("lat")
    private String latitude;

    @SerializedName("lng")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
