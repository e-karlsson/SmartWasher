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
public class StartFragment extends BaseFragment {

    @Override
    protected void init(View view) {

    }

    public static StartFragment create(){
        StartFragment fragment = new StartFragment();
        fragment.setArguments(createBundle(R.layout.layout_start_content, "START"));
        return fragment;
    }
}
