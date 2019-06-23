package com.example.loginandregisteruser;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Tasks {

private String description;
private String done;
private String title;
private String id;
    private Context current_context;
    private static Tasks sTask;

    final static List<Tasks> tasksList = new ArrayList<Tasks>();

    private Tasks(Context context){ current_context = context.getApplicationContext(); }

    public static Tasks get(Context context){
        if(sTask == null){
            sTask = new Tasks(context);
        }
        return sTask;
    }

    public Tasks(String description, String done, String title, String id) {
        this.description = description;
        this.done = done;
        this.title = title;
        this.id = id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void addToTaskList(Tasks tasks){
        tasksList.add(tasks);
    }

    public static List<Tasks> getAllTasks(){
        return tasksList;
    }
}
