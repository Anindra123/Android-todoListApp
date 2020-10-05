package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  OnTaskListener{
    private static final String TAG = "MainActivity";
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noitems;
    private ArrayList<TaskData> tasks;
    private TaskDataBase db;
    private String taskTitle;
    private String taskDesc;
    private String due_time;
    private int id;
    private  int Position;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddTask.class);
                startActivity(intent);
            }
        });
        db = new TaskDataBase(MainActivity.this);
        tasks = new ArrayList<>();
        addItems();
        buildRecyclerView();
    }

    private void addItems(){
        Cursor cursor = db.allTask();
        if(cursor.getCount() == 0 ){
            noitems = findViewById(R.id.noitems);
            noitems.setText("No task currently available");
            noitems.setEnabled(true);
        }else{
            while (cursor.moveToNext()){
                id = cursor.getInt(0);
                taskTitle = cursor.getString(1);
                taskDesc = cursor.getString(2);
                due_time = cursor.getString(3);
                TaskData taskData = new TaskData(id,taskTitle,taskDesc,due_time);
                tasks.add(taskData);
            }
        }
    }

    private  void removeItems(final int position){
        final TaskData taskData = tasks.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ taskData.getTaskTitle()+" ?");
        builder.setMessage("Are you sure you want to delete "+taskData.getTaskTitle()+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteTask(String.valueOf(taskData.getId()));
                adapter.notifyItemRemoved(position);
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }

    private void updateItems(int position,Intent intent){
        TaskData taskData = intent.getParcelableExtra("updated_task");
        int id = taskData.getId();
        String updated_title = taskData.getTaskTitle();
        String updated_desc = taskData.getTaskDescription();
        String due_time = taskData.getdueTime();
        db.updateTask(String.valueOf(id),updated_title,updated_desc,due_time);
        adapter.notifyItemChanged(position);
    }

    private void buildRecyclerView(){

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(tasks,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,DisplayActivity.class);
        intent.putExtra("task_data",tasks.get(position));
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {
        removeItems(position);

    }

    @Override
    public void onUpdateClick(int position) {
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra("task_data",tasks.get(position));
        startActivityForResult(intent,1);
        Position = position;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                updateItems(Position,data);
                recreate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            db.deleteAllTask();
            Toast.makeText(getApplicationContext(),"All tasks removed",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}