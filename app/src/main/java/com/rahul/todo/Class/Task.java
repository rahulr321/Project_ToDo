package com.rahul.todo.Class;

import java.util.Date;

/**
 * Created by Rahul R. on 21/02/2018.
 */

public class Task extends ListItem {

    // enum Category{Default, Personal, Work, Shopping};

    /**
     * Optional
     * It is optional fields that's why they are separated,
     * when to notify the user and category divide them - prioritise.
     */
    private Date date, time;
    //Repeat private;


    // Default to Default
    private String category;

    //title, description of task
    private String title, description;

    private boolean taskComplete;

    public Task() {
        //initialise variables
        this.date = new Date();
        this.time = new Date();

        this.category = "Default";
        this.title = "";
        this.description = "";
        this.taskComplete = false;
    }

    public Task(String title, String description, boolean taskComplete, String category, Date date, Date time) {
        this.date = date;
        this.time = time;

        this.category = category;
        this.title = title;
        this.description = description;
        this.taskComplete = taskComplete;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        this.taskComplete = taskComplete;
    }


    @Override
    public boolean isSection() {
        return false;
    }


    @Override
    public int compareTo(Object o) {
        Task t = (Task) o;

        int n = 0;
        //check if
        if (time.getYear() != 1970) {
            n = this.getTime().compareTo(((Task) o).getTime());
        } else {
            n = this.getDate().compareTo(((Task) o).getDate());
        }

        /**
         * the value 0 if the argument Date is equal to this Date;
         * a value less than 0 if this Date is before the Date argument;
         * and a value greater than 0 if this Date is after the Date argument.
         */

        /*if (this.getCategory().compareTo(t.getCategory()) == 0) {
            return this.getTime().compareTo(t.getTime());
        }*/
        return 0;
    }

    @Override
    public String toString() {

        String dateTime = "";
        if (date.getYear() != 1970) {
            dateTime = date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + " , ";
            if (time.getHours() != 0 && time.getMinutes() != 0) {
                dateTime = dateTime + date.getDay() + "/" + date.getMonth() + "/" + date.getYear();

            }
        }

        return "[" + getTitle() + ", " + getDescription() + ", " + getCategory() + ", " + dateTime + "]";
    }

    public String getOtherDetail() {

        String dateTime = "";
        if (date.getYear() != 1970) {
            dateTime = " . " + date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + " . ";
            if (time.getHours() != 0 && time.getMinutes() != 0) {
                dateTime = +date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + " ";

            }
        }
        return getCategory() + dateTime;
    }
}