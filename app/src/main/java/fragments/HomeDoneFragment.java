package fragments;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

import model.Status;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeDoneFragment extends BaseFragment {
    LinearLayout okButton;

    @Override
    protected void init(View view) {
        okButton = (LinearLayout) view.findViewById(R.id.ll_start_done_ok);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasherService.setDone(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Log.d("Washer", "I've sent DONE.");
                    }

                    @Override
                    public void onError(WasherError error) {
                        Log.d("Washer", "Unsuccessfully sent DONE.");
                    }
                });
            }
        });

        MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP);

    }

    public static BaseFragment create() {
        HomeDoneFragment fragment = new HomeDoneFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_done_content, "HEM"));
        return fragment;
    }

}
