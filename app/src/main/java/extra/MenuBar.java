package extra;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by xxkarlue on 2015-04-16.
 */
public class MenuBar {
    private static LinearLayout menuBackButton;

    public static void init(LinearLayout menuBackButton){
        MenuBar.menuBackButton = menuBackButton;
    }

    public static void hideBackButton(){
        MenuBar.menuBackButton.setVisibility(View.INVISIBLE);
    }

    public static void showBackButton(){
        MenuBar.menuBackButton.setVisibility(View.VISIBLE);
    }

    public static void setBackAction(final Runnable function){
        MenuBar.menuBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.run();
            }
        });

        MenuBar.menuBackButton.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.run();
            }
        });
    }


}
