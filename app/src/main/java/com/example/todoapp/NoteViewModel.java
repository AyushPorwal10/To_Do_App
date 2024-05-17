package com.example.todoapp;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private Note_Repo noteRepo;
    LiveData<List<Note_Model>> listLiveData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo = new Note_Repo(application);
        listLiveData = noteRepo.getAllData();
    }

    public void insert(Note_Model note){
        noteRepo.insertData(note);
    }
    public void update(Note_Model note){
        noteRepo.updateData(note);
    }
    public void delete(Note_Model note){
        noteRepo.deleteData(note);
    }
    public LiveData<List<Note_Model>> getAllData(){
        return listLiveData;
    }
}
