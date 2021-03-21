package com.example.kanbanapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kanbanapp.Project;
import com.example.kanbanapp.R;

import java.util.List;

public class ProjectAdapter extends ArrayAdapter<Project> {

    public ProjectAdapter(@NonNull Context context, int resource, @NonNull List<Project> projects) {
        super(context, resource, projects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_project, null);
        }

        Project project = getItem(position);

        if(project != null) {
            TextView name = (TextView) convertView.findViewById(R.id.item_project_name);

            name.setText(project.getName());
        }
        return convertView;
    }
}
