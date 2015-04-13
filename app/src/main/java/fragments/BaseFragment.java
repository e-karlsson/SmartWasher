package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xxottosl on 2015-04-13.
 */
public abstract class BaseFragment extends Fragment {

    private int layoutId;
    private String titleName;

    public BaseFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        layoutId = bundle.getInt("layoutId");
        titleName = bundle.getString("title");
        View view = inflater.inflate(layoutId, container, false);
        return view;
    }


    public String getTitleName(){
        return titleName;
    }



    protected static Bundle createBundle(int layoutId, String titleName){
        Bundle bundle = new Bundle();
        bundle.putInt("layoutId", layoutId);
        bundle.putString("title", titleName);
        return bundle;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Paused", titleName);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Resumed", titleName);
    }
}
