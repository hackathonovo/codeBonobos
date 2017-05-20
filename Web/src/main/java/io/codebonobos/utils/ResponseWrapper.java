package io.codebonobos.utils;

/**
 * Created by afilakovic on 20.05.17..
 */
public class ResponseWrapper<T> {
    private T response;
    private String message;

    public ResponseWrapper() {
    }

    public ResponseWrapper(T response, String message) {
        this.response = response;
        this.message = message;
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
