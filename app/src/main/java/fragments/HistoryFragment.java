package fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import org.w3c.dom.Text;

import extra.Storage;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HistoryFragment extends BaseFragment {

    NavigationBar topTabBar, graphDataBar;



    protected void init(View v){
        topTabBar = NavigationBar.blueWhiteToggle(v);

        topTabBar.addView(v.findViewById(R.id.tv_history_week), new Runnable() {
            @Override
            public void run() {

            }
        });

        topTabBar.addView(v.findViewById(R.id.tv_history_month), new Runnable() {
            @Override
            public void run() {

            }
        });

        topTabBar.addView(v.findViewById(R.id.tv_history_year), new Runnable() {
            @Override
            public void run() {

            }
        });

        graphDataBar = NavigationBar.blueWhiteToggle(v);

        graphDataBar.addView(v.findViewById(R.id.tv_history_graph), new Runnable() {
            @Override
            public void run() {

            }
        });

        graphDataBar.addView(v.findViewById(R.id.tv_history_data), new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        int timeId = Storage.loadInt(Storage.HISTORY_TIME_INDEX, 0);
        if(topTabBar != null)
            topTabBar.setActive(timeId);

    }

    @Override
    public void onPause() {
        super.onPause();
        int timeId = topTabBar.getSelectedId();
        Storage.saveInt(Storage.HISTORY_TIME_INDEX, timeId);
    }

    public static BaseFragment create() {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(createBundle(R.layout.layout_history_content, "HISTORIK"));
        return fragment;
    }
}
