package com.example.legye.wouldyourather.dataaccess;

import com.example.legye.wouldyourather.dataaccess.entity.Answer;
import com.example.legye.wouldyourather.dataaccess.entity.AnswersResult;
import com.example.legye.wouldyourather.dataaccess.entity.Info;
import com.example.legye.wouldyourather.dataaccess.entity.Question;
import com.example.legye.wouldyourather.dataaccess.entity.QuestionsResult;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by legye on 2016. 11. 23..
 */

public class QuestionProvider {

    /**
     * Get questions (10)
     * @param testId Test's ID
     * @return Question list
     */
    public List<Question> getQuestions(int testId)
    {
        JSONParser jParser = new JSONParser();

        // Getting JSON from URL
        JSONObject json = jParser.getRequest(StaticResources.buildUrl("/testquestions", testId), JSONParser.GET);

        if(json != null)
        {
            // Getting JSON Array
            Gson gson = new Gson();
            return (gson.fromJson(json.toString(), QuestionsResult.class)).getData();
        }

        return new ArrayList<>();
    }

    /**
     * Get question's answers
     * @param questionId Question ID
     * @return Answer list
     */
    public List<Answer> getQuestionAnswers(int questionId)
    {
        JSONParser jParser = new JSONParser();
        String getAnswersUrl = StaticResources.buildUrl("/questionanswers", questionId);

        // Getting JSON from URL
        JSONObject json = jParser.getRequest(getAnswersUrl, JSONParser.GET);

        if(json != null)
        {
            // Getting JSON Array
            Gson gson = new Gson();
            return (gson.fromJson(json.toString(), AnswersResult.class)).getData();
        }

        return new ArrayList<>();
    }

    public String insertQuestion(String text) {
        Question question = new Question(0, text);

        // Getting JSON Array
        Gson gson = new Gson();
        String putData =  gson.toJson(question, Question.class);

        if(putData == null) {
            return null;
        }

        JSONParser jParser = new JSONParser();

        // Getting JSON from URL
        JSONObject json = jParser.postOrPutRequest(StaticResources.buildUrl("/question"), JSONParser.PUT, putData);
        return gson.fromJson(json.toString(), Info.class).getInfo();
    }
}
