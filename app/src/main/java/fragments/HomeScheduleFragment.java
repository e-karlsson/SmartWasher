package fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.washer.smart.smartwasher.R;

import java.util.Calendar;

import extra.FontCache;
import sdk.WasherService;

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

        TextView scheduleTimeFa = (TextView) view.findViewById(R.id.tv_schedule_clock);
        TextView scheduleProgramFa = (TextView) view.findViewById(R.id.tv_schedule_program);
        TextView scheduleExtraFa = (TextView) view.findViewById(R.id.tv_schedule_extra);

        FontCache.setCustomFont(scheduleProgramFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(scheduleTimeFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(scheduleExtraFa, getResources().getString(R.string.fa), getActivity());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_WASHING,false);

                Calendar rightNow = Calendar.getInstance();
                long currentTime = rightNow.getTimeInMillis();




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
