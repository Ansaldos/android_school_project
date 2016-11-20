package com.example.legye.wouldyourather.dataaccess;

/**
 * Created by legye on 2016. 11. 20..
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Makes API connection and implements methods
 */
public class HttpRequestProvider {

    // Volley request queue
    private RequestQueue mQueue;

    // Api base url
    private final String BASE_URL = "http://192.168.1.102:80/www/OSS_Database_API/api/";

    private final String API_KEY = "";

    private NetworkConnection mNetworkConnection;

    private Context mContext;

    /**
     * Constructor - initialize queue
     * @param context
     */
    public HttpRequestProvider(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
        mNetworkConnection = new NetworkConnection(mContext);
    }

    private boolean checkInternetConnection()
    {
        boolean isConnected = mNetworkConnection.isConnectingToInternet();
        if(isConnected)
        {
            return true;
        }
        else
        {
            Toast.makeText(mContext, "Not connected to the internet", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Build full API url
     * @param serviceUrl Service url
     * @return Return with full url
     */
    private String buildUrl(String serviceUrl)
    {
        return BASE_URL + serviceUrl + "?key=" + API_KEY;
    }

    /**
     * Run http get request
     * @param callback Callback method
     * @param serviceUrl API service url
     */
    public void runGetRequest(final IVolleyCallback callback, String serviceUrl)
    {
        if(!checkInternetConnection())
        {
            return;
        }

        // prepare the Request
        JsonObjectRequest httpGetRequest = new JsonObjectRequest(Request.Method.GET, buildUrl(serviceUrl), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onCompleted(response); // Callback method
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", "Http get request failed: " + error.getMessage());
                        callback.onCompleted(null); // Callback method
                    }
                }
        );

        // Add it to the RequestQueue
        mQueue.add(httpGetRequest);
    }

    /**
     * Run http post or put request
     * @param callback Callback method
     * @param serviceUrl API service url
     * @param jsonBody Postbody
     * @param httpMethod Http method
     */
    public void runPostOrPutRequest(final IVolleyCallback callback, String serviceUrl, JSONObject jsonBody, int httpMethod)
    {
        if(!checkInternetConnection())
        {
            return;
        }

        JsonObjectRequest httpPostRequest = new JsonObjectRequest(httpMethod, buildUrl(serviceUrl), jsonBody,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onCompleted(response); // Callback method
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", "Http post or put request failed: " + error.getMessage());
                        callback.onCompleted(null); // Callback method
                    }
                }
        );

        // Add it to the RequestQueue
        mQueue.add(httpPostRequest);
    }

    /**
     * Run http delete rquest
     * @param callback Callback method
     * @param serviceUrl API service URL
     */
    public void runDeleteRequest(final IVolleyCallback callback, String serviceUrl)
    {
        if(!checkInternetConnection())
        {
            return;
        }

        JsonObjectRequest httpDeleteRequest = new JsonObjectRequest(Request.Method.DELETE, buildUrl(serviceUrl), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onCompleted(response); // Callback method
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", "Http delete request failed: " + error.getMessage());
                        callback.onCompleted(null); // Callback method
                    }
                }
        );
        mQueue.add(httpDeleteRequest);
    }
}
