package com.example.legye.wouldyourather.infrastructure;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by legye on 2016. 11. 20..
 */

public class InteractionFragment extends Fragment {

    // fragment azonosítója
    public static final String FRAGMENT_ID = "InteractionFragment";

    // a tartalmazó Activity megszólítására
    protected OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException exception) {
            Log.e(FRAGMENT_ID,
                    "onAttach" +
                            activity.toString() + " " +
                            "nem implementálja OnFragmentInteractionListener interface-t");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
