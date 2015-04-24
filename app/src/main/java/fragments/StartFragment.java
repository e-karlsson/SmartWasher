package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import java.util.Calendar;

import dialogs.CustomDialog;
import dialogs.WasherProgramDialog;
import dialogs.WasherTimeDialog;
import extra.FontCache;
import extra.LiveData;
import extra.MenuBar;
import extra.Storage;
import extra.WasherInfo;
import model.LiveRecord;
import model.StartStatus;
import model.Status;
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
    int programIndex = 0;
    int degreeIndex = 0;
    int programTime;
    boolean badTime = false;

    NavigationBar topStartBar;
    LinearLayout extraParams;
    LinearLayout startButton;
    LinearLayout startTimeLayout;
    LinearLayout startChooseProgram;
    TextView startTimeDescription, chooseProgramDescription, startTimeTitle;
    String programName = "Välj tvättprogram";
    String degreeName = "";
    Switch priceSwitch, windSwitch;




    @Override
    protected void init(View view) {
        TextView startTimeFa = (TextView) view.findViewById(R.id.tv_start_time_fa);
        TextView startProgramFa = (TextView) view.findViewById(R.id.tv_start_program_fa);
        TextView windIcon = (TextView) view.findViewById(R.id.tv_start_info_wind);
        TextView priceIcon = (TextView) view.findViewById(R.id.tv_start_info_cheap);
        FontCache.setCustomFont(startProgramFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(startTimeFa, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(windIcon, getResources().getString(R.string.fa), getActivity());
        FontCache.setCustomFont(priceIcon, getResources().getString(R.string.fa), getActivity());

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


        priceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPriceInfo();
            }
        });

        windIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindInfo();
            }
        });


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
        int tempMin;

        boolean fromStart = Storage.loadBoolean(Storage.FROM_START, true);
        Calendar rightNow = Calendar.getInstance();
        String min;

        if(fromStart == false){
            rightNow.setTimeInMillis(Storage.loadLong(Storage.CHANGE_TIME, rightNow.getTimeInMillis()));
            min = ""+rightNow.get(Calendar.MINUTE);
        }else {

            tempMin = rightNow.get(Calendar.MINUTE);
            tempMin = ((tempMin / 5) + 1) * 5;

            if (tempMin == 60) {
                tempMin = 0;
                rightNow.add(Calendar.HOUR_OF_DAY, 1);
            }
            rightNow.set(Calendar.MINUTE, tempMin);

            dayIndex = 0;
            hourIndex = rightNow.get(Calendar.HOUR_OF_DAY);
            minIndex = rightNow.get(Calendar.MINUTE) / 5;
            min = tempMin + "";
            if (tempMin < 10) min = "0" + min;
        }
        startTimeDescription.setText("idag, kl. "+rightNow.get(Calendar.HOUR_OF_DAY)+":"+min);




        time = rightNow.getTimeInMillis();
    }

    private void setProgram(){
        if(degreeName.equals("")){
            chooseProgramDescription.setText(programName);
        }else{
            chooseProgramDescription.setText(programName + " > " + degreeName + "°C");
        }
    }

    private boolean validateFields(){
        boolean isOK = true;
        if(badTime){
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


            LiveData.getLiveRecord().setProgramInfo(status.getProgramInfo());
            MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SCHEDULE,false);
        }

        @Override
        public void onError(WasherError error) {
            Log.d("hej", "Nope! Couldn't start server..");

        }
    };


    private void onStartClick(){
        if(validateFields() == false) return;
        programTime = WasherInfo.getWashTime(programName, degreeName);
        if(topStartBar.getSelectedId() == 0){
            WasherService.startAt(time, programTime, programName, degreeName, callback);
        }else{
            WasherService.startReadyAt(time, programTime, programName, degreeName, priceSwitch.isChecked(), windSwitch.isChecked(), callback);
        }



    }




    private void chooseProgram(){
        WasherProgramDialog wpd = new WasherProgramDialog(getActivity());

        wpd.bindOK(new CustomDialog.Callback() {
            @Override
            public void run(int[] ids, String[] tags) {
                chooseProgramDescription.setText(tags[0] + " > " + tags[1] + "°C");
                chooseProgramDescription.setTextColor(getResources().getColor(R.color.text_color_dark_gray));
                programName = tags[0];
                degreeName = tags[1];
                programIndex = ids[0];
                degreeIndex = ids[1];

                Storage.saveInt(Storage.LAST_PROGRAM_ID, programIndex);
                Storage.saveInt(Storage.LAST_DEGREE_ID, degreeIndex);

            }
        });

        wpd.bindCancel(new Runnable() {
            @Override
            public void run() {

            }
        });

        programIndex = Storage.loadInt(Storage.LAST_PROGRAM_ID, programIndex);
        degreeIndex = Storage.loadInt(Storage.LAST_DEGREE_ID, degreeIndex);

        wpd.show();
        wpd.setStartTime(time, topStartBar.getSelectedId() == 1);
        wpd.setIds(programIndex, degreeIndex);

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
                   // time = -1;
                    badTime = true;
                }else{
                    startTimeDescription.setTextColor(getResources().getColor(R.color.text_color_dark_gray));
                    startTimeTitle.setTextColor(getResources().getColor(R.color.text_color_dark_gray));
                    badTime = false;
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
        programName = Storage.loadString(Storage.LAST_PROGRAM_STRING, "Välj tvättprogram");
        degreeName = Storage.loadString(Storage.LAST_DEGREE_STRING,"");
        priceSwitch.setChecked(Storage.loadBoolean(Storage.CHEAP_PRICE, false));
        windSwitch.setChecked(Storage.loadBoolean(Storage.USE_WIND, false));
    }

    private void showPriceInfo(){
        showDialog("Om billigast el", getResources().getString(R.string.price_info));
    }

    private void showWindInfo(){
        showDialog("Om vindkraft",getResources().getString(R.string.wind_info));
    }

    private void showDialog(String title, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(info);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onResume() {
        MenuBar.showBackButton();
        MenuBar.setBackAction(new Runnable() {
            @Override
            public void run() {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP);
            }
        });
        load();
        setProgram();
        setCurrentTime();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        MenuBar.hideBackButton();
        save();
    }

    public static StartFragment create(){
        StartFragment fragment = new StartFragment();
        fragment.setArguments(createBundle(R.layout.layout_start_content, "START"));
        return fragment;
    }
}
