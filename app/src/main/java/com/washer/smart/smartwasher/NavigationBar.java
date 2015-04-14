package com.washer.smart.smartwasher;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import extra.Storage;

/**
 * Created by xxkarlue on 2015-04-10.
 */
public abstract class NavigationBar {
    ArrayList<View> views = new ArrayList<>();
    private int viewCount = 0;
    private int selectedId = 0;
    ArrayList<Runnable> functions = new ArrayList<>();

    public void addView(final View v, final Runnable function){
        final int id = viewCount++;
        views.add(v);

        functions.add(function);

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

    public void setSelected(int id){
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

    public void runSelected(){
        functions.get(selectedId).run();
    }

    public void saveState(String name){
        Storage.saveInt(name, selectedId);
    }

    public void loadState(String name){
        int loadedId = Storage.loadInt(name, 0);
        setSelected(loadedId);
        runSelected();
    }



    public static  NavigationBar greenWhiteToggle(final View root){
        return new NavigationBar() {
            @Override
            public void onActive(View v) {
                TextView tv = (TextView) v;
                tv.setBackgroundColor(root.getResources().getColor(R.color.tieto_green));
                tv.setTextColor(root.getResources().getColor(R.color.white));
            }

            @Override
            public void onInactive(View v) {
                TextView tv = (TextView) v;
                tv.setBackgroundColor(root.getResources().getColor(R.color.white));
                tv.setTextColor(root.getResources().getColor(R.color.tieto_green));
            }
        };
    }

}
