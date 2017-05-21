package io.codebonobos.enums;

/**
 * Created by afilakovic on 20.05.17..
 */
public enum HgssSpecijalnost {
    SPELEOLOGIJA(1, "Speleologija"),
    ALPINIZAM(2, "Alpinizam"),
    TURNO_SKIJANJE(3, "Turno skijanje"),
    VODIC_POTRAZNIH_PASA(4, "Vodic potraznih pasa"),
    DOKTOR(5, "Doktor"),
    CRTAC_MAPE(6, "Crtac mape"),
    HELIKOPTER(7, "Helikoptersko spasavanje");

    private String code;
    private int value;

    HgssSpecijalnost(int value, String code) {
        this.code = code;
        this.value = value;
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

    public static HgssSpecijalnost getByValue(int i) {
        switch (i) {
            case 1:
                return SPELEOLOGIJA;
            case 2:
                return ALPINIZAM;
            case 3:
                return TURNO_SKIJANJE;
            case 4:
                return VODIC_POTRAZNIH_PASA;
            case 5:
                return DOKTOR;
            case 6:
                return CRTAC_MAPE;
            case 7:
                return HELIKOPTER;
            default:
                return null;
        }
    }
}
