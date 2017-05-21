package dev.skliba.guardianangel.data.models.ordinary;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public final class FacebookProfile implements Parcelable {

    public static final Creator<FacebookProfile> CREATOR = new Creator<FacebookProfile>() {
        @Override
        public FacebookProfile createFromParcel(Parcel source) {
            return new FacebookProfile(source);
        }

        @Override
        public FacebookProfile[] newArray(int size) {
            return new FacebookProfile[size];
        }
    };

    private final String id;

    private final String firstName;

    private final String lastName;

    private final String middleName;

    private final String name;

    private final Uri linkUri;

    public FacebookProfile(String id, String firstName, String lastName, String middleName, String name, Uri linkUri) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.name = name;
        this.linkUri = linkUri;
    }

    protected FacebookProfile(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.middleName = in.readString();
        this.name = in.readString();
        this.linkUri = in.readParcelable(Uri.class.getClassLoader());
    }

    @Override
    public String toString() {
        return "FacebookProfile{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
                + ", middleName='" + middleName + '\'' + ", name='" + name + '\'' + ", linkUri=" + linkUri + '}';
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getName() {
        return name;
    }

    public Uri getLinkUri() {
        return linkUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.middleName);
        dest.writeString(this.name);
        dest.writeParcelable(this.linkUri, flags);
    }
}