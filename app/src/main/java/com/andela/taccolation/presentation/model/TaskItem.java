package com.andela.taccolation.presentation.model;

import java.util.Objects;

public class TaskItem {
    int position;
    String title;

    public TaskItem() {
    }

    public TaskItem(int position, String title) {
        this.position = position;
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskItem)) return false;
        TaskItem taskItem = (TaskItem) o;
        return getPosition() == taskItem.getPosition() &&
                getTitle().equals(taskItem.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getTitle());
    }
}
