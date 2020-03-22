package com.example.backing_app.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbailURL;

    protected Step(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbailURL = in.readString();
    }

    public Step(){

    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbailURL);
    }
}
