package fragments;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class ProgramFragment extends BaseFragment {



    public static BaseFragment create() {
        ProgramFragment fragment = new ProgramFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
