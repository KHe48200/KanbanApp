package com.example.kanbanapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TaskActivity extends AppCompatActivity {
    private EditText mEtTitle;
    private EditText mEtContent;
    private EditText mEtStatus;
    private String mTaskDateTime;
    private Task mLoadedTask;
    private String mTabName;
    private String mProjectName;
    private Project mProject;
    private int mTabPosition;
    private boolean deleteUsed;
    private int indexOfmLoadedTask;
    private String mHelpMessage;

    private void saveTask() {
        Task task;
        if(mLoadedTask == null && mEtStatus.getText().toString().equals("")){
            task = new Task(System.currentTimeMillis(), mEtTitle.getText().toString(), mEtContent.getText().toString(), mTabName);
            mProject.getTasks().add(task);
        } else {
            task = new Task(mLoadedTask.getDateTime(), mEtTitle.getText().toString(), mEtContent.getText().toString(), mEtStatus.getText().toString());
            mProject.getTasks().set(indexOfmLoadedTask, task);
        }

        /**if(Utilities.saveTask(this, MainActivity.project, task)) {
            Toast.makeText(this, "Task saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cannot save the task. Please make sure you have enough space on your device", Toast.LENGTH_SHORT).show();
        }*/
        startMainActivity();
    }

    private void deleteTask() {
        if(mLoadedTask == null) {
            startMainActivity();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Delete task")
                    .setMessage("Are you sure you want to delete " + mEtTitle.getText().toString() + "?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mProject.getTasks().remove(mLoadedTask);
                            Toast.makeText(getApplicationContext(), mEtTitle.getText().toString() + " deleted!", Toast.LENGTH_SHORT).show();
                            startMainActivity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .setCancelable(false);
            dialog.show();
        }
        deleteUsed = true;
    }
    private void deleteDoneTask() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Delete task")
                .setMessage("Task \"" + mEtTitle.getText().toString().trim() + "\" is done. Do you want to delete it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProject.getTasks().remove(mLoadedTask);
                        Toast.makeText(getApplicationContext(), mEtTitle.getText().toString() + " deleted!", Toast.LENGTH_SHORT).show();
                        startMainActivity();
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);
        dialog.create().show();
        deleteUsed = true;
    }

    public void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("PROJECT_FILE", mProjectName);
        intent.putExtra("TAB_POSITION", mTabPosition);
        Utilities.saveProject(getApplicationContext(), mProject);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mEtTitle = findViewById(R.id.taskEditorTitle);
        mEtContent = findViewById(R.id.taskEditorContent);
        mEtStatus = findViewById(R.id.taskEditorStatus);
        mTabName = getIntent().getStringExtra("TAB_NAME");
        mProjectName = getIntent().getStringExtra("PROJECT_FILE");
        mTaskDateTime = getIntent().getStringExtra("TASK_FILE");
        mProject = Utilities.getProjectByName(this, mProjectName);
        switch (mTabName) {
            case "TODO":
                mTabPosition = 0;
                break;
            case "DOING":
                mTabPosition = 1;
                break;
            case "DONE":
                mTabPosition = 2;
                break;
        }

        if(mTaskDateTime != null && !mTaskDateTime.isEmpty()) {
            mLoadedTask = Utilities.getTaskByName(this, mProject.getTasks(), mTaskDateTime);
            if(mLoadedTask != null) {
                indexOfmLoadedTask = mProject.getTasks().indexOf(mLoadedTask);
                mEtTitle.setText(mLoadedTask.getTitle());
                mEtContent.setText(mLoadedTask.getContent());
                mEtStatus.setText(mLoadedTask.getStatus());
            }
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(mEtStatus.getText().toString()) {
                    case "TODO":
                        mEtStatus.setText(getString(R.string.DOING));
                        startMainActivity();
                        break;
                    case "DOING":
                        mEtStatus.setText(getString(R.string.DONE));
                        startMainActivity();
                        break;
                    case "DONE":
                        deleteDoneTask();
                        break;
                }
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                statusChangeDialog();
                return false;
            }
        });
    }

    private void statusChangeDialog() {
        if (mLoadedTask != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Change task status to:")
                    .setItems(new CharSequence[]
                                    {"TODO", "DOING", "DONE"},
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position of the selected item
                                    switch (which) {
                                        case 0:
                                            mEtStatus.setText(getString(R.string.TODO));
                                            startMainActivity();
                                            break;
                                        case 1:
                                            mEtStatus.setText(getString(R.string.DOING));
                                            startMainActivity();
                                            break;
                                        case 2:
                                            mEtStatus.setText(getString(R.string.DONE));
                                            startMainActivity();
                                            break;
                                    }
                                }
                            });
            dialog.create().show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task, menu);
        if (mLoadedTask == null)
            getMenuInflater().inflate(R.menu.menu_task_save, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_task_delete:
                deleteTask();
            case R.id.action_task_help:
                helpTask();
            case R.id.action_task_save:
                startMainActivity();
        }
        return true;
    }

    private void helpTask() {
        mHelpMessage = "Click the check button to change task status";
        if (mLoadedTask != null) {
            switch (mTabName) {
                case "TODO":
                    mHelpMessage = "Click the check button to change task status to DOING";
                    break;
                case "DOING":
                    mHelpMessage = "Click the check button to change task status to DONE";
                    break;
                case "DONE":
                    mHelpMessage = "Click the check button to delete task";
                    break;
            }
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Help")
                .setMessage(mHelpMessage+"\n\nLong click the button to open a dialog where you can select desired status.")
                .setCancelable(true);
        dialog.show();
    }

    protected void onPause() {
        super.onPause();
        if(!deleteUsed) {
            if(!mEtTitle.getText().toString().trim().isEmpty()){
                saveTask();
            } else {
                Toast.makeText(getApplicationContext(), "Enter a title to save note", Toast.LENGTH_LONG).show();
                startMainActivity();
            }
        }
        finish();
    }
}
