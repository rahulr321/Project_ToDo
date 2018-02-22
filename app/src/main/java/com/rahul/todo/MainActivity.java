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
import com.rahul.todo.Class.Task;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView myList;

    public final static int TAG_ADD_TASK = 1;
    public final static String TAG_ADD_TASK_DATA = "NewTask";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Task> mListTask = new ArrayList();

        //for simplicity we will add the same name for 20 times to populate the list view
        for (int i = 0; i < 20; i++) {
            Task task = new Task("Savita  " + i, "Rahul 2+ " + i, false);
            mListTask.add(task);
        }

        //relate the listView from java to the one created in xml
        myList = findViewById(R.id.listview_task);

        //show the ListView on the screen
        // The adapter MyCustomAdapter is responsible for maintaining the data backing this list and for producing
        // a view to represent an item in that data set.
        myList.setAdapter(new TaskAdaptor(this, mListTask));


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
                } else  {
                    Toast.makeText(getApplicationContext(), "RESULT CANCELLED", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
