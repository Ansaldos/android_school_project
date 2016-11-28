package com.example.legye.wouldyourather.dataaccess.entity;

/**
 * Created by legye on 2016. 11. 23..
 */

public class TestQuestionsResult {

    private TestQuestionsResult data;

    private String info;

    public TestQuestionsResult(TestQuestionsResult data, String info) {
        this.data = data;
        this.info = info;
    }

    public TestQuestionsResult getData() {
        return data;
    }

    public void setData(TestQuestionsResult data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
