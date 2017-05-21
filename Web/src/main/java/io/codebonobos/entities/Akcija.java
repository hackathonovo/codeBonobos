package io.codebonobos.entities;

import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */
public class Akcija {
    private int id;
    private String voditelj;
    private HgssLocation location;
    private double radius;
    private String opis;
    private List<Spasavatelj> pozvaniSpasavatelji;
    private List<Spasavatelj> prihvaceniSpasavatelji;
    private boolean isActive;
    private String meetingTime;
    private String meetingLocation;
    private int priority;

    public Akcija() {
    }

    public HgssLocation getLocation() {
        return location;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setLocation(HgssLocation location) {
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public List<Spasavatelj> getPozvaniSpasavatelji() {
        return pozvaniSpasavatelji;
    }

    public void setPozvaniSpasavatelji(List<Spasavatelj> pozvaniSpasavatelji) {
        this.pozvaniSpasavatelji = pozvaniSpasavatelji;
    }

    public List<Spasavatelj> getPrihvaceniSpasavatelji() {
        return prihvaceniSpasavatelji;
    }

    public void setPrihvaceniSpasavatelji(List<Spasavatelj> prihvaceniSpasavatelji) {
        this.prihvaceniSpasavatelji = prihvaceniSpasavatelji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoditelj() {
        return voditelj;
    }

    public void setVoditelj(String voditelj) {
        this.voditelj = voditelj;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        this.isActive = active;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String timesSas) {
        this.meetingTime = timesSas;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }
}
