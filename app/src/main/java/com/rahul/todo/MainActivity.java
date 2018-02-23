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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

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

    private static int lastPositionClick = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.listview_task);

        mContext = getApplicationContext();
        mListTask = new ArrayList<Task>();

        for (int i = 0; i < 20; i++) {
            Task task = new Task("KDDK  " + i, "Rahul 2+ " + i, false, "Default", new Date(), new Date());
            mListTask.add(task);
        }
        adapter = new TaskAdaptor(this, mListTask);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(adapter);

        loadDateFromFile();

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAG_ADD_TASK:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    if (b != null) {
                        //item received successfully

                        Task t = (Task) b.getSerializable(TAG_ADD_TASK_DATA);
                        mListTask.add(t);
                        adapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(mContext, "CANCELLED", Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            case TAG_EDIT_TASK:

                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    if (b != null) {
                        //item received successfully

                        Task t = (Task) b.getSerializable(TAG_ADD_TASK_DATA);
                        mListTask.add(lastPositionClick, t);
                        adapter.notifyDataSetChanged();
                        lastPositionClick = -1;
                    } else {
                        Toast.makeText(mContext, "CANCELLED", Toast.LENGTH_SHORT).show();

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

                        break;
                    case 1:

                        Task task = (Task) mListTask.get(position);

                        Intent intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra(TAG_Edit_TASK_DATA, task);
                        lastPositionClick = position;
                        mListTask.remove(position);
                        startActivity(intent);

                        break;
                    case 2:
                        mListTask.remove(position);
                        adapter.notifyDataSetChanged();
                }
            }
        });
        builder.show();
    }
}
