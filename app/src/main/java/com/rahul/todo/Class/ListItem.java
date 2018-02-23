package com.rahul.todo.Class;

/**
 * Created by Rahul R. on 22/02/2018.
 */


import java.io.Serializable;

/**
 * This class will be used as the Row in the ListView
 */
public abstract class ListItem implements Comparable, Serializable {
    public abstract boolean isSection();
}
