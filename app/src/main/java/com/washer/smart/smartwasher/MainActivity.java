package com.washer.smart.smartwasher;


import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import extra.LiveData;
import dialogs.CustomDialog;
import extra.FontCache;
import extra.MenuBar;
import extra.Storage;
import extra.WasherInfo;
import fragments.*;
import model.LiveRecord;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;


public class MainActivity extends FragmentActivity {

    MyViewPager viewPager;
    PagerAdapter pagerAdapter;
    private boolean running = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GcmHandler.handle(this);
        WasherInfo.init();
        init();
    }

    private List<BaseFragment> createFragments(){
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(HomeSleepFragment.create());
        fragments.add(HomeScheduleFragment.create());
        fragments.add(HomeWashingFragment.create());
        fragments.add(HomeDoneFragment.create());
        fragments.add(StartFragment.create());
        fragments.add(ProgramFragment.create());
        fragments.add(HistoryFragment.create());
        fragments.add(SettingsFragment.create());
        return fragments;
    }

    public void init(){
        final TextView topBarTv = (TextView) findViewById(R.id.tv_top_title);
        TextView homeIcon = (TextView) findViewById(R.id.tv_main_home_icon);
        TextView settingsIcon = (TextView) findViewById(R.id.tv_main_settings_icon);
        TextView historyIcon = (TextView) findViewById(R.id.tv_main_history_icon);
        TextView backIcon = (TextView) findViewById(R.id.tv_main_back_icon);
        FontCache.setCustomFont(homeIcon, getResources().getString(R.string.fa), this);
        FontCache.setCustomFont(settingsIcon, getResources().getString(R.string.fa), this);
        FontCache.setCustomFont(historyIcon, getResources().getString(R.string.fa), this);
        FontCache.setCustomFont(backIcon, getResources().getString(R.string.fa), this);


        MenuBar.init((LinearLayout) findViewById(R.id.ll_main_menu_bar_back));
        MenuBar.hideBackButton();


        pagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(), createFragments());

        viewPager = (MyViewPager) findViewById(R.id.viewpager);
        MyViewPager.init(viewPager, topBarTv);
        viewPager.setAdapter(pagerAdapter);

        NavigationBar navbar = new NavigationBar() {
            @Override
            public void onActive(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.tieto_green));
                for(int i = 0; i < 2; ++i){
                    TextView tv = (TextView) ((LinearLayout) v).getChildAt(i);
                    tv.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onInactive(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.white));
                for(int i = 0; i < 2; ++i){
                    TextView tv = (TextView) ((LinearLayout) v).getChildAt(i);
                    tv.setTextColor(getResources().getColor(R.color.tieto_green));
                }
            }
        };

        LinearLayout leftButton = (LinearLayout) findViewById(R.id.ll_nav_left);
        LinearLayout centerButton = (LinearLayout) findViewById(R.id.ll_nav_center);
        LinearLayout rightButton = (LinearLayout) findViewById(R.id.ll_nav_right);

        navbar.addView(leftButton, new Runnable() {
            @Override
            public void run() {
                MyViewPager.goHome();


            }
        });

        navbar.addView(centerButton, new Runnable() {
            @Override
            public void run() {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HISTORY);




            }
        });

        navbar.addView(rightButton, new Runnable() {
            @Override
            public void run() {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.SETTINGS);

            }
        });

        //set home as selected and update looks
        navbar.setSelected(0);

    }




    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        new LiveFetch().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    private class LiveFetch extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            while(true){
                if(!running) return null;
                WasherService.getLiveRecord(new CallBack<LiveRecord>() {
                    @Override
                    public void onSuccess(LiveRecord liveRecord) {
                        LiveData.setLiveRecord(liveRecord);
                        MyViewPager.changeHome();
                    }

                    @Override
                    public void onError(WasherError error) {

                    }
                });

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //MyViewPager.changeHome();

            }

        }
    }


}
