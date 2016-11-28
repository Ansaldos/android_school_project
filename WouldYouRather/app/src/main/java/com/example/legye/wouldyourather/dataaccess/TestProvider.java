package com.example.legye.wouldyourather.dataaccess;

import com.android.volley.NoConnectionError;
import com.example.legye.wouldyourather.dataaccess.entity.QuestionsResult;
import com.example.legye.wouldyourather.dataaccess.entity.Test;
import com.example.legye.wouldyourather.dataaccess.entity.TestsResult;
import com.google.gson.Gson;

import junit.framework.TestResult;

import org.json.JSONObject;

import java.util.concurrent.TimeoutException;

/**
 * Created by legye on 2016. 11. 23..
 */

public class TestProvider {

    public Test getRandomTest() throws TimeoutException {
        JSONParser jParser;
        JSONObject json = new JSONObject();
        Gson gson;
        jParser = new JSONParser();

        // Getting JSON from URL
        json = jParser.getRequest(StaticResources.buildUrl("/test"), JSONParser.GET);

        if(json == null)
        {
            throw new TimeoutException("Server not responding");
        }

        gson = new Gson();

        return (gson.fromJson(json.toString(), TestsResult.class)).getData().get(0);
    }

    public void incrementTestCounter(int testId) throws TimeoutException {
        JSONParser jParser;
        JSONObject json = new JSONObject();
        jParser = new JSONParser();

        // Getting JSON from URL
        json = jParser.getRequest(StaticResources.buildUrl("/test", testId), JSONParser.GET);

        if(json == null)
        {
            throw new TimeoutException("Server not responding");
        }
    }



}
