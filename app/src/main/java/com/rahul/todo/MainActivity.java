package com.rahul.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.rahul.todo.Adaptor.TaskAdaptor;
import com.rahul.todo.Class.ListItem;
import com.rahul.todo.Class.Task;
import com.rahul.todo.Class.TaskCategory;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {

    private ListView myList;

    public final static int TAG_ADD_TASK = 1;
    public final static String TAG_ADD_TASK_DATA = "NewTask";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ListItem> mTemp = new ArrayList();

        String header = "";
        for (int i = 0; i < 20; i++) {
            Task task = new Task("SOS  " + i, "Rahul 2+ " + i, false,"Default");

            if(i%5==0){
                task.setCategory("Personal");
            }
            mTemp.add(task);
        }

        mTemp = sortAndAddCategory(mTemp);

        myList = (ListView) findViewById(R.id.listview_task);

        TaskAdaptor adapter = new TaskAdaptor(this, mTemp);
        myList.setAdapter(adapter);


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

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //Listen when user has existed add activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAG_ADD_TASK) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    Task t = (Task) b.getSerializable(TAG_ADD_TASK_DATA);
                    Toast.makeText(getApplicationContext(), "Title : " + t.getTitle(),
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "RESULT CANCELLED", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
