package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeFragment extends BaseFragment {

    @Override
    protected void init(View view) {

    }

    public static BaseFragment create() {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
