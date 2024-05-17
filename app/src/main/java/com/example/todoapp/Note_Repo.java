package com.example.todoapp;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;




public class Note_Repo  {
    private Notes_DAO notesDao;
    LiveData<List<Note_Model>> noteList;

    Note_Repo(Application application){
        Database_Creation databaseCreation = Database_Creation.getInstance(application);
        notesDao = databaseCreation.getMethods();
        noteList = notesDao.getAllData();
    }


    // Insert in Background
    public void insertData(Note_Model noteModel){
        new InsertTask(notesDao).execute(noteModel);
    }
    // Update in Background
    public void updateData(Note_Model noteModel){
        new UpdateTask(notesDao).execute(noteModel);
    }
    // Delete in Background
    public void deleteData(Note_Model noteModel){
        new DeleteTask(notesDao).execute(noteModel);
    }

    // Fetching in Background
    public LiveData<List<Note_Model>> getAllData(){

        return noteList;
    }


    private static class InsertTask extends AsyncTask<Note_Model , Void , Void>{
        private Notes_DAO notesDao;

        public InsertTask(Notes_DAO notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note_Model... noteModels) {
            notesDao.insert(noteModels[0]);
            return null;
        }
    }
    private class UpdateTask extends AsyncTask<Note_Model,Void , Void>{
        private Notes_DAO notesDao;

        public UpdateTask(Notes_DAO notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note_Model... noteModels) {
            notesDao.update(noteModels[0]);
            return null;
        }
    }
    private class DeleteTask extends AsyncTask<Note_Model,Void , Void>{
        private Notes_DAO notesDao;

        public DeleteTask(Notes_DAO notesDao) {
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Note_Model... noteModels) {
            notesDao.delete(noteModels[0]);
            return null;
        }
    }
}
