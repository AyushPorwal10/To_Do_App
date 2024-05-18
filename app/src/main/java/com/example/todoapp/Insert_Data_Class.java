package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.WallpaperInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityInsertDataClassBinding;

import java.util.Calendar;
import java.util.Objects;

public class Insert_Data_Class extends AppCompatActivity {
    ActivityInsertDataClassBinding binding;
    private int currentHour , cuttentMinute;
    public long timeToTriggerNotification =0;
    public static final String PREFS_NAME = "TodoAppPrefs";
    public static final String TASK_ID_KEY = "taskIdKey";
    public static int taskId = 1;

    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertDataClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window =  this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        //shared preferences

         preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        taskId = preferences.getInt(TASK_ID_KEY,1);


        // time picker
        binding.getNotified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentTime = Calendar.getInstance();
                currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
                cuttentMinute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(Insert_Data_Class.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                        Calendar targetTime = Calendar.getInstance();
                        targetTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        targetTime.set(Calendar.MINUTE , minuteOfDay);
                        targetTime.set(Calendar.SECOND, 0);
                        targetTime.set(Calendar.MILLISECOND, 0);
                        if (targetTime.before(currentTime)) {
                            targetTime.add(Calendar.DATE,1);
                        }


                         timeToTriggerNotification = targetTime.getTimeInMillis();

                    }
                },currentHour,cuttentMinute,false);
                timePickerDialog.show();
            }
        });

        String type = getIntent().getStringExtra("type");

        if(type.equals("update")){
            setTitle("Update Note");
            binding.titleOftask.setText(getIntent().getStringExtra("title"));
            binding.taskDescription.setText(getIntent().getStringExtra("description"));
            int id = getIntent().getIntExtra("id",0);
            binding.insertDataBtn.setText("Update");
            binding.insertDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = Objects.requireNonNull(binding.titleOftask.getText()).toString();
                    String description  = Objects.requireNonNull(binding.taskDescription.getText()).toString();

                    if(title.isEmpty() || description.isEmpty()){
                        Toast.makeText(Insert_Data_Class.this,"Title or Description can't be empty ",Toast.LENGTH_SHORT).show();
                    }
                    else{
                       result_of_on_activity_result(title,description,id);
                    }

                }
            });
        }
        else{
        setTitle("Add Note");

        binding.insertDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = Objects.requireNonNull(binding.titleOftask.getText()).toString();
                String description  = Objects.requireNonNull(binding.taskDescription.getText()).toString();
                if(title.isEmpty() || description.isEmpty()){
                    Toast.makeText(Insert_Data_Class.this,"Title or Description can't be empty ",Toast.LENGTH_SHORT).show();
                }
                else{
                   result_of_on_activity_result(title,description,0);
                }
            }
        });
        }
    }

public void result_of_on_activity_result(String title , String description,int idForUpdate){
    Intent intent = new Intent();
    intent.putExtra("title",title);
    intent.putExtra("description",description);
    taskId++;
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(TASK_ID_KEY,taskId);
    editor.apply();

    Log.d("TRIGGET TIME RECEIVED ",taskId +"");

    intent.putExtra("taskId",taskId);
    intent.putExtra("id",idForUpdate);
    Log.d("TRIGGET TIME SENT ","TRIGGET TIME SENT " + timeToTriggerNotification);
    intent.putExtra("triggerTime",timeToTriggerNotification);
    setResult(RESULT_OK,intent);
    finish();
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}