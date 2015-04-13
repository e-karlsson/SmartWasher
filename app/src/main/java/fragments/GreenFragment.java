package fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class GreenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_testgreen, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("did", "resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("did", "pause");
    }
}
