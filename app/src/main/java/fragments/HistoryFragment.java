package fragments;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import java.util.ArrayList;
import java.util.List;

import customviews.Border;
import customviews.EnergyGraph;
import customviews.RecordLabel;
import extra.Storage;
import model.WashRecord;
import model.WashRecords;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HistoryFragment extends BaseFragment {

    NavigationBar topTabBar, graphDataBar;
    LinearLayout scrollContent, graphContent;
    ScrollView sv;
    boolean showGraphs = false;

    private final long DAY_MILLIS = 1000*60*60*24;
    private final long WEEK_MILLIS = DAY_MILLIS*7;
    private final long MONTH_MILLIS = DAY_MILLIS*30;
    private final long YEAR_MILLIS = DAY_MILLIS*365;

    private List<WashRecord> weekRecords;
    private List<WashRecord> monthRecords;
    private List<WashRecord> yearRecords;



    protected void init(View v){
        topTabBar = NavigationBar.greenWhiteToggle(v);
        scrollContent = (LinearLayout) v.findViewById(R.id.ll_history_scroll_content);
        graphContent = (LinearLayout) v.findViewById(R.id.ll_history_graph_content);
        sv = (ScrollView) v.findViewById(R.id.sv_history);



        topTabBar.addView(v.findViewById(R.id.tv_history_week), new Runnable() {
            @Override
            public void run() {
                if(weekRecords == null);
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
                sv.setVisibility(View.GONE);
                graphContent.setVisibility(View.VISIBLE);
                showGraphs = true;
            }
        });

        graphDataBar.addView(v.findViewById(R.id.tv_history_data), new Runnable() {
            @Override
            public void run() {
                graphContent.setVisibility(View.GONE);
                sv.setVisibility(View.VISIBLE);
                showGraphs = false;
            }
        });
    }

    private void loadRecordsSince(long time){
        WasherService.getHistoryByTime(time, new CallBack<WashRecords>() {
            @Override
            public void onSuccess(WashRecords washRecords) {
                createGraphs(washRecords.getRecords());
                createLabels(washRecords.getRecords());

            }

            @Override
            public void onError(WasherError error) {
                Log.d("Error", error.getReason());
            }
        });
    }

    private void createGraphs(List<WashRecord> records){
        graphContent.removeAllViews();
        graphContent.addView(new EnergyGraph(getActivity(), records));
    }

    private void createLabels(List<WashRecord> records){
        scrollContent.removeAllViews();
        for(int i = 0; i < records.size(); ++i){
            if(i != 0)
                scrollContent.addView(new Border(getActivity(), getResources().getColor(R.color.tieto_green), 10, 0));

            scrollContent.addView(new RecordLabel(getActivity(), records.get(records.size()-i-1)));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        topTabBar.loadState("TimeBar");
        graphDataBar.loadState("GraphData");
        loadRecordsSince(-1);
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
