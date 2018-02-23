package com.rahul.todo.Class;

/**
 * Created by Rahul R. on 22/02/2018.
 */

/**
 * This class will be used as the Category in the ListView
 */
public class TaskCategory extends ListItem {

    public TaskCategory(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    private  String categoryTitle;


    @Override
    public boolean isSection() {
        return true;
    }

    @Override
    public int compareTo( Object o) {
        TaskCategory t= (TaskCategory) o;
        return this.getCategoryTitle().compareTo(t.getCategoryTitle());
    }
}
