package io.codebonobos.entities;

import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */
public class Akcija {
    private HgssLocation location;
    private double radius;
    private String opis;
    private List<Spasavatelj> pozvaniSpasavatelji;
    private List<Spasavatelj> prihvaceniSpasavatelji;

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
}
