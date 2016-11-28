package com.example.legye.wouldyourather.dataaccess;

/**
 * Created by legye on 2016. 11. 20..
 */

import java.util.List;

/**
 * Include all needed API call methods
 */
public interface ITestsMethods<T> {

    List<T> getAllTest();
    void getTest();
    void insertTest();
    void updateTest();
    void deleteTest();

}
