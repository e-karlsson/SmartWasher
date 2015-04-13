package fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xxottosl on 2015-04-13.
 */
public abstract class BaseFragment extends Fragment {

    private int layoutId;
    private String titleName;
    protected Activity activity;


    public BaseFragment(Activity activity, int layoutId, String titleName){
        this.activity = activity;
        this.layoutId = layoutId;
        this.titleName = titleName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        return view;
    }


    public String getTitleName(){
        return titleName;
    }



}
