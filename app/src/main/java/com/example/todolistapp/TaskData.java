package com.example.todolistapp;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskData implements  Parcelable{
    private int id;
    private  String taskTitle;
    private  String taskDescription;
    private String due_time;
    public TaskData(int id,String taskTitle,String taskDescription,String due_time){
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.due_time = due_time;
    }


    protected TaskData(Parcel in) {
        id = in.readInt();
        taskTitle = in.readString();
        taskDescription = in.readString();
        due_time = in.readString();
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    public String getTaskTitle(){
        return  taskTitle;
    }
    public String getTaskDescription(){
        return  taskDescription;
    }
    public int getId(){return id;}
    public String getdueTime(){return due_time;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskTitle);
        dest.writeString(taskDescription);
        dest.writeString(due_time);
    }
}
