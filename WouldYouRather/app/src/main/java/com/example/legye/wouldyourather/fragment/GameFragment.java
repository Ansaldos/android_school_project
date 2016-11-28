package com.example.legye.wouldyourather.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.legye.wouldyourather.R;
import com.example.legye.wouldyourather.dataaccess.AnswerProvider;
import com.example.legye.wouldyourather.dataaccess.QuestionProvider;
import com.example.legye.wouldyourather.dataaccess.TestProvider;
import com.example.legye.wouldyourather.dataaccess.entity.Answer;
import com.example.legye.wouldyourather.dataaccess.entity.Question;
import com.example.legye.wouldyourather.dataaccess.entity.Test;

import org.json.JSONObject;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class GameFragment extends Fragment {

    private OnFragmentInteractionListener mListener; // TODO REMOVE ME

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private ProgressBar mLoading;
    private Button mNextQuiz;

    private Test mTest;
    private List<Question> mQuestionsResult;
    private List<Answer> mAnswersResult;

    private int mQuestionCounter = 0;
    private Context mContext;
    
    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // INIT UI ELEMENTS HERE
        mQuestion = (TextView) view.findViewById(R.id.tvQuestion);
        mAnswer1 = (Button) view.findViewById(R.id.btAnswer1);
        mNextQuiz = (Button) view.findViewById(R.id.btNextQuiz);
        mNextQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuestionsAndAnswers();
                view.setVisibility(View.GONE);
            }
        });


        View.OnClickListener answerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int btnId = 0;
                switch(view.getId()) {
                    case R.id.btAnswer1:
                        // it was the first button
                        btnId = 0;
                        break;
                    case R.id.btAnswer2:
                        // it was the second button
                        btnId = 1;
                        break;
                }

                // Számláló növelése
                new IncrementAnswerAsync().execute(mAnswersResult.get(btnId).getId());

                // Ha a kérdés számláló 10-el egyenlő, akkor elért a felhasználó a teszt végéhez
                if(mQuestionCounter == 9)
                {
                    // Increment test completing counter
                    try {
                        new TestProvider().incrementTestCounter(mTest.getId());
                    } catch (TimeoutException e) {
                        showErrorPopupMessage(e);
                    }

                    // TEST ENDED
                    mQuestionsResult = null;
                    mAnswersResult = null;
                    mTest = null;
                    mQuestionCounter = 0;

                    mAnswer1.setVisibility(View.GONE);
                    mAnswer2.setVisibility(View.GONE);

                    mNextQuiz.setVisibility(View.VISIBLE);
                    mQuestion.setText(R.string.game_fragment_play_again);

                }
                else // Egyébként növeljük a számlálót és betöltjük a következő válaszokat
                {
                    mQuestionCounter++;
                    loadQuestionsAndAnswers();
                    Log.d("SZAMLALO", "SZAMLALO: " + mQuestionCounter);
                }

            }
        };

        mAnswer1.setOnClickListener(answerOnClickListener);

        mAnswer2 = (Button) view.findViewById(R.id.btAnswer2);
        mAnswer2.setOnClickListener(answerOnClickListener);

        mLoading = (ProgressBar) view.findViewById(R.id.pbLoading);

        mQuestionsResult = new ArrayList<>();
        mAnswersResult = new ArrayList<>();

        loadQuestionsAndAnswers();

        return view;
    }

    private void showErrorPopupMessage(Exception exception)
    {
        new AlertDialog.Builder(mContext)
                .setTitle("Error happened")
                .setMessage(exception.getMessage())
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, new HomeFragment(), "Home Fragment Tag");
                        ft.commit();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void loadQuestionsAndAnswers()
    {
        new GetQuestionsAndAnswers(this.getContext()).execute();
    }

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

    private class GetQuestionsAndAnswers extends AsyncTask<String, String, JSONObject> {

        private final String LOG_TAG = this.getClass().toString();
        private Context mContext;
        private Exception exception;

        public GetQuestionsAndAnswers(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoading.setVisibility(View.VISIBLE);
            mQuestion.setVisibility(View.GONE);
            mAnswer1.setVisibility(View.GONE);
            mAnswer2.setVisibility(View.GONE);
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            // Random teszt lekérése a szervertől, ha még nem történt meg
            if(mTest == null)
            {
                try{
                    mTest = new TestProvider().getRandomTest();
                    if(mTest == null)
                    {
                        exception = new NullPointerException("Sorry, our server not responding.\nPlease try again later.");
                        return null;
                    }
                } catch (TimeoutException ex)
                {
                    exception = ex;
                    return null;
                }

            }

            if(mQuestionsResult == null || mQuestionsResult.size() == 0)
            {
                mQuestionsResult = new QuestionProvider().getQuestions(mTest.getId());
            }

            int questionsListSize = mQuestionsResult.size();
            if(questionsListSize > 0 && questionsListSize > mQuestionCounter)
            {
                mAnswersResult = new QuestionProvider().getQuestionAnswers(mQuestionsResult.get(mQuestionCounter).getId());
            }
            else
            {
                return null;
            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if(exception != null) {
                showErrorPopupMessage(exception);
                /*new AlertDialog.Builder(mContext)
                        .setTitle("Error happened")
                        .setMessage(exception.getMessage())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame, new HomeFragment(), "Home Fragment Tag");
                                ft.commit();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();*/

                return;
            }

            if(json != null)
            {
                Log.d(LOG_TAG, "Q size: " + mQuestionsResult.size() + " A size: " + mAnswersResult.size());

                if(mAnswersResult.size() >= 2)
                {
                    mQuestion.setVisibility(View.VISIBLE);
                    mAnswer1.setVisibility(View.VISIBLE);
                    mAnswer2.setVisibility(View.VISIBLE);
                    mQuestion.setText(mQuestionsResult.get(mQuestionCounter).getText());
                    mAnswer1.setText(mAnswersResult.get(0).getText());
                    mAnswer2.setText(mAnswersResult.get(1).getText());
                }
                else
                {
                    // Clear list because test or answers was bad
                    mQuestionsResult.clear();
                    mAnswersResult.clear();

                    showErrorPopupMessage(new InvalidParameterException("Question has less than 2 avalaible answer"));
                }

                mLoading.setVisibility(View.GONE);
            }
            else
            {
                showErrorPopupMessage(new InvalidParameterException("Sorry, our server not responding.\nPlease try again later."));
            }
        }
    }

    public class IncrementAnswerAsync extends AsyncTask<Integer, String, JSONObject> {

        private Exception mException;

        @Override
        protected JSONObject doInBackground(Integer... args) {
            try{
                return new AnswerProvider().incrementAnswerCounter(args[0]);
            } catch (NullPointerException exception){
                mException = exception;
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if(mException != null)
            {
                showErrorPopupMessage(mException);
                return;
            }
        }
    }
}
