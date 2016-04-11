package com.example.leon.project1.ui;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import android.R;
import com.example.leon.project1.util.*;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

//linked to opening screen main_wrapper_ activity
public class MainWrapperActivity extends ActionBarActivity implements MaterialTabListener {

    //used to create tabbed views
    private MaterialTabHost tabHost;
    private ViewPager pager;
    private ViewPagerAdapter adapter;

    private boolean locked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Calls Activity CLass onCreate
        super.onCreate(savedInstanceState);

        //Sets content in screeen along with layouts
        setContentView(com.example.leon.project1.R.layout.activity_main_wrapper);
        Toolbar toolbar = (Toolbar) findViewById(com.example.leon.project1.R.id.toolbar);
        setSupportActionBar(toolbar);
        //associate id with view instead of working with namde
        pager = (ViewPager) this.findViewById(com.example.leon.project1.R.id.pager );
        //assign fragment
        adapter = new ViewPagerAdapter(getFragmentManager());
        //assign tabs on screen to tabHost object
        tabHost = (MaterialTabHost) this.findViewById(com.example.leon.project1.R.id.tabHost);
        // insert all tabs from pagerAdapter data
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(this)
            );

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //obtain background color from colors.xml file
        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorBackground,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        //call GC to free array memory
        array.recycle();

        if(!locked) {
            locked = true;
            Intent i = new Intent(this, LockActivity.class);
            //fetch color from Util.class
            i.putExtra(Util.BACKGROUND_COLOR, backgroundColor);
            //call next activity and get result of activity
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //code will return when activity exists
        if (requestCode == 1) {

            locked = false;

            if(resultCode == RESULT_OK && pager.getAdapter() == null){
                // init view pager
                pager.setAdapter(adapter);
                //keep two tabs on non-current screen
                pager.setOffscreenPageLimit(2);
                pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // when user do a swipe the selected tab change
                        tabHost.setSelectedNavigationItem(position);

                    }
                });

            }
            if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        //attach names to tabs
        public Fragment getItem(int num) {
            switch (num) {
                case 0:
                    return new android.app.Fragment();
                case 1:
                    return new android.app.Fragment();
                case 2:
                    return new android.app.Fragment();
            }

            return null;
        }
        //total no. of tabs
        @Override
        public int getCount() {
            return 3;
        }

        //names of tabs
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Packages";
                case 1:
                    return "Log";
                case 2:
                    return "Settings";
            }
            return "";
        }

    }

}
