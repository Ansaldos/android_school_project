package com.example.legye.wouldyourather.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.legye.wouldyourather.R;
import com.example.legye.wouldyourather.viewmodel.NavDrawerItem;

/**
 * Created by legye on 2016. 11. 20..
 */

public class NavDrawerListAdapter extends ArrayAdapter<NavDrawerItem> {
    private Context mContext;
    private int mLayoutResourceId;
    private NavDrawerItem mData[] = null;

    public NavDrawerListAdapter(Context context, int layoutResourceId, NavDrawerItem[] data) {
        super(context, layoutResourceId, data);
        this.mContext = context;
        mLayoutResourceId = layoutResourceId;
        mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;

        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(mLayoutResourceId, parent, false);

        // UI elemek
        ImageView ivIcon = (ImageView) listItem.findViewById(R.id.drawer_item_icon);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.drawer_item_title);
        TextView tvCounter = (TextView) listItem.findViewById(R.id.drawer_item_counter);

        // adatokkal feltöltés
        NavDrawerItem item = mData[position];
        ivIcon.setImageResource(item.getIcon());
        tvTitle.setText(item.getTitle());
        if(item.getCounterVisibility()){
            tvCounter.setText(item.getCount());
        }
        else {
            tvCounter.setVisibility(View.GONE);
        }

        return listItem;
    }
}
