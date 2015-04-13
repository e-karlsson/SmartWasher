package com.washer.smart.smartwasher;


import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fragments.BlueFragment;
import fragments.GreenFragment;
import fragments.HomeFragment;
import fragments.MyFragmentAdapter;
import fragments.RedFragment;
import fragments.StartFragment;
import sdk.WasherService;


public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WasherService.init("http://kexdns.ddns.net", 8080);
        new HTTPTest();
    }

    private List<Fragment> createFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new StartFragment());
        fragments.add(new BlueFragment());

        return fragments;
    }

    public void init(){
        pagerAdapter = new MyFragmentAdapter(getSupportFragmentManager(), createFragments());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        final TextView topBarTv = (TextView) findViewById(R.id.tv_top_title);

        NavigationBar navbar = new NavigationBar();

        LinearLayout leftButton = (LinearLayout) findViewById(R.id.ll_nav_left);
        LinearLayout centerButton = (LinearLayout) findViewById(R.id.ll_nav_center);
        LinearLayout rightButton = (LinearLayout) findViewById(R.id.ll_nav_right);

        navbar.addButton(this, leftButton, new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(0);
                topBarTv.setText("HEM");

            }
        });

        navbar.addButton(this, centerButton, new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(1);
                topBarTv.setText("HISTORIK");


            }
        });

        navbar.addButton(this, rightButton, new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(2);
                topBarTv.setText("INSTÄLLNINGAR");


            }
        });
    }
}
