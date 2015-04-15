package fragments;

import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

import model.LiveRecord;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeSleepFragment extends BaseFragment {
    LinearLayout scheduleButton, startNowButton;

    @Override
    protected void init(View view) {
        scheduleButton = (LinearLayout) view.findViewById(R.id.ll_home_sleep_schedule);
        startNowButton = (LinearLayout) view.findViewById(R.id.ll_home_sleep_start);

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.START,false);
            }
        });

        startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_DONE);
            }
        });
    }

    public static BaseFragment create() {
        HomeSleepFragment fragment = new HomeSleepFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }


    private class LiveFetch extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {

            while(shown){

                WasherService.getLiveRecord(new CallBack<LiveRecord>() {
                    @Override
                    public void onSuccess(LiveRecord liveRecord) {

                    }

                    @Override
                    public void onError(WasherError error) {

                    }
                });


                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }

}
