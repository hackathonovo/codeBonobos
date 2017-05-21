package dev.skliba.saviourapp.data.models.response;

import com.google.gson.annotations.SerializedName;

public class NewsResponse {

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
