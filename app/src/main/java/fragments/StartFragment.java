package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import java.util.Calendar;

import dialogs.CustomDialog;
import dialogs.WasherProgramDialog;
import dialogs.WasherTimeDialog;
import extra.FontCache;
import extra.Storage;
import model.StartStatus;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class StartFragment extends BaseFragment {

    long time = -1;
    int dayIndex;
    int hourIndex;
    int minIndex;


    String program;

    NavigationBar topStartBar;
    LinearLayout extraParams;
    LinearLayout startButton;
    LinearLayout startTimeLayout;
    LinearLayout startChooseProgram;
    TextView startTimeDescription, chooseProgramDescription, startTimeTitle;
    String programName = "Välj program";
    String degreeName = "Välj grader";
    Switch priceSwitch, windSwitch;



    @Override
    protected void init(View view) {
        TextView startTimeFa = (TextView) view.findViewById(R.id.tv_start_time_fa);
        FontCache.setCustomFont(startTimeFa, getResources().getString(R.string.fa), getActivity());
        startTimeFa.setText("\u0f00");
        topStartBar = NavigationBar.greenWhiteToggle(view);
        extraParams = (LinearLayout) view.findViewById(R.id.ll_extra_params);
        startButton = (LinearLayout) view.findViewById(R.id.ll_start_start_button);
        priceSwitch = (Switch) view.findViewById(R.id.sw_start_cheapest);
        windSwitch = (Switch) view.findViewById(R.id.sw_start_wind);
        startTimeLayout = (LinearLayout) view.findViewById(R.id.ll_start_time);
        startChooseProgram = (LinearLayout) view.findViewById(R.id.ll_start_choose_program);
        startTimeDescription = (TextView) view.findViewById(R.id.tv_start_time_description);
        chooseProgramDescription = (TextView) view.findViewById(R.id.tv_start_program);
        startTimeTitle = (TextView) view.findViewById(R.id.tv_start_time);

        setCurrentTime();

        startChooseProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProgram();
            }
        });

        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClick();
            }
        });

        priceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)   windSwitch.setChecked(false);
            }
        });

        windSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)   priceSwitch.setChecked(false);
            }
        });

        topStartBar.addView(view.findViewById(R.id.tv_start_start_at), new Runnable() {
            @Override
            public void run() {

                extraParams.setVisibility(View.GONE);
                extraParams.setEnabled(false);

            }

        });

        topStartBar.addView(view.findViewById(R.id.tv_start_ready_at), new Runnable() {
            @Override
            public void run() {
                extraParams.setVisibility(View.VISIBLE);
                extraParams.setEnabled(true);
            }
        });


    }

    private void setCurrentTime(){
        long currentTime =0;
        int tempMin;

        Calendar rightNow = Calendar.getInstance();

        currentTime = rightNow.getTimeInMillis();

        tempMin = rightNow.get(Calendar.MINUTE);
        tempMin = ((tempMin/5)+1)*5;

        if(tempMin == 60){
            tempMin = 0;
            rightNow.add(Calendar.HOUR_OF_DAY, 1);
        }
        rightNow.set(Calendar.MINUTE, tempMin);

        dayIndex = 0;
        hourIndex = rightNow.get(Calendar.HOUR_OF_DAY);
        minIndex = rightNow.get(Calendar.MINUTE)/5;
        String min = tempMin+"";
        if(tempMin < 10) min = "0"+min;
        startTimeDescription.setText("idag, kl. "+rightNow.get(Calendar.HOUR_OF_DAY)+":"+min);


        time = currentTime;
    }

    private boolean validateFields(){
        boolean isOK = true;
        if(time<0){
            startTimeDescription.setTextColor(getResources().getColor(R.color.tieto_orange));
            startTimeTitle.setTextColor(getResources().getColor(R.color.tieto_orange));

            isOK = false;
        }
        if(chooseProgramDescription.getText().toString().equals("Välj tvättprogram")){
            chooseProgramDescription.setTextColor(getResources().getColor(R.color.tieto_orange));
            isOK = false;
        }

        return isOK;
    }

    CallBack<StartStatus> callback = new CallBack<StartStatus>() {
        @Override
        public void onSuccess(StartStatus status) {
            Log.d("hej", "Great! Will start server!");
        }

        @Override
        public void onError(WasherError error) {
            Log.d("hej", "Nope! Couldn't start server..");

        }
    };


    private void onStartClick(){
        if(validateFields() == false) return;

        if(topStartBar.getSelectedId() == 0){
            WasherService.startAt(time, 45, callback);
        }else{
            WasherService.startReadyAt(time, 45, priceSwitch.isChecked(), windSwitch.isChecked(), callback);
        }

        MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SCHEDULE,false);


    }




    private void chooseProgram(){
        WasherProgramDialog wpd = new WasherProgramDialog(getActivity());

        wpd.bindOK(new CustomDialog.Callback() {
            @Override
            public void run(int[] ids, String[] tags) {
                chooseProgramDescription.setText(tags[0] + " > " + tags[1] + "°C");
                chooseProgramDescription.setTextColor(getResources().getColor(R.color.text_color_dark_gray));

            }
        });

        wpd.bindCancel(new Runnable() {
            @Override
            public void run() {

            }
        });

        wpd.show();
    }

    public void chooseTime() {
        WasherTimeDialog wtd = new WasherTimeDialog(getActivity());

        wtd.bindOK(new CustomDialog.Callback() {
            @Override
            public void run(int[] ids, String[] tags) {
                startTimeDescription.setText(tags[0]+", kl. "+tags[1]+":"+tags[2]);
                dayIndex = ids[0];
                hourIndex = ids[1];
                minIndex = ids[2];

                Log.d("hej", ids[0]+" "+ids[1]+" "+ids[2]);

                Calendar rightNow = Calendar.getInstance();
                long tempTime = rightNow.getTimeInMillis();

                rightNow.add(Calendar.DATE, ids[0]);
                rightNow.set(Calendar.HOUR_OF_DAY, ids[1]);
                rightNow.set(Calendar.MINUTE, ids[2]*5);



                time = rightNow.getTimeInMillis();

                if((time-tempTime)<0){
                    startTimeDescription.setTextColor(getResources().getColor(R.color.tieto_orange));
                    time = -1;
                }else{
                    startTimeDescription.setTextColor(getResources().getColor(R.color.text_color_light_gray));
                    startTimeTitle.setTextColor(getResources().getColor(R.color.text_color_light_gray));

                }


            }
        });

        wtd.bindCancel(new Runnable() {
            @Override
            public void run() {

            }
        });
        wtd.show();
        wtd.setIds(dayIndex,hourIndex,minIndex);
    }

    private boolean useWind(){
        return windSwitch.isChecked();
    }

    private boolean cheapPrice(){
        return priceSwitch.isChecked();
    }

    private void save(){
        topStartBar.saveState("StartTopBar");
        Storage.saveString(Storage.LAST_PROGRAM_STRING, programName);
        Storage.saveString(Storage.LAST_DEGREE_STRING, degreeName);
        Storage.saveBoolean(Storage.USE_WIND, useWind());
        Storage.saveBoolean(Storage.CHEAP_PRICE, cheapPrice());
    }

    private void load(){
        topStartBar.loadState("StartTopBar");
        programName = Storage.loadString(Storage.LAST_PROGRAM_STRING,"Välj program");
        degreeName = Storage.loadString(Storage.LAST_PROGRAM_STRING,"Välj grader");
        priceSwitch.setChecked(Storage.loadBoolean(Storage.CHEAP_PRICE, false));
        windSwitch.setChecked(Storage.loadBoolean(Storage.USE_WIND, false));
    }


    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    public static StartFragment create(){
        StartFragment fragment = new StartFragment();
        fragment.setArguments(createBundle(R.layout.layout_start_content, "START"));
        return fragment;
    }
}
