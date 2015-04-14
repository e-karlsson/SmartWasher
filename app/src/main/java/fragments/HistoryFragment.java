package fragments;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HistoryFragment extends BaseFragment {



    public static BaseFragment create() {
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
