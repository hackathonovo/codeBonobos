package dev.skliba.saviourapp.data.models.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.skliba.saviourapp.data.models.ordinary.Location;
import dev.skliba.saviourapp.data.models.ordinary.Rescuer;

public class ContactResponse {

    @SerializedName("id")
    private int actionId;

    @SerializedName("location")
    private Location location;

    @SerializedName("opis")
    private String description;

    @SerializedName("pozvaniSpasavatelji")
    private List<Rescuer> rescuerList;

    @SerializedName("prihvacenaSpasavatelji")
    private List<Rescuer> acceptedRescuers;

    @SerializedName("radius")
    private int radius;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Rescuer> getRescuerList() {
        return rescuerList;
    }

    public void setRescuerList(List<Rescuer> rescuerList) {
        this.rescuerList = rescuerList;
    }

    public List<Rescuer> getAcceptedRescuers() {
        return acceptedRescuers;
    }

    public void setAcceptedRescuers(List<Rescuer> acceptedRescuers) {
        this.acceptedRescuers = acceptedRescuers;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
