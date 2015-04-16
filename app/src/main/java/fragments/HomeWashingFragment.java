package fragments;

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
public class HomeWashingFragment extends BaseFragment {
    LinearLayout cancelButton;
    @Override
    protected void init(View view) {
        cancelButton = (LinearLayout) view.findViewById(R.id.ll_start_washing_cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasherService.stop(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {

                    }

                    @Override
                    public void onError(WasherError error) {

                    }
                });
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_DONE,false);
            }
        });
    }

    public static BaseFragment create() {
        HomeWashingFragment fragment = new HomeWashingFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_washing_content, "HEM"));
        return fragment;
    }

}
