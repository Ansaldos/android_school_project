package com.example.legye.wouldyourather.dataaccess.entity;

/**
 * Created by legye on 2016. 11. 23..
 */

public class Answer {

    private int id;

    private String text;

    private int question_id;

    public Answer(int id, String text, int question_id) {
        this.id = id;
        this.text = text;
        this.question_id = question_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
