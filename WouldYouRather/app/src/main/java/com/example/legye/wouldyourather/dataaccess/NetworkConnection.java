package com.example.legye.wouldyourather.dataaccess;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by legye on 2016. 11. 20..
 */

/**
 * Provide network informations
 */
public class NetworkConnection {

    private Context mContext;

    /**
     * Constructor - init context
     * @param context
     */
    public NetworkConnection(Context context){

        mContext = context;
    }

    /**
     * Is internet connection avalaible
     * @return true if connected
     */
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if(networkInfo == null)
            {
                return false;
            }
            else
            {
                return networkInfo.isConnectedOrConnecting();
            }
        }
        return false;
    }
}
