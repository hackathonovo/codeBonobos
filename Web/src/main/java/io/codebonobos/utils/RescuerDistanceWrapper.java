package io.codebonobos.utils;

import io.codebonobos.entities.Spasavatelj;

/**
 * Created by afilakovic on 20.05.17..
 */
public class RescuerDistanceWrapper {
    private Spasavatelj rescuer;
    private double distance;

    public RescuerDistanceWrapper() {
    }

    public RescuerDistanceWrapper(Spasavatelj rescuer, double distance) {
        this.rescuer = rescuer;
        this.distance = distance;
    }

    public Spasavatelj getRescuer() {
        return rescuer;
    }

    public void setRescuer(Spasavatelj rescuer) {
        this.rescuer = rescuer;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
