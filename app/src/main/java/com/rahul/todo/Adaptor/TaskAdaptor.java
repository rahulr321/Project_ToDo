package com.rahul.todo.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahul.todo.Class.Task;
import com.rahul.todo.R;

import java.util.ArrayList;

/**
 * Created by Rahul R. on 21/02/2018.
 */

public class TaskAdaptor extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    //Read: https://stackoverflow.com/questions/322715/when-to-use-linkedlist-over-arraylist
    private ArrayList<Task> mListTask;

    //Constructor used to initialise the Adaptor for the ListView
    public TaskAdaptor(Context context, ArrayList<Task> items) {
        mContext = context;
        mListTask = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Let ListView knows how many items are in the data source
    @Override
    public int getCount() {
        return mListTask.size();
    }

    @Override
    public Object getItem(int position) {
        //could put if statement for bound checking
        return mListTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //creating a TaskViewHolder reference
        TaskViewHolder taskViewHolder;

        //checking to see if convertView is null or not, if not null reuse it
        if (convertView == null) {
            // inflate the layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_task, parent, false);

            //initialise the TaskViewHolder with the inflated view
            taskViewHolder = new TaskViewHolder(convertView);

            //store the tag with view
            convertView.setTag(taskViewHolder);
        } else {
            /**
             * Prevents program from using findViewById() every time user scroll and
             * get stored view holder object using getTag
             */
            taskViewHolder = (TaskViewHolder) convertView.getTag();
        }

        //Create  reference  variable to hold data
        Task task = (Task) mListTask.get(position);

        if (task != null) {

            //set the item name on the  taskViewHolder
            taskViewHolder.textViewTaskTitle.setText(task.getTitle());
            taskViewHolder.textViewTaskDescription.setText(task.getDescription());

            //may changed
            if (task.isTaskComplete()) {
                taskViewHolder.checkBoxTaskComplete.setChecked(true);
            } else {
                taskViewHolder.checkBoxTaskComplete.setChecked(false);
            }
            taskViewHolder.textviewOtherDetails.setText(task.getOtherDetail());

        }

        return convertView;
    }


    /**
     * Using View Holder Design Pattern to reduce performance
     * impact when scrolling through ListView
     */

    private class TaskViewHolder {
        protected TextView textViewTaskTitle, textViewTaskDescription, textviewOtherDetails;
        protected CheckBox checkBoxTaskComplete;
        protected LinearLayout linearLayout;


        public TaskViewHolder(View view) {
            linearLayout = view.findViewById(R.id.layout_item_task);

            textViewTaskTitle = view.findViewById(R.id.textview_task_title);
            textViewTaskDescription = view.findViewById(R.id.textview_task_description);
            checkBoxTaskComplete = view.findViewById(R.id.checkbox_task_complete);

            textviewOtherDetails = view.findViewById(R.id.textview_other_details);
        }
    }
}
