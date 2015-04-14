package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class StartFragment extends BaseFragment {

    NavigationBar topStartBar;
    LinearLayout extraParams;

    @Override
    protected void init(View view) {
        topStartBar = NavigationBar.greenWhiteToggle(view);
        extraParams = (LinearLayout) view.findViewById(R.id.ll_extra_params);
        topStartBar.addView(view.findViewById(R.id.tv_start_start_at), new Runnable() {
            @Override
            public void run() {

                extraParams.setVisibility(View.GONE);
                extraParams.setEnabled(false);

            }
        });

        topStartBar.addView(view.findViewById(R.id.tv_start_ready_at), new Runnable() {
            @Override
            public void run() {
                extraParams.setVisibility(View.VISIBLE);
                extraParams.setEnabled(true);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        topStartBar.loadState("StartTopBar");
    }

    @Override
    public void onPause() {
        super.onPause();
        topStartBar.saveState("StartTopBar");
    }

    public static StartFragment create(){
        StartFragment fragment = new StartFragment();
        fragment.setArguments(createBundle(R.layout.layout_start_content, "START"));
        return fragment;
    }
}
