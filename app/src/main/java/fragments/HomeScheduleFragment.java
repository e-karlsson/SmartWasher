package fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeScheduleFragment extends BaseFragment {

    LinearLayout startButton, cancelButton, changeButton;
    @Override
    protected void init(View view) {
        startButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_start_direct);
        cancelButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_cancel);
        changeButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_change);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_WASHING,false);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.START,false);
            }
        });


    }

    public static BaseFragment create() {
        HomeScheduleFragment fragment = new HomeScheduleFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_scheduled_content, "HEM"));
        return fragment;
    }

}
