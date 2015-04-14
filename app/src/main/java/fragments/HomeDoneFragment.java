package fragments;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeDoneFragment extends BaseFragment {



    public static BaseFragment create() {
        HomeDoneFragment fragment = new HomeDoneFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
