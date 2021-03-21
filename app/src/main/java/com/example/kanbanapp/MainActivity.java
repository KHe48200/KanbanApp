package com.example.kanbanapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.kanbanapp.Tabs.TodoTab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kanbanapp.Adapters.SectionsPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Task> allTasks;
    public static Project project;
    public static String fileName;
    public static String taskColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        final ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);
        viewPager.setCurrentItem(getIntent().getIntExtra("TAB_POSITION", 0));
        fileName = getIntent().getStringExtra("PROJECT_FILE");
        project = Utilities.getProjectByName(this, fileName);
        allTasks = project.getTasks();
        if(allTasks.isEmpty()) {
            Toast.makeText(this, "No tasks to show yet!", Toast.LENGTH_SHORT).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tab = sectionsPagerAdapter.getPageTitle(viewPager.getCurrentItem()).toString();
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("PROJECT_FILE", fileName);
                intent.putExtra("TAB_NAME", tab);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_main_help){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                    .setTitle("Help")
                    .setMessage("Click add button to create a new task.\n\nClick task name to select a task.\n\nLong click task name to delete it.")
                    .setCancelable(true);
            dialog.show();
        }
        return true;
    }

    /**public void setBgWhite(View view) {
        taskColor = "white";
        //findViewById(R.id.item_task_bg).setBackgroundColor(getColor(R.color.colorWhite));
    }
    public void setBgYellow(View view) {
        taskColor = "yellow";
        //findViewById(R.id.item_task_bg).setBackgroundColor(getColor(R.color.colorYellow));
    }
    public void setBgCyan(View view) {
        taskColor = "cyan";
        //findViewById(R.id.item_task_bg).setBackgroundColor(getColor(R.color.colorCyan));
    }
    public void setBgPink(View view) {
        taskColor = "pink";
        //findViewById(R.id.item_task_bg).setBackgroundColor(getColor(R.color.colorPink));
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}