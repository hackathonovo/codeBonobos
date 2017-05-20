package io.codebonobos.entities;

import io.codebonobos.enums.HgssIskustvo;

/**
 * Created by afilakovic on 20.05.17..
 */
public class Spasavatelj {
    private int id;
    private String ime;
    private String brojTelefona;
    private String specijalnost;
    private HgssIskustvo iskustvo;
    private HgssLocation lokacija;

    public Spasavatelj() {
    }

    public Spasavatelj(int id, String ime, String brojTelefona, String specijalnost, HgssIskustvo iskustvo, HgssLocation lokacija) {
        this.id = id;
        this.ime = ime;
        this.brojTelefona = brojTelefona;
        this.specijalnost = specijalnost;
        this.iskustvo = iskustvo;
        this.lokacija = lokacija;
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

    public String getSpecijalnost() {
        return specijalnost;
    }

    public void setSpecijalnost(String specijalnost) {
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
}
