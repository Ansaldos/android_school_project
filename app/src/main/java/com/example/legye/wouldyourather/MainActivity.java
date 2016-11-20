package com.example.legye.wouldyourather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.legye.wouldyourather.adapter.NavDrawerListAdapter;
import com.example.legye.wouldyourather.fragment.AdminFragment;
import com.example.legye.wouldyourather.fragment.HomeFragment;
import com.example.legye.wouldyourather.fragment.StatisticsFragment;
import com.example.legye.wouldyourather.infrastructure.OnFragmentInteractionListener;
import com.example.legye.wouldyourather.viewmodel.NavDrawerItem;

public class MainActivity extends
        AppCompatActivity implements OnFragmentInteractionListener {

    // Declare UI elements here

    // Navigation ui elements
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // Drawer's title
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    // Slider menu elements
    private NavDrawerItem mNavDrawerItems[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init UI elements here
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        setupDrawerItems();

        NavDrawerListAdapter adapter = new NavDrawerListAdapter(this, R.layout.drawer_list_item,
                mNavDrawerItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        setupDrawerToggle();

        // Set first page when application starts
        selectItem(0);

    }

    private void setupDrawerItems() {
        mNavDrawerItems = new NavDrawerItem[3]; // !! be careful with array size
        mNavDrawerItems[0] = new NavDrawerItem("Home", R.drawable.ic_home_white_48dp);
        mNavDrawerItems[1] = new NavDrawerItem("Statistics", R.drawable.ic_home_white_48dp); // TODO icon
        mNavDrawerItems[2] = new NavDrawerItem("Admin", R.drawable.ic_verified_user_white_48dp);
        // TODO add more items, such as admin element
    }

    // Setting up drawer button, and subscribe to open and close event
    private void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close)
        {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // Drawer item event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Sync drawer toggle
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    // Menu click event
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new StatisticsFragment();
                break;
            case 2:
                fragment = new AdminFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            /*FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();*/
            navigateTo(fragment, false);

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavDrawerItems[position].getTitle());
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    // Navigate to framegment
    private void navigateTo (Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(addToBackStack) {
            transaction.replace(R.id.content_frame, fragment)
                    .addToBackStack("tag").commit();
        }
        else {
            transaction.replace(R.id.content_frame, fragment)
                    .commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    public void onFragmentMessage(String fragmentId, Object data) {
        /*switch (fragmentId) {
            // hotel lista
            case HotelListFragment.FRAGMENT_ID:
                // részletes nézetbe navigálunk
                HotelListItemViewModel item = (HotelListItemViewModel) data;
                navigateTo(HotelDetailsFragment.newInstance(item.getmHotelId()), true);
                break;
            // hotel részletes nézet
            case HotelDetailsFragment.FRAGMENT_ID:
                // booking-ra navigálunk

                break;
        }*/


    }

    // Back button pressed event handler
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
