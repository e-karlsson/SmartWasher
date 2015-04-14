package fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import dialogs.WasherTimeDialog;
import extra.Storage;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class StartFragment extends BaseFragment {

    NavigationBar topStartBar;
    LinearLayout extraParams;
    LinearLayout startButton;
    LinearLayout startTimeLayout;
    CharSequence[] programs = {"Bomull", "Vit/Kulör", "Snabbtvätt"};
    CharSequence[] degrees = {"30 C", "40 C", "60 C"};
    String programName = "Välj program";
    String degreeName = "Välj grader";
    Switch priceSwitch, windSwitch;



    @Override
    protected void init(View view) {
        topStartBar = NavigationBar.greenWhiteToggle(view);
        extraParams = (LinearLayout) view.findViewById(R.id.ll_extra_params);
        startButton = (LinearLayout) view.findViewById(R.id.ll_start_start_button);
        priceSwitch = (Switch) view.findViewById(R.id.sw_start_cheapest);
        windSwitch = (Switch) view.findViewById(R.id.sw_start_wind);
        startTimeLayout = (LinearLayout) view.findViewById(R.id.ll_start_time);

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


    private void chooseProgram(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Välj program");
        builder.setSingleChoiceItems(programs, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedProgram = programs[which].toString();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        builder.show();
    }

    private void onStartClick(){}





    private void chooseDegree(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Välj grader");
        builder.setSingleChoiceItems(degrees, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedDegrees = degrees[which].toString();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        builder.show();
    }

    public void chooseTime() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.layout_washer_picker, null))
                // Add action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("avbryt", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


       // builder.show();

        WasherTimeDialog wtd = new WasherTimeDialog(getActivity());
        wtd.show();
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
