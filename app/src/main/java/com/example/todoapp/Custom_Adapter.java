package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.databinding.SingleRowBinding;

import java.util.List;

public class Custom_Adapter extends ListAdapter<Note_Model,Custom_Adapter.MyViewHolder> {


   public Custom_Adapter(){
       super(CALLBACK);
   }
   private static final DiffUtil.ItemCallback<Note_Model> CALLBACK = new DiffUtil.ItemCallback<Note_Model>() {
       @Override
       public boolean areItemsTheSame(@NonNull Note_Model oldItem, @NonNull Note_Model newItem) {
           return oldItem.getId() == newItem.getId();
       }

       @Override
       public boolean areContentsTheSame(@NonNull Note_Model oldItem, @NonNull Note_Model newItem) {
           return oldItem.getTitle().equals(newItem.getTitle()) && newItem.getDescription().equals(oldItem.getDescription());
       }
   };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note_Model noteModel = getNote(position);
        holder.binding.textView.setText(noteModel.getTitle());
        holder.binding.textView2.setText(noteModel.getDescription());
    }
    public Note_Model getNote(int position){
        return getItem(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        SingleRowBinding binding;
        MyViewHolder(@NonNull View  itemView){
            super(itemView);
            binding = SingleRowBinding.bind(itemView);
        }
    }
}
