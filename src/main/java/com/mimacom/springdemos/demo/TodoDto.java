package com.mimacom.springdemos.demo;

import java.util.Objects;

public class TodoDto {

    private long userId;
    private long id;
    private String title;
    private boolean completed;

    public TodoDto(long userId, long id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public TodoDto() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoDto todoDto = (TodoDto) o;
        return userId == todoDto.userId &&
                id == todoDto.id &&
                completed == todoDto.completed &&
                Objects.equals(title, todoDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, completed);
    }
}
