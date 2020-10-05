package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "AddTask";
    private EditText taskTitle;
    private EditText taskDescription;
    private EditText due_time;
    private Button set_time;
    private Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskTitle = findViewById(R.id.setTasktitle);
        taskDescription = findViewById(R.id.setTextDescription);
        due_time = findViewById(R.id.due_time);
        set_time = findViewById(R.id.set_time);
        taskTitle.addTextChangedListener(textWatcher);
        taskDescription.addTextChangedListener(textWatcher);
        due_time.addTextChangedListener(textWatcher);
        saveButton = findViewById(R.id.saveButton);
        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePicker();
                timepicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDataBase db = new TaskDataBase(AddTask.this);
                db.addTask(taskTitle.getText().toString().trim(),taskDescription.getText().toString().trim(),due_time.getText().toString());
                Intent intent = new Intent(AddTask.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String task_title = taskTitle.getText().toString().trim();
            String task_desc = taskDescription.getText().toString().trim();
            String time = due_time.getText().toString().trim();
            saveButton.setEnabled(!task_title.isEmpty() && !task_desc.isEmpty() && !time.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0,0,0,hourOfDay,minute);
        due_time.setText(DateFormat.format("hh:mm aa",calendar));
    }
}