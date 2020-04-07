package com.example.backing_app.recipe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;


/**
 * Represents a table with only one row that identifies the recipe selected by the user
 * This is needed by the widget in order to update the ingredients shown
 */

@Entity(tableName = "selected_recipe", indices = {@Index(value = {"index"},
        unique = true)})

public class SelectedRecipe {

    @PrimaryKey
    private int key = 1;

    @ColumnInfo(name = "index")
    private int index;

    public SelectedRecipe(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

