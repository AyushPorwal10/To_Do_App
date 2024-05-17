package com.example.todoapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface Notes_DAO {
    @Insert
    public void insert(Note_Model note);
    @Update
    public void update(Note_Model note);
    @Delete
    public void delete(Note_Model note);

    @Query("Select * from my_notes")

    public LiveData<List<Note_Model>> getAllData();

}
