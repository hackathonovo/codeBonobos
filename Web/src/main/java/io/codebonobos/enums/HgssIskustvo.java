package io.codebonobos.enums;

/**
 * Created by afilakovic on 20.05.17..
 */
public enum HgssIskustvo {
    PRIDRUZENI_CLAN(1, "Pridruzeni clan"),
    PRIPRAVNIK(2, "Pripravnik"),
    ISKUSAN(3, "Iskusan");

    private String code;
    private int value;

    HgssIskustvo(int value, String code) {
        this.value = value;
        this.code = code;
    }
}
