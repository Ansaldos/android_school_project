package com.example.legye.wouldyourather.dataaccess;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by legye on 2016. 11. 21..
 */

public class JSONParser {

    public static String GET = "GET";
    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String DELETE = "DELETE";

    private final String LOG_TAG;
    private InputStream pureResponse = null;
    private JSONObject jsonObject = null;
    private String jsonString = "";


    // constructor
    public JSONParser()
    {
        LOG_TAG = this.getClass().toString();
    }

    public JSONObject getRequest(String url, String method)
    {
        String httpMethod = "";
        if(method.equals(GET))
        {
            httpMethod = GET;
        }
        else
        {
            Log.e(LOG_TAG, "Bad request type, request method is " + method);
        }


        HttpURLConnection connection = null;

        URL apiUrl;
        try {
            apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(StaticResources.HTTP_TIMEOUT);

            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                pureResponse = connection.getInputStream();
                Log.v(LOG_TAG, "Http connection OK");
            }
        } catch (SocketTimeoutException | MalformedURLException exception) {
            Log.e(LOG_TAG, exception.toString());
        } catch (IOException exception) {
            Log.e(LOG_TAG, exception.toString());
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    pureResponse, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            pureResponse.close();
            jsonString = sb.toString();
        } catch (Exception exception) {
            Log.e(LOG_TAG, exception.toString());
        }

        // try parse the string to a JSON object
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException exception) {
            Log.e(LOG_TAG, exception.toString());
        }

        // return JSON String
        return jsonObject;
    }

    public JSONObject postOrPutRequest(String url, String method, String requestBody)
    {
        String httpMethod = "";
        if(method.equals(POST))
        {
            httpMethod = POST;
        }
        else if(method.equals(PUT))
        {
            httpMethod = PUT;
        }
        else
        {
            Log.e(LOG_TAG, "Bad request type, request method is " + method);
        }

        HttpURLConnection connection = null;
        URL apiUrl;
        try {
            apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Content-Type","application/json");
            //connection.setRequestProperty("Content-Type", "application/jsonString");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(StaticResources.HTTP_TIMEOUT);

            if(requestBody != null)
            {
                /*OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
                streamWriter.write(requestBody.toString());
                streamWriter.flush();*/

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(requestBody.toString());

                writer.flush();
                writer.close();
                os.close();
            }

            StringBuilder stringBuilder = new StringBuilder();

            // If conn ok process server response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                String response = null;

                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString();

                Log.d(LOG_TAG, response);
                return new JSONObject(response);
            } else {
                Log.e(LOG_TAG, connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e(LOG_TAG, exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }

    public JSONObject deleteRequest(String url, String method)
    {
        String httpMethod = "";
        if(method.equals(DELETE))
        {
            httpMethod = DELETE;
        }
        else
        {
            Log.e(LOG_TAG, "Bad request type, request method is " + method);
        }

        HttpURLConnection connection = null;
        URL apiUrl;
        try {
            apiUrl = new URL(url);
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(httpMethod);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(StaticResources.HTTP_TIMEOUT);

            StringBuilder stringBuilder = new StringBuilder();

            // If conn ok, process server response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);

                String response; // Hold response

                // Read whole response to stringbuilder
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                response = stringBuilder.toString(); // Set response

                Log.d(LOG_TAG, response); // Log
                return new JSONObject(response);
            } else {
                Log.e(LOG_TAG, connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e(LOG_TAG, exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}
