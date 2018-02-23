package com.rahul.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rahul.todo.Adaptor.TaskAdaptor;
import com.rahul.todo.Class.ListItem;
import com.rahul.todo.Class.Task;
import com.rahul.todo.Class.TaskCategory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {


    public final static int TAG_ADD_TASK = 1;
    public final static int TAG_EDIT_TASK = 2;
    public final static String TAG_ADD_TASK_DATA = "NewTask";
    public final static String TAG_Edit_TASK_DATA = "EditTask";
    public final static String FILE_NAME = "data.txt";

    private ListView mListView;
    private Context mContext;
    private ArrayList<Task> mListTask;
    private TaskAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listview_task);
        mListView.setOnItemClickListener(this);

        mContext = getApplicationContext();
        mListTask = new ArrayList();


        adapter = new TaskAdaptor(this, mListTask);
        mListView.setAdapter(adapter);

        loadDateFromFile();

    }

    private ArrayList sortAndAddCategory(ArrayList<ListItem> itemList) {

        ArrayList<ListItem> tempList = new ArrayList();
        //First we sort the array
        Collections.sort(itemList);

        //Loops through the list and add a section before each start
        String header = "";
        for (int i = 0; i < itemList.size(); i++) {

            Task task = (Task) itemList.get(i);
            if (header != task.getCategory()) {
                header = task.getCategory();

                TaskCategory tc = new TaskCategory(task.getCategory());
                tempList.add(tc);
            }
            tempList.add(task);
        }

        return tempList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, TAG_ADD_TASK);
                return true;
            case R.id.action_open:
                loadDateFromFile();
                return true;
            case R.id.action_save:
                saveDataToFile();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //Listen when user has existed add activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAG_ADD_TASK:
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    //item received successfully

                    Task t = (Task) b.getSerializable(TAG_ADD_TASK_DATA);
                    mListTask.add(t);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(mContext, "CANCELLED", Toast.LENGTH_LONG).show();

                }
            }
            break;
            case TAG_EDIT_TASK:

                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    if (b != null) {
                        //item received successfully

                        Task t = (Task) b.getSerializable(TAG_ADD_TASK_DATA);
                        mListTask.add(t);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(mContext, "CANCELLED", Toast.LENGTH_LONG).show();

                    }
                }



        }
    }


    private void saveDataToFile() {
        try {

            FileOutputStream fos = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            ArrayList<ListItem> temp = new ArrayList<>();
            for (int x = 0; x < mListTask.size(); x++) {
                if (mListTask.get(x).isSection() == false) {
                    temp.add(mListTask.get(x));
                }
            }


            //write the list object to file using serializable
            os.writeObject(mListTask);
            os.close();
            fos.close();

            Toast.makeText(mContext, "File Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadDateFromFile() {
        try {

            FileInputStream fis = mContext.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            mListTask = (ArrayList<Task>) is.readObject();
            is.close();
            fis.close();


            //sort the data- not needed as data was already sorted
            //mListTask = sortAndAddCategory(mListTask);
            if (mListTask.size() == 0) {
                Toast.makeText(mContext, "File Empty", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "File Opened", Toast.LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, "Create a file", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        //only listen task not category
        if (mListTask.get(position) instanceof Task) {

            final String option[] = new String[]{"Toggle Task ", "Edit", "Delete "};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("What would like to do");
            builder.setItems(option, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int userOptionChosen) {

                    //user chosen option from
                    switch (userOptionChosen) {
                        case 0:
                            //task completed
                            Task t = (Task) mListTask.get(position);
                            boolean ok;
                            if (t.isTaskComplete()) {
                                ok = false;
                            } else {
                                ok = false;
                            }
                            t.setTaskComplete(ok);
                            mListTask.add(t);
                            adapter.notifyDataSetChanged();

                            return;
                    }
                }
            });
            builder.show();
        }
    }


   /* private void ssortAndAddTask(Task newItem) {

        if (newItem != null) {
            //create new collection for all the task and category;
            ArrayList<ListItem> tempAllTaskAndCategory = new ArrayList();
            tempAllTaskAndCategory.addAll(mListTask);
            tempAllTaskAndCategory.add(newItem);

            //only contain task
            ArrayList<ListItem> unCategoryList = new ArrayList();
            //new list for adaptor with sorted and category
            ArrayList<ListItem> tempList = new ArrayList();

            //partition big array into smaller
            ArrayList<ListItem> tempOverDueList = new ArrayList();
            ArrayList<ListItem> tempTodayList = new ArrayList();
            ArrayList<ListItem> tempFollowingDaysList = new ArrayList();
            ArrayList<ListItem> tempNoDateList = new ArrayList();


            Date currentTime = Calendar.getInstance().getTime();

            //could have just remove the list item
            for (int x = 0; x < tempAllTaskAndCategory.size(); x++) {
                if (tempAllTaskAndCategory.get(x).isSection() == false) {
                    unCategoryList.add(tempAllTaskAndCategory.get(x));
                }

            }


            for (int i = 0; i < unCategoryList.size(); i++) {
                Task task = (Task) unCategoryList.get(i);

                //check task date and time and divide them into their respective list and then join;
                *//***
     * the value 0 if the argument Date is equal to this Date;
     * a value less than 0 if this Date is before the Date argument;
     * and a value greater than 0 if this Date is after the Date argument.
     *//*

                if (task.getDate().getYear() == 1970) {
                    tempNoDateList.add(task);
                } else if (task.getDate().compareTo(currentTime) == 0) {
                    tempTodayList.add(task);
                } else if (task.getDate().compareTo(currentTime) > 0) {
                    tempFollowingDaysList.add(task);
                } else if (task.getDate().compareTo(currentTime) < 0) {
                    tempOverDueList.add(task);
                }

            }

            //sort all partition list
            Collections.sort(tempOverDueList);
            Collections.sort(tempTodayList);
            Collections.sort(tempFollowingDaysList);
            Collections.sort(tempNoDateList);

            //Add all partition list in order, to display
            TaskCategory tc;
            if (tempOverDueList.size() > 0) {

                tc = new TaskCategory("Over Due");
                tempList.add(tc);
                tempList.addAll(tempOverDueList);
                Log.e("MainActivty:", "OverDue Task Collection added");

            }

            if (tempTodayList.size() > 0) {

                tc = new TaskCategory("Today");
                tempList.add(tc);
                tempList.addAll(tempTodayList);
                Log.e("MainActivty:", "Upcoming Days Task Collection added");

            }
            if (tempFollowingDaysList.size() > 0) {

                tc = new TaskCategory("Upcoming Days");
                tempList.add(tc);
                tempList.addAll(tempFollowingDaysList);
                Log.e("MainActivty:", "Upcoming Days Task Collection added");

            }
            if (tempNoDateList.size() > 0) {

                tc = new TaskCategory("No Date");
                tempList.add(tc);
                tempList.addAll(tempNoDateList);

                Log.e("MainActivty:", "No Date Task Collection added");
            }

            //
            mListTask = tempList;
        }
        adapter.notifyDataSetChanged();

    }*/

}
