package fragments;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

import java.util.Calendar;

import dialogs.CustomDialog;
import dialogs.WasherProgramDialog;
import model.StartStatus;
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
                startNow();
            }
        });
    }



    private void startNow(){
        WasherProgramDialog wpd = new WasherProgramDialog(getActivity());

        wpd.bindOK(new CustomDialog.Callback() {
            @Override
            public void run(int[] ids, String[] tags) {

                long currentTime = 0;
                int tempMin;

                Calendar rightNow = Calendar.getInstance();
                currentTime = rightNow.getTimeInMillis();

                WasherService.startAt(currentTime, 45, new CallBack<StartStatus>() {
                    @Override
                    public void onSuccess(StartStatus startStatus) {
                        Log.d("hej", "Great! Will start server!");
                    }

                    @Override
                    public void onError(WasherError error) {
                        Log.d("hej", "Nope! Couldn't start server..");
                    }
                });

            }
        });

        wpd.bindCancel(new Runnable() {
            @Override
            public void run() {

            }
        });

        wpd.show();
    }




    public static BaseFragment create() {
        HomeSleepFragment fragment = new HomeSleepFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_content, "HEM"));
        return fragment;
    }

}
