package fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeSleepFragment extends BaseFragment {
    LinearLayout scheduleButton;

    @Override
    protected void init(View view) {
        scheduleButton = (LinearLayout) view.findViewById(R.id.ll_home_sleep_schedule);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.START,false);
            }
        });
    }

    public static BaseFragment create() {
        HomeSleepFragment fragment = new HomeSleepFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
