package com.armasconi.taskmaster.models;

public class MyTask {
    private String title;
    private String body;
    private Boolean state;

    public MyTask(String title, String body, Boolean state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Boolean getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
