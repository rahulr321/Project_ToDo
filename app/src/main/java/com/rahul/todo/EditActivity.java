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

import static com.rahul.todo.MainActivity.TAG_Edit_TASK_DATA;

public class EditActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        updateUIFromModel();
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

                Task task = updateModelFromUI();
                if (task != null) {

                    Intent intent = new Intent();
                    intent.putExtra(TAG_Edit_TASK_DATA, task);
                    setResult(RESULT_OK, intent);
                    finish();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }



    //return task detail if it valid
    private Task updateModelFromUI() {
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
        Task t = new Task(tempTitle, tempDescription, false, "Default",
                dateToBe, timeToBe);

        Log.e("EditActivty", "Following is sent back to append : " + t.toString());


        return t;

    }

    private void updateUIFromModel() {
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            Task t = (Task) bd.get(TAG_Edit_TASK_DATA);

            EditText editTextTitle = findViewById(R.id.edittext_task_title);
            EditText editTextDescription = findViewById(R.id.edittext_task_description);
            DatePicker datePicker = findViewById(R.id.dp_date);
            TimePicker timePickerPicker = findViewById(R.id.dp_time);

            editTextDescription.setText(t.getDescription());
            editTextDescription.setText(t.getTitle());

            //need to check
            //datePicker.set(t.getDescription());
            //timePickerPicker.setText(t.getDescription());
        }
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
