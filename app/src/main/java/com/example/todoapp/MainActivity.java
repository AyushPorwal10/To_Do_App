package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    String isTitleUpdate = null;
    String isDescriptionUpdate = null;
    public static  PendingIntent pendingIntent;
    static int REQUEST_CODE ;
    public static final String CHANNEL_ID = "channel_id";
    Custom_Adapter adapter;
    int swipedItem = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //
        creating_Notification_channel();
        //


        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.blue));

        noteViewModel  =  new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);



        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         adapter = new Custom_Adapter();

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Insert_Data_Class.class);
                intent.putExtra("type","addNote");
                startActivityForResult(intent,1);
                
            }
        });
        binding.mainRecyclerView.setAdapter(adapter);
        noteViewModel.getAllData().observe(this, new Observer<List<Note_Model>>() {
            @Override
            public void onChanged(List<Note_Model> noteModels) {
                adapter.submitList(noteModels);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // to check whether update is done or not


            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // deleted when swipe left

                if(direction == ItemTouchHelper.LEFT) {
                    noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                }
                //update when swipe right

                else if(direction == ItemTouchHelper.RIGHT){

                    Intent intent = new Intent(MainActivity.this,Insert_Data_Class.class);
                    isTitleUpdate = adapter.getNote(viewHolder.getAdapterPosition()).getTitle();
                    swipedItem = viewHolder.getAdapterPosition();
                    isDescriptionUpdate = adapter.getNote(viewHolder.getAdapterPosition()).getDescription();
                    intent.putExtra("title",isTitleUpdate);
                    intent.putExtra("type","update");
                    intent.putExtra("description",isDescriptionUpdate);
                    intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                    startActivityForResult(intent,2);

                }
            }
        }).attachToRecyclerView(binding.mainRecyclerView);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == 1){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            Note_Model model = new Note_Model(title,description);
            noteViewModel.insert(model);
            Toast.makeText(MainActivity.this,"Added successfully",Toast.LENGTH_SHORT).show();
            manage_timer(data);

        }
        else if(requestCode == 2 && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");

            if(title.equals(isTitleUpdate) && description.equals(isDescriptionUpdate) ){
                adapter.notifyItemChanged(swipedItem);
            }

            else {
            Note_Model noteModel = new Note_Model(title,description);
            Log.d("DATA TO BE UPDATED",title + " " + description);
            noteModel.setId(data.getIntExtra("id",0));
            noteViewModel.update(noteModel);
            Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
            }
            manage_timer(data);
        }
        else
            adapter.notifyItemChanged(swipedItem);
         swipedItem = -1;




    }
    public void creating_Notification_channel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    @SuppressLint("ScheduleExactAlarm")
    public void startingTimerToNotify(long triggerTime, int taskId){
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this,MyBroadCast.class);
        Log.d("Task Id in alarm manager",taskId +"");
        REQUEST_CODE = taskId;
        pendingIntent  = PendingIntent.getBroadcast(MainActivity.this,REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,triggerTime,pendingIntent);
        Log.d("GOING To BROADCASTRECEIVER","GOING To BROADCASTRECEIVER");
    }
    public void manage_timer(Intent data){
        int taskId = data.getIntExtra("taskId",-1);
        Log.d("TRIGGET TIME RECEIVED ",taskId +"");
        long triggertime = data.getLongExtra("triggerTime",0);
        Log.d("TRIGGET TIME RECEIVED ",triggertime +"");
        if (triggertime > System.currentTimeMillis()) {
            startingTimerToNotify(triggertime,taskId);
        } else {
            Log.d("TRIGGER TIME ERROR", "Trigger time is in the past. Notification not set.");
        }
    }


}