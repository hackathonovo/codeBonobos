package io.codebonobos.entities;

/**
 * Created by afilakovic on 20.05.17..
 */
public class User {
    private String name;
    private String phone;
    private String email;
    private String fbToken;
    private String gPlusToken;

    public User() {
    }

    public User(String name, String phone, String email, String fbToken, String gPlusToken) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.fbToken = fbToken;
        this.gPlusToken = gPlusToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getgPlusToken() {
        return gPlusToken;
    }

    public void setgPlusToken(String gPlusToken) {
        this.gPlusToken = gPlusToken;
    }
}
