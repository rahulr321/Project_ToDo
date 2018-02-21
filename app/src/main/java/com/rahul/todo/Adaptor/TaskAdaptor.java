package com.rahul.todo.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rahul.todo.Class.Task;
import com.rahul.todo.R;

import java.util.ArrayList;

/**
 * Created by Droid on 21/02/2018.
 */

public class TaskAdaptor extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Task> mTask;

    //Constructor used to initialise the Adaptor for the ListView
    public TaskAdaptor(Context context, ArrayList<Task> items) {
        mContext = context;
        mTask = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
    *  Let ListView knows how many items are in the data source
     */

    @Override
    public int getCount() {
        return mTask.size();
    }

    @Override
    public Object getItem(int position) {
        //could put if statement bound checking
        return mTask.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_task, parent, false);
        return rowView;
    }
}
