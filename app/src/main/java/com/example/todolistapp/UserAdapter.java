package com.example.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {
    private  ArrayList<TaskData> tasks;
    private  OnTaskListener taskListener;

    public  UserAdapter(ArrayList<TaskData> tasks,OnTaskListener taskListener){
        this.tasks = tasks;
        this.taskListener = taskListener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row,parent,false);
        return  new ViewHolder(view,taskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        TaskData exampleTask = tasks.get(position);
        holder.taskTitle.setText(exampleTask.getTaskTitle());
        holder.due_time.setText(exampleTask.getdueTime());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView taskTitle;
        ImageView deleteImage;
        ImageView updateImage;
        OnTaskListener taskListener;
        TextView due_time;

        public ViewHolder(@NonNull View itemView, final OnTaskListener taskListener) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            due_time = itemView.findViewById(R.id.time);
            deleteImage = itemView.findViewById(R.id.image_delete);
            updateImage = itemView.findViewById(R.id.image_update);
            this.taskListener = taskListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskListener.onItemClick(getAdapterPosition());
                }
            });
            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskListener.onDeleteClick(getAdapterPosition());
                }
            });
            updateImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskListener.onUpdateClick(getAdapterPosition());
                }
            });

        }
    }
}
