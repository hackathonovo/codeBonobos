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

    public static HgssIskustvo getByValue(int i) {
        switch(i) {
            case 1:
                return PRIDRUZENI_CLAN;
            case 2:
            default:
                return PRIPRAVNIK;
            case 3:
                return ISKUSAN;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
