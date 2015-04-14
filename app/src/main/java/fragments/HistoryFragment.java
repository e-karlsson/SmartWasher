package fragments;

import android.view.View;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import extra.Storage;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HistoryFragment extends BaseFragment {

    NavigationBar topTabBar, graphDataBar;



    protected void init(View v){
        topTabBar = NavigationBar.greenWhiteToggle(v);

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

        graphDataBar = NavigationBar.greenWhiteToggle(v);

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
        topTabBar.loadState("TimeBar");
        graphDataBar.loadState("GraphData");
    }

    @Override
    public void onPause() {
        super.onPause();
        topTabBar.saveState("TimeBar");
        topTabBar.saveState("GraphData");
    }

    public static BaseFragment create() {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(createBundle(R.layout.layout_history_content, "HISTORIK"));
        return fragment;
    }
}
