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
public class NavigationBar {
    ArrayList<LinearLayout> buttons = new ArrayList<>();
    private static int buttonCount = 0;

    public void addButton(final Activity context, final LinearLayout button, final Runnable function){
        final int id = buttonCount++;
        buttons.add(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundColor(context.getResources().getColor(R.color.tieto_darkblue));
                for(int i = 0; i<buttonCount;i++){
                    if(i == id) continue;


                    LinearLayout inactive = buttons.get(i);
                    inactive.setBackgroundColor(context.getResources().getColor(R.color.tieto_green));
                }

                function.run();
            }
        });

    }
}
