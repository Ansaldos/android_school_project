package com.example.legye.wouldyourather.dataaccess.entity;

import java.util.List;

/**
 * Created by legye on 2016. 11. 20..
 */

public class TestsResult {

    private List<Test> data;

    private String info;

    public TestsResult(List<Test> data, String info) {
        this.data = data;
        this.info = info;
    }

    public List<Test> getData() {
        return data;
    }

    public void setData(List<Test> data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
