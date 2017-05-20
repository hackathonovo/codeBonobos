package io.codebonobos.entities;

import java.sql.Timestamp;
import java.util.Date;
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
    private Timestamp timeSas;
    private String locSas;

    public Akcija() {
    }

    public HgssLocation getLocation() {
        return location;
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

    public Timestamp getTimeSas() {
        return timeSas;
    }

    public void setTimeSas(Timestamp timesSas) {
        this.timeSas = timesSas;
    }

    public String getLocSas() {
        return locSas;
    }

    public void setLocSas(String locSas) {
        this.locSas = locSas;
    }
}
