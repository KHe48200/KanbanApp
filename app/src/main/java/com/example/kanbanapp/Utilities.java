package com.example.kanbanapp;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Utilities {
    public static final String FILE_EXTENSION = ".bin";

    public static boolean saveProject(Context context, Project project) {
        String fileName = String.valueOf(project.getDateTime()) + FILE_EXTENSION;
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(project);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Project> getAllSavedProjects(Context context){
        ArrayList<Project> projects = new ArrayList<>();
        File filesDir = context.getFilesDir();
        ArrayList<String> projectFiles = new ArrayList<>();

        for (String file : filesDir.list()) {
            if (file.endsWith(FILE_EXTENSION)) {
                projectFiles.add(file);
            }
        }
        FileInputStream fis;
        ObjectInputStream ois;

        for(int i = 0; i < projectFiles.size(); i++) {
            try {
                fis = context.openFileInput(projectFiles.get(i));
                ois = new ObjectInputStream(fis);
                projects.add((Project) ois.readObject());
                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
        return projects;
    }

    public static Project getProjectByName(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        Project project;

        if(file.exists()) {
            FileInputStream fis;
            ObjectInputStream ois;
            try {
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                project = (Project) ois.readObject();
                fis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            return project;
        }
        return null;
    }

    public static boolean saveTask(Context context, Project project, Task task) {
        ArrayList<Task> allTasks = project.getTasks();
        if(allTasks.contains(task)){
            int i = allTasks.indexOf(task);
            allTasks.set(i, task);
        } else {
            allTasks.add(task);
        }
        return false;
    }


    public static ArrayList<Task> getAllTasksByStatus(Context context, ArrayList<Task> allSavedTasks, String status) {
        ArrayList<Task> tasksByStatus = new ArrayList<>();

        for (int i = 0; i < allSavedTasks.size(); i++) {
            if (allSavedTasks.get(i).getStatus().equals(status)) {
                Task task = allSavedTasks.get(i);
                tasksByStatus.add(task);
            }
        }
        return tasksByStatus;
    }

    public static Task getTaskByName(Context context, ArrayList<Task> tasks, String fileName) {
        for (int i = 0; i <tasks.size(); i++) {
            String dateTime = String.valueOf(tasks.get(i).getDateTime());
            if(fileName.equals(dateTime)){
                return tasks.get(i);
            }
        }
        return null;
    }
    public static void deleteProject(Context context, String fileName) {
        //Kansio missä tiedosto on
        File dir = context.getFilesDir();
        //Poistettavan tiedoston koko polku
        File file = new File(dir, fileName);

        //Jos sen niminen on olemassa poistetaan se
        if(file.exists()){
            file.delete();
        }
    }
}
