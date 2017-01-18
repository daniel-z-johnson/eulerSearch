package com.self.exercise.search.euler.model;

/**
 * Created by daniel on 1/12/17.
 */
public class Problem {
    private int id;
    private String title;
    private String body;

    public Problem() {
    }

    public Problem(int id) {
        this.id = id;
    }

    public Problem(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
