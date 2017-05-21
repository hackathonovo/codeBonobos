package dev.skliba.guardianangel.data.models.response;


import com.google.gson.annotations.SerializedName;

public class PanicModeResponse {

    @SerializedName("user")
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
