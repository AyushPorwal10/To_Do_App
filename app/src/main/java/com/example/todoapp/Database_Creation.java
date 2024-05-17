package com.example.todoapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Note_Model.class ,version = 1)
abstract public class Database_Creation extends RoomDatabase {
    private static  Database_Creation instace;
    public static  synchronized Database_Creation getInstance(Context context){
        if(instace == null) {
            instace = Room.databaseBuilder(context.getApplicationContext(), Database_Creation.class, "notes_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instace;
    }
   abstract public Notes_DAO getMethods();
}