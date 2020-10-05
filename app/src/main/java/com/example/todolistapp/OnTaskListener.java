package com.example.todolistapp;

public interface OnTaskListener {
    void onItemClick(int position);
    void onDeleteClick(int position);
    void onUpdateClick(int position);
}
