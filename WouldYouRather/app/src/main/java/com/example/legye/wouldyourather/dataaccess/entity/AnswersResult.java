package com.example.legye.wouldyourather.dataaccess.entity;

import java.util.List;

/**
 * Created by legye on 2016. 11. 23..
 */

public class AnswersResult {

    private List<Answer> data;

    private String info;

    public AnswersResult(List<Answer> data, String info) {
        this.data = data;
        this.info = info;
    }

    public List<Answer> getData() {
        return data;
    }

    public void setData(List<Answer> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
