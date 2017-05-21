package io.codebonobos.entities;

import io.codebonobos.enums.HgssIskustvo;
import io.codebonobos.enums.HgssSpecijalnost;

/**
 * Created by afilakovic on 20.05.17..
 */
public class Spasavatelj {
    private int id;
    private String ime;
    private String brojTelefona;
    private HgssSpecijalnost specijalnost;
    private HgssIskustvo iskustvo;
    private HgssLocation lokacija;
    private boolean isActive;

    public Spasavatelj() {
    }

    public Spasavatelj(int id, String ime, String brojTelefona, HgssSpecijalnost specijalnost, HgssIskustvo iskustvo, HgssLocation lokacija, boolean isActive) {
        this.id = id;
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.specijalnost = specijalnost;
        this.iskustvo = iskustvo;
        this.lokacija = lokacija;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public HgssSpecijalnost getSpecijalnost() {
        return specijalnost;
    }

    public void setSpecijalnost(HgssSpecijalnost specijalnost) {
        this.specijalnost = specijalnost;
    }

    public HgssIskustvo getIskustvo() {
        return iskustvo;
    }

    public void setIskustvo(HgssIskustvo iskustvo) {
        this.iskustvo = iskustvo;
    }

    public HgssLocation getLokacija() {
        return lokacija;
    }

    public void setLokacija(HgssLocation lokacija) {
        this.lokacija = lokacija;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
