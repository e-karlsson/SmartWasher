package com.washer.smart.smartwasher;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xxkarlue on 2015-04-10.
 */
public abstract class NavigationBar {
    ArrayList<View> views = new ArrayList<>();
    private int viewCount = 0;
    private int selectedId = 0;

    public void addView(final View v, final Runnable function){
        final int id = viewCount++;
        views.add(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onActive(v);
                selectedId = id;

                for(int i = 0; i<views.size();i++){
                    if(i == id) continue;
                    onInactive(views.get(i));
                }

                function.run();
            }
        });

    }

    public void setActive(int id){
        if(id < 0 || id >= views.size()) return;

        selectedId = id;
        onActive(views.get(id));
        for(int i = 0; i<views.size();i++){
            if(i == id) continue;
            onInactive(views.get(i));
        }

    }

    public abstract void onActive(View v);

    public abstract void onInactive(View v);

    public int getSelectedId(){
        return selectedId;
    }


    public static  NavigationBar blueWhiteToggle(final  View root){
        return new NavigationBar() {
            @Override
            public void onActive(View v) {
                TextView tv = (TextView) v;
                tv.setBackgroundColor(root.getResources().getColor(R.color.tieto_darkblue));
                tv.setTextColor(root.getResources().getColor(R.color.white));
            }

            @Override
            public void onInactive(View v) {
                TextView tv = (TextView) v;
                tv.setBackgroundColor(root.getResources().getColor(R.color.white));
                tv.setTextColor(root.getResources().getColor(R.color.tieto_darkblue));
            }
        };
    }
}
