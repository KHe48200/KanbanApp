package com.example.kanbanapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kanbanapp.MainActivity;
import com.example.kanbanapp.R;
import com.example.kanbanapp.Task;

import java.util.List;

//TaskAdapter perii atribuutit ja metodit ArrayAdapterilta
public class TaskAdapter extends ArrayAdapter<Task> {

    //Constructor
    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Task> tasks) {
        super(context, resource, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Asetetaan Viewlle contexti ja layoutiksi item_task.xml
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, null);
        }

        Task task = getItem(position);

        //Jos task ei null asetetaan item_task.xml layoutin TextViewlle tekstiksi getTitlen avulla taskin otsikko
        if(task != null){
            TextView title = convertView.findViewById(R.id.item_task_title);

            title.setText(task.getTitle());

            final LinearLayout linearLayout = convertView.findViewById(R.id.item_task_bg);

            /**convertView.findViewById(R.id.buttonWhite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
                }});
            convertView.findViewById(R.id.buttonYellow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setBackgroundColor(Color.parseColor("#feff9c"));
                }});
            convertView.findViewById(R.id.buttonCyan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setBackgroundColor(Color.parseColor("#7afcff"));
                }});
            convertView.findViewById(R.id.buttonPink).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linearLayout.setBackgroundColor(Color.parseColor("#ff7eb9"));
                }});*/
        }
        return convertView;
    }
}