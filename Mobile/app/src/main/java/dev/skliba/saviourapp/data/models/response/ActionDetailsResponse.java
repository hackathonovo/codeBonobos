package dev.skliba.saviourapp.data.models.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.skliba.saviourapp.data.models.ordinary.Location;
import dev.skliba.saviourapp.data.models.ordinary.Rescuer;

public class ActionDetailsResponse {

    @SerializedName("id")
    private int actionId;

    @SerializedName("voditelj")
    private String leaderPhoneNo;

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

    @SerializedName("meetingTime")
    private String meetingTime;

    @SerializedName("meetingLocation")
    private String meetingLocation;

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

    public String getLeaderPhoneNo() {
        return leaderPhoneNo;
    }

    public void setLeaderPhoneNo(String leaderPhoneNo) {
        this.leaderPhoneNo = leaderPhoneNo;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }
}
