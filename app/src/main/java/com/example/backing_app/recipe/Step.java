package com.example.backing_app.recipe;

public class Step {
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbailURL;

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setThumbailURL(String thumbailURL) {
        this.thumbailURL = thumbailURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbailURL() {
        return thumbailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
