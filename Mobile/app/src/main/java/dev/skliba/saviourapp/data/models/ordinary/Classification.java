package dev.skliba.saviourapp.data.models.ordinary;


import com.google.gson.annotations.SerializedName;

public enum Classification {

    @SerializedName("PRIDRUZENI_CLAN")
    NEW_MEMBER,

    @SerializedName("PRIPRAVNIK")
    INTERN,

    @SerializedName("ISKUSAN")
    EXPERIENCED;
}
