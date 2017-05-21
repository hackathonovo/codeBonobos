package dev.skliba.saviourapp.data.models.ordinary;


import com.google.gson.annotations.SerializedName;

public class Rescuer {

    @SerializedName("brojTelefona")
    private String phoneNumber;

    @SerializedName("id")
    private int rescuerId;

    @SerializedName("ime")
    private String name;

    @SerializedName("iskustvo")
    private String classification;

    @SerializedName("location")
    private Location location;

    @SerializedName("specijalnost")
    private String specialty;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRescuerId() {
        return rescuerId;
    }

    public void setRescuerId(int rescuerId) {
        this.rescuerId = rescuerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
