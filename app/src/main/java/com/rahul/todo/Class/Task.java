package com.rahul.todo.Class;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * Created by Rahul R. on 21/02/2018.
 */

public class Task implements Comparable<Task>, Serializable {

    // enum Category{Default, Personal, Work, Shopping};

    /**
     * Optional
     * It is optional fields that's why they are separated,
     * when to notify the user and category divide them - prioritise.
     *
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

    public Task(String title, String description, boolean taskComplete) {
        this.date = new Date();
        this.time = new Date();

        this.category = "Default";
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
    public int compareTo( Task o) {
        Date compareQuantity = o.getDate();

        //ascending order
        //return getDate() - compareQuantity;

        //descending order
        //return compareQuantity - this.quantity;
        return 0;
    }
}
