package io.codebonobos.utils;

import io.codebonobos.entities.Spasavatelj;

import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */
public class RescuerListsWrapper {
    private List<Spasavatelj> available;
    private List<Spasavatelj> inAction;
    private List<Spasavatelj> inactive;

    public RescuerListsWrapper(List<Spasavatelj> available, List<Spasavatelj> inAction, List<Spasavatelj> inactive) {
        this.available = available;
        this.inAction = inAction;
        this.inactive = inactive;
    }

    public List<Spasavatelj> getAvailable() {
        return available;
    }

    public void setAvailable(List<Spasavatelj> available) {
        this.available = available;
    }

    public List<Spasavatelj> getInAction() {
        return inAction;
    }

    public void setInAction(List<Spasavatelj> inAction) {
        this.inAction = inAction;
    }

    public List<Spasavatelj> getInactive() {
        return inactive;
    }

    public void setInactive(List<Spasavatelj> inactive) {
        this.inactive = inactive;
    }
}
