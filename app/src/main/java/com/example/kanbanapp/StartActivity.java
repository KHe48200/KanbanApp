package com.example.kanbanapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.kanbanapp.Adapters.ProjectAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    private ListView mListViewProjects;
    private String fileName;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mListViewProjects = findViewById(R.id.start_listview);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProjectActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListViewProjects.setAdapter(null);
        ArrayList<Project> projects = Utilities.getAllSavedProjects(this);
        if(projects == null || projects.size() == 0) {
            Toast.makeText(this, "No projects to show yet!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProjectAdapter pa = new ProjectAdapter(this, R.layout.item_project, projects);
            mListViewProjects.setAdapter(pa);
        }

        mListViewProjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fileName = ((Project)mListViewProjects.getItemAtPosition(position)).getDateTime() + Utilities.FILE_EXTENSION;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("PROJECT_FILE", fileName);
                startActivity(intent);
            }
        });

        mListViewProjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                fileName = ((Project)mListViewProjects.getItemAtPosition(position)).getDateTime() + Utilities.FILE_EXTENSION;
                projectName = ((Project) mListViewProjects.getItemAtPosition(position)).getName();
                deleteDialog();
                return true;
            }
        });
    }

    private void deleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle("Delete project")
                .setMessage("Are you sure you want to delete task \"" + projectName.trim() + "\"?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utilities.deleteProject(getApplicationContext(), fileName);
                        Toast.makeText(getApplicationContext(),  projectName + " deleted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .setCancelable(false);
        dialog.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Info")
                    .setMessage("KanbanApp made by Kalle Heini \n\nAdd icon made by srip from www.flaticon.com \n\nCheck icon made by Freepik from www.flaticon.com \n\nNotes icon made by Smashicons from www.flaticon.com")
                    .setCancelable(true);
            dialog.show();
        }
        if (id == R.id.action_start_help) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Help")
                    .setMessage("Click add button to create a new project.\n\nClick project name to select a project.\n\nLong click project name to delete it.")
                    .setCancelable(true);
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
