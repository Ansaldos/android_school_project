package com.example.legye.wouldyourather.fragment;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.legye.wouldyourather.R;
import com.example.legye.wouldyourather.dataaccess.JSONParser;
import com.example.legye.wouldyourather.dataaccess.entity.Info;
import com.example.legye.wouldyourather.dataaccess.entity.Test;
import com.example.legye.wouldyourather.dataaccess.entity.TestsResult;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;


    private List<Test> mTestList; // TODO REMOVE ME


    private Button mStart;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mStart = (Button) view.findViewById(R.id.btStart);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new GameFragment(), "Game Fragment Tag");
                ft.commit();
            }
        });

        /*mTestGetButton = (Button)view.findViewById(R.id.btTestGet);
        mTestGetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONParse jsonParse = new JSONParse();
                    jsonParse.execute("http://192.168.1.102:80/www/Wouldyou_API/api/tests?key=WP0ueO3XMsNVhjsN").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        mTestPostPutButton = (Button)view.findViewById(R.id.btTestPostPut);
        mTestPostPutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONParsePostPut jsonParse = new JSONParsePostPut(JSONParser.PUT, null);
                    jsonParse.execute("http://192.168.1.102:80/www/Wouldyou_API/api/test?key=WP0ueO3XMsNVhjsN").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        mTestDeleteButton = (Button)view.findViewById(R.id.btTestDelete);
        mTestDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONParseDelete jsonParse = new JSONParseDelete();
                    jsonParse.execute("http://192.168.1.102:80/www/Wouldyou_API/api/test/2?key=WP0ueO3XMsNVhjsN").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*
    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        private final String LOG_TAG = this.getClass().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTestList = null;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getRequest(args[0], JSONParser.GET);

            // Getting JSON Array
            Gson gson = new Gson();
            List<Test> testList = (gson.fromJson(json.toString(), TestsResult.class)).getData();
            mTestList = testList;

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            Log.d(LOG_TAG, "Size: " + mTestList.size());

        }
    }

    private class JSONParseDelete extends AsyncTask<String, String, JSONObject> {

        private final String LOG_TAG = this.getClass().toString();
        private String mInfo;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTestList = null;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.deleteRequest(args[0], JSONParser.DELETE);

            // Getting JSON Array
            Gson gson = new Gson();
            mInfo = (gson.fromJson(json.toString(), Info.class)).getInfo();
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.d(LOG_TAG, "Delete result: " + mInfo);
        }
    }

    private class JSONParsePostPut extends AsyncTask<String, String, JSONObject> {

        private final String LOG_TAG = this.getClass().toString();
        private String mInfo;
        private JSONObject mRequestBody;
        private String mHttpMethod;

        public JSONParsePostPut(String httpMethod, String requestBody) {
            if(requestBody != null)
            {
                try {
                    mRequestBody = new JSONObject(requestBody);
                } catch (JSONException e) {
                    mRequestBody = null;
                }
            }

            mHttpMethod = httpMethod;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTestList = null;
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.postOrPutRequest(args[0], mHttpMethod, mRequestBody);

            // Getting JSON Array
            Gson gson = new Gson();
            mInfo = (gson.fromJson(json.toString(), Info.class)).getInfo();
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.d(LOG_TAG, "Post or put result: " + mInfo);
        }
    }*/
}
