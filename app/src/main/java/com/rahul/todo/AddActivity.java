package com.rahul.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rahul.todo.Class.Task;

import java.util.Calendar;
import java.util.Date;

import static com.rahul.todo.MainActivity.TAG_ADD_TASK_DATA;

public class AddActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                //check for validation

                Task task = UpdateModelFromUI();
                if (task != null) {

                    Intent intent = new Intent();
                    intent.putExtra(TAG_ADD_TASK_DATA, task);
                    setResult(RESULT_OK, intent);
                    finish();
                } /*else {
                    Toast.makeText(getApplicationContext(), "Enter Validate Details",
                            Toast.LENGTH_SHORT).show();
                }*/

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    //return task detail if it valid
    private Task UpdateModelFromUI() {
        EditText editTextTitle = findViewById(R.id.edittext_task_title);
        EditText editTextDescription = findViewById(R.id.edittext_task_description);
        DatePicker datePicker = findViewById(R.id.dp_date);
        TimePicker timePickerPicker = findViewById(R.id.dp_time);

        String tempTitle = editTextTitle.getText().toString();
        String tempDescription = editTextDescription.getText().toString();

        Date dateToBe = getDateFrom(datePicker);
        Date timeToBe = getTimeFrom(dateToBe, timePickerPicker);

        //validation starts here
        if (tempTitle.length() < 5) {
            Toast.makeText(getApplicationContext(), "Title must be greater than 5 chars longs",
                    Toast.LENGTH_SHORT).show();
            return null;
        }
        /*
            Date currentTime = Calendar.getInstance().getTime();

            if (dateToBe.compareTo(currentTime) < 0) {
                Toast.makeText(getApplicationContext(), "Must be Today or Future Date only",
                        Toast.LENGTH_SHORT).show();
                return null;
            }

            //if it today and then time must be greater than now
            if (dateToBe.compareTo(currentTime) == 0) {
                if (timeToBe.compareTo(currentTime) > 0) {
                    Toast.makeText(getApplicationContext(), "Time Must be in the future",
                            Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        */
         Task t=new Task(tempTitle, tempDescription, false, "Default",
                dateToBe, timeToBe);

        Log.e("AddActivty","Following is sent back to add : "+t.toString());


        return t;

    }

    public java.util.Date getDateFrom(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public java.util.Date getTimeFrom(Date date, TimePicker timePicker) {
        int hour = timePicker.getCurrentHour();
        int mins = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonth(), date.getDate(), hour, mins);

        return calendar.getTime();
    }

}
