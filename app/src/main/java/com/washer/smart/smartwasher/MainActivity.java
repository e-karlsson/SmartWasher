package com.washer.smart.smartwasher;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import sdk.WasherService;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WasherService.init("http://kexdns.ddns.net", 8080);
        new HTTPTest();
        init();
    }

    public void init(){
        NavigationBar navbar = new NavigationBar();
        final TextView tvStatus = (TextView) findViewById(R.id.tv_home_status);
        LinearLayout leftButton = (LinearLayout) findViewById(R.id.ll_nav_left);
        LinearLayout centerButton = (LinearLayout) findViewById(R.id.ll_nav_center);
        LinearLayout rightButton = (LinearLayout) findViewById(R.id.ll_nav_right);

        navbar.addButton(this, leftButton, new Runnable() {
            @Override
            public void run() {
                tvStatus.setText("Vänster knapp");

            }
        });

        navbar.addButton(this, centerButton, new Runnable() {
            @Override
            public void run() {
                tvStatus.setText("Mitten knapp");

            }
        });

        navbar.addButton(this, rightButton, new Runnable() {
            @Override
            public void run() {
                tvStatus.setText("Höger knapp");

            }
        });
    }

    //Test21324
    //This is a branch

}
