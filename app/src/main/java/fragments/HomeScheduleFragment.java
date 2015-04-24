package fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.washer.smart.smartwasher.R;

import org.w3c.dom.Text;

import java.util.Calendar;

import extra.FontCache;
import extra.LiveData;
import extra.Storage;
import extra.Timer;
import model.LiveRecord;
import model.StartStatus;
import model.Status;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeScheduleFragment extends BaseFragment {

    long startTime;
    String programName, degreeName;
    boolean wind, cheapest;
    LinearLayout startButton, cancelButton, changeButton;
    TextView startTimeText, programNameText, degreeNameText, extraName, extraIcon;

    @Override
    protected void init(View view) {
        startButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_start_direct);
        cancelButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_cancel);
        changeButton = (LinearLayout) view.findViewById(R.id.ll_start_schedule_change);

        startTimeText = (TextView) view.findViewById(R.id.tv_start_schedule_time);
        programNameText = (TextView) view.findViewById(R.id.tv_start_schedule_wash_program);
        degreeNameText = (TextView) view.findViewById(R.id.tv_start_schedule_wash_degree);
        extraName = (TextView) view.findViewById(R.id.tv_start_schedule_extra);
        extraIcon = (TextView) view.findViewById(R.id.tv_schedule_extra);


        TextView scheduleTimeFa = (TextView) view.findViewById(R.id.tv_schedule_clock);
        TextView scheduleProgramFa = (TextView) view.findViewById(R.id.tv_schedule_program);
        TextView scheduleExtraFa = (TextView) view.findViewById(R.id.tv_schedule_extra);

        FontCache.setCustomFont(scheduleProgramFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(scheduleTimeFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(scheduleExtraFa, getResources().getString(R.string.fa), getActivity());


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar rightNow = Calendar.getInstance();
                long currentTime = rightNow.getTimeInMillis();

                WasherService.startAt(currentTime, 45, programName, degreeName, new CallBack<StartStatus>() {
                    @Override
                    public void onSuccess(StartStatus startStatus) {
                        MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_WASHING,false);
                    }

                    @Override
                    public void onError(WasherError error) {
                        Toast.makeText(getActivity(), "Kunde inte ansluta till servern!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasherService.stop(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
                    }

                    @Override
                    public void onError(WasherError error) {
                        Toast.makeText(getActivity(), "Kunde inte ansluta till servern!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasherService.stop(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Storage.saveBoolean(Storage.FROM_START, false);
                        MyViewPager.getInstance().setCurrentItem(MyViewPager.START,false);
                    }

                    @Override
                    public void onError(WasherError error) {
                        Toast.makeText(getActivity(), "Kunde inte ansluta till servern!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    private void updateInfo(){
            LiveRecord liveRecord = LiveData.getLiveRecord();

            if(liveRecord == null){
                return;
            }

            startTime = liveRecord.getProgramInfo().getEndTime();
            programName = liveRecord.getProgramInfo().getName();
            degreeName = liveRecord.getProgramInfo().getDegree();
            wind = liveRecord.getProgramInfo().isWind();
            cheapest = liveRecord.getProgramInfo().isLowPrice();

            Timer.TimeInfo timeInfo = Timer.translate(startTime);

            startTimeText.setText(""+timeInfo.getHour()+":"+timeInfo.getMinute());
            programNameText.setText(programName);
            degreeNameText.setText(degreeName);

            if(wind){
                extraName.setText("Vindkraft");
                extraIcon.setText(getResources().getText(R.string.wind_icon));
            }else if(cheapest){
                extraName.setText("Billigast el");
                extraIcon.setText(getResources().getText(R.string.money_icon));

            }else{
                extraName.setText("");
                extraIcon.setText("");
            }

    }

    @Override
    public void onResume() {
        updateInfo();
        super.onResume();
    }

    @Override
    public void update() {
        updateInfo();
    }

    public static BaseFragment create() {
        HomeScheduleFragment fragment = new HomeScheduleFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_scheduled_content, "HEM"));
        return fragment;
    }

}
