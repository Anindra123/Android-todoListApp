package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private EditText updateTaskTitle;
    private EditText updateTaskDesc;
    private EditText updateDueTime;
    private Button updateTaskButton;
    private Button updateTimeButton;
    private  String oldTaskTitle;
    private  String oldTaskDesc;
    private String oldDueTime;
    private  TaskData oldTaskData;
    private  int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        updateTaskTitle = findViewById(R.id.updateTasktitle);
        updateTaskDesc = findViewById(R.id.updateTaskDesc);
        updateTaskButton = findViewById(R.id.updateTaskbutton);
        updateDueTime = findViewById(R.id.updated_time);
        updateTimeButton = findViewById(R.id.set_updatedtime);


        if(getIntent().hasExtra("task_data")){
            oldTaskData  = getIntent().getParcelableExtra("task_data");
            oldTaskTitle = oldTaskData.getTaskTitle();
            oldTaskDesc  = oldTaskData.getTaskDescription();
            id = oldTaskData.getId();
            oldDueTime = oldTaskData.getdueTime();
        }

        updateTaskTitle.setText(oldTaskTitle);
        updateTaskDesc.setText(oldTaskDesc);
        updateDueTime.setText(oldDueTime);

        updateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePicker();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData updatedTask = new TaskData(id,updateTaskTitle.getText().toString(),updateTaskDesc.getText().toString(),updateDueTime.getText().toString());
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
                intent.putExtra("updated_task",updatedTask);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0,0,0,hourOfDay,minute);
        updateDueTime.setText(DateFormat.format("hh:mm aa",calendar));
    }
}