package fragments;

import android.view.View;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class SettingsFragment extends BaseFragment {



    public static BaseFragment create() {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(createBundle(R.layout.layout_settings_content, "INSTÄLLNINGAR"));
        return fragment;
    }

    @Override
    protected void init(View view) {

    }
}
