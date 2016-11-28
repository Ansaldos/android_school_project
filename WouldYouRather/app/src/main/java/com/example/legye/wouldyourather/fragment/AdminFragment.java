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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.legye.wouldyourather.R;
import com.example.legye.wouldyourather.dataaccess.AnswerProvider;
import com.example.legye.wouldyourather.dataaccess.QuestionProvider;
import com.example.legye.wouldyourather.dataaccess.StaticResources;
import com.example.legye.wouldyourather.dataaccess.entity.Question;

import org.json.JSONObject;


public class AdminFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EditText etQuestion;
    private EditText etAnswer1;
    private EditText etAnswer2;
    private Button btAdd;
    private ProgressBar pbAddProgress;

    public AdminFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        showDialog(inflater);

        etQuestion = (EditText) view.findViewById(R.id.etQuestion);
        etAnswer1 = (EditText) view.findViewById(R.id.etAnswer1);
        etAnswer2 = (EditText) view.findViewById(R.id.etAnswer2);
        btAdd = (Button) view.findViewById(R.id.btAdd);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate input fields one by one
                String emptyFieldMessage = getString(R.string.admin_fragment_empty_input);
                Boolean isThereError = false;

                if(etQuestion.getText().toString().isEmpty()) {
                    etQuestion.setError(emptyFieldMessage);
                    isThereError = true;
                }

                if(etAnswer1.getText().toString().isEmpty()) {
                    etAnswer1.setError(emptyFieldMessage);
                    isThereError = true;
                }

                if(etAnswer2.getText().toString().isEmpty()) {
                    etAnswer2.setError(emptyFieldMessage);
                    isThereError = true;
                }

                if(!isThereError) {
                    // SAVE
                    new InsertQuestionAndAnswer(
                            etQuestion.getText().toString(), etAnswer1.getText().toString(), etAnswer2.getText().toString())
                            .execute();
                }
            }
        });
        pbAddProgress = (ProgressBar) view.findViewById(R.id.pbAddProgress);

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

    private void showDialog(LayoutInflater inflater) {
        View alertLayout = inflater.inflate(R.layout.password_needed_dialog, null);
        final EditText etPassword = (EditText) alertLayout.findViewById(R.id.etPassword);

        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle(R.string.dialog_title);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton(R.string.dialog_negative_button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Home Fragment Tag");
                ft.commit();
            }
        });

        alert.setPositiveButton(R.string.dialog_positive_button, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Do nothing here because we override this button later to change the close behaviour.
                //However, we still need this because on older versions of Android unless we
                //pass a handler the button doesn't get instantiated
            }
        });

        final AlertDialog dialog = alert.create();
        dialog.show();

        //Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String pass = etPassword.getText().toString(); // Get entered password
                if(pass.equals(StaticResources.ADMIN_PASSWORD)) {
                    dialog.dismiss();
                } else {
                    etPassword.setError(getString(R.string.dialog_wrong_password));
                    // STAY DIALOG OPEN
                }
            }
        });
    }

    public class InsertQuestionAndAnswer extends AsyncTask<Question, String, String> {

        private String mQuestionText;
        private String mAnswer1Text;
        private String mAnswer2Text;
        private Exception mException;

        public InsertQuestionAndAnswer(String mQuestionText, String mAnswer1Text, String mAnswer2Text) {
            this.mQuestionText = mQuestionText;
            this.mAnswer1Text = mAnswer1Text;
            this.mAnswer2Text = mAnswer2Text;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbAddProgress.setVisibility(View.VISIBLE);
            btAdd.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(Question... args) {
            try{
                int questionId = Integer.decode(new QuestionProvider().insertQuestion(mQuestionText));
                AnswerProvider answerProvider = new AnswerProvider();
                answerProvider.insertAnswer(questionId, mAnswer1Text);
                answerProvider.insertAnswer(questionId, mAnswer2Text);

                return "Added new question with answers";
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(mException != null)
            {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                return;
            }
            pbAddProgress.setVisibility(View.GONE);
            btAdd.setVisibility(View.VISIBLE);
        }
    }
}
