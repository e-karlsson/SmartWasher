package com.washer.smart.smartwasher;


import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import dialogs.CustomDialog;
import extra.FontCache;
import extra.MenuBar;
import extra.Storage;
import fragments.*;
import sdk.WasherService;


public class MainActivity extends FragmentActivity {

    MyViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WasherService.init("http://kexdns.ddns.net", 8080);
        Storage.init(this);
        new HTTPTest();
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
                v.setBackgroundColor(getResources().getColor(R.color.tieto_darkblue));
            }

            @Override
            public void onInactive(View v) {
                v.setBackgroundColor(getResources().getColor(R.color.tieto_green));
            }
        };

        LinearLayout leftButton = (LinearLayout) findViewById(R.id.ll_nav_left);
        LinearLayout centerButton = (LinearLayout) findViewById(R.id.ll_nav_center);
        LinearLayout rightButton = (LinearLayout) findViewById(R.id.ll_nav_right);

        navbar.addView(leftButton, new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(0);


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
    }
}
