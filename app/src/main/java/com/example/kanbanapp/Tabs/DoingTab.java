package com.example.kanbanapp.Tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.kanbanapp.Adapters.TaskAdapter;
import com.example.kanbanapp.MainActivity;
import com.example.kanbanapp.R;
import com.example.kanbanapp.Task;
import com.example.kanbanapp.TaskActivity;
import com.example.kanbanapp.Utilities;

import java.util.ArrayList;

/**Kts. TodoTab.java kommentit. Periaatteessa sama paitsi Utilities.getAllTasksByStatus (rivi 38) metodille annetaan eri status parametri*/
public class DoingTab extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.doing_tab, container, false);

        final ListView listView = rootView.findViewById(R.id.doingListView);
        listView.setAdapter(null);

        ArrayList<Task> doingTasks = Utilities.getAllTasksByStatus(getContext(), MainActivity.allTasks, "DOING");

        TaskAdapter taskAdapter = new TaskAdapter(getContext(), R.layout.item_task, doingTasks);
        listView.setAdapter(taskAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String taskDateTime = String.valueOf(((Task)listView.getItemAtPosition(position)).getDateTime());
                String tabName = "DOING";

                Intent viewTaskIntent = new Intent(getContext(), TaskActivity.class);
                viewTaskIntent.putExtra("PROJECT_FILE", MainActivity.fileName);
                viewTaskIntent.putExtra("TASK_FILE", taskDateTime);
                viewTaskIntent.putExtra("TAB_NAME", tabName);
                startActivity(viewTaskIntent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final String taskTitle = ((Task) listView.getItemAtPosition(position)).getTitle();
                final Task task = ((Task) listView.getItemAtPosition(position));
                final int tabPosition = 1;

                //AlertDialogi jossa kysytään halutaanko taski poistaa
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                        .setTitle("Delete task")
                        .setMessage("Are you sure you want to delete task \"" + taskTitle.trim() + "\"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.allTasks.remove(task);
                                Utilities.saveProject(getContext(), MainActivity.project);
                                Toast.makeText(getContext(),  taskTitle + " deleted!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                intent.putExtra("PROJECT_FILE", MainActivity.fileName);
                                intent.putExtra("TAB_POSITION", tabPosition);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .setCancelable(false);
                dialog.show();
                return true;
            }
        });

        return rootView;
    }
}
