package com.example.kanbanapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ProjectActivity extends AppCompatActivity {
    private EditText mEtName;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEtName = findViewById(R.id.projectEditorName);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProject();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("PROJECT_FILE", project.getDateTime()+Utilities.FILE_EXTENSION);
                startActivity(intent);
            }
        });
    }

    private void saveProject() {
        ArrayList<Task> tasks = new ArrayList<>();
        project = new Project(System.currentTimeMillis(), mEtName.getText().toString(), tasks);
        if(Utilities.saveProject(this, project)){
            Toast.makeText(this, "Project saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cannot save the note. Please make sure you have enough space on your device", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
