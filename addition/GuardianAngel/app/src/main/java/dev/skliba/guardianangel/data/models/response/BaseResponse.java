package dev.skliba.guardianangel.data.models.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("response")
    private T response;

    @SerializedName("message")
    private String message;

    public BaseResponse() {
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
