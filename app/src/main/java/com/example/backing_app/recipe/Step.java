package com.example.backing_app.recipe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * Java object that represents an Step. The associated table has a foreign key set to its
 * recipe. Please see RecipeDataBase for more info about the Database structure
 */

@Entity(tableName = "steps",
        indices =  {@Index("recipeId")},
        foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId",
        onDelete = CASCADE))

public class Step {
    @PrimaryKey(autoGenerate = true)
    private int key;
    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    @Ignore
    private String thumbnailURL;
    @ColumnInfo(name = "recipeId")
    private int recipeId;


    public Step(){

    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
