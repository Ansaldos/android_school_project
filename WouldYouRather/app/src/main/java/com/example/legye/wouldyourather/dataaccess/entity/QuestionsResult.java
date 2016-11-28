package com.example.legye.wouldyourather.dataaccess.entity;

import java.util.List;

/**
 * Created by legye on 2016. 11. 23..
 */

public class QuestionsResult {

    private List<Question> data;

    private String info;

    public QuestionsResult(List<Question> data, String info) {
        this.data = data;
        this.info = info;
    }

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
