package com.example.kanbanapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Project implements Serializable {
    private long mDateTime;
    private String mName;
    private ArrayList<Task> mTasks;

    public Project(long mDateTime, String mName, ArrayList<Task> mTasks) {
        this.mDateTime = mDateTime;
        this.mName = mName;
        this.mTasks = mTasks;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    public void setTasks(ArrayList<Task> mTasks) {
        this.mTasks = mTasks;
    }
}
