package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private  TaskData taskData;
    private TextView taskDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if(getIntent().hasExtra("task_data")){
            taskData = getIntent().getParcelableExtra("task_data");
            setTitle(taskData.getTaskTitle());
        }
        taskDesc = findViewById(R.id.taskDesc);
        taskDesc.setText(taskData.getTaskDescription());
    }
}