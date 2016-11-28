package com.example.legye.wouldyourather.dataaccess.entity;

/**
 * Created by legye on 2016. 11. 23..
 */

public class TestQuestion {

    private int id;

    private int question_id;

    private int answer_id;

    public TestQuestion(int id, int answer_id, int question_id) {
        this.id = id;
        this.answer_id = answer_id;
        this.question_id = question_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }
}
