package com.example.legye.wouldyourather.dataaccess;

import android.os.AsyncTask;

import com.example.legye.wouldyourather.dataaccess.entity.Answer;
import com.example.legye.wouldyourather.dataaccess.entity.Info;
import com.example.legye.wouldyourather.dataaccess.entity.Question;
import com.example.legye.wouldyourather.dataaccess.entity.QuestionsResult;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by legye on 2016. 11. 23..
 */

public class AnswerProvider{


    public JSONObject incrementAnswerCounter(int answerId) throws NullPointerException
    {
        JSONParser jParser;
        JSONObject json = new JSONObject();
        Gson gson;
        jParser = new JSONParser();

        // Getting JSON from URL
        json = jParser.postOrPutRequest(StaticResources.buildUrl("/answer_increment", answerId), JSONParser.POST, null);

        if(json == null)
        {
            throw new NullPointerException("Server not responding");
        }

        return json;
    }

    public String insertAnswer(int questionId, String text) {
        Answer answer = new Answer(0, text, questionId);

        // Getting JSON Array
        Gson gson = new Gson();
        String putData =  gson.toJson(answer, Answer.class);

        if(putData == null) {
            return null;
        }

        JSONParser jParser = new JSONParser();

        // Getting JSON from URL
        JSONObject json = jParser.postOrPutRequest(StaticResources.buildUrl("/answer"), JSONParser.PUT, putData);
        return gson.fromJson(json.toString(), Info.class).getInfo();
    }

}
