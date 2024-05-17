package com.example.todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "my_notes")
public class Note_Model {
    private String title , description;
    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public Note_Model() {
    }

    public int getId() {
        return id;
    }

    public Note_Model(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
