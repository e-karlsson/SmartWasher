package fragments;

import android.view.View;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeSleepFragment extends BaseFragment {

    @Override
    protected void init(View view) {

    }

    public static BaseFragment create() {
        HomeSleepFragment fragment = new HomeSleepFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
