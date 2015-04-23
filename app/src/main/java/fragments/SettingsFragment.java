package fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.NavigationBar;
import com.washer.smart.smartwasher.R;

import extra.Storage;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class SettingsFragment extends BaseFragment {

    NavigationBar priceNavBar, notificationNavBar;
    LinearLayout addPrice, setNotification, saveButton;
    EditText setPrice, setTime;


    public static BaseFragment create() {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(createBundle(R.layout.layout_settings_content, "INSTÄLLNINGAR"));
        return fragment;
    }

    @Override
    protected void init(final View view) {
        priceNavBar = NavigationBar.greenWhiteToggle(view);
        notificationNavBar = NavigationBar.greenWhiteToggle(view);

        addPrice = (LinearLayout) view.findViewById(R.id.ll_settings_add_price);
        setPrice = (EditText) view.findViewById(R.id.et_settings_price);
        setNotification = (LinearLayout) view.findViewById(R.id.ll_settings_time_to_remember);
        setTime = (EditText) view.findViewById(R.id.et_settings_reminder_time);
        saveButton = (LinearLayout) view.findViewById(R.id.ll_settings_save_button);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);



        priceNavBar.addView(view.findViewById(R.id.tv_settings_variable), new Runnable() {
            @Override
            public void run() {

                addPrice.setVisibility(View.INVISIBLE);
                setPrice.setEnabled(false);
                imm.hideSoftInputFromWindow(setPrice.getWindowToken(), 0);


            }
        });

        priceNavBar.addView(view.findViewById(R.id.tv_settings_static), new Runnable() {
            @Override
            public void run() {

                addPrice.setVisibility(View.VISIBLE);
                setPrice.setEnabled(true);

            }
        });

        notificationNavBar.addView(view.findViewById(R.id.tv_settings_notify_on), new Runnable() {
            @Override
            public void run() {
                setNotification.setVisibility(View.VISIBLE);
                setTime.setEnabled(true);
            }
        });

        notificationNavBar.addView(view.findViewById(R.id.tv_settings_notify_off), new Runnable() {
            @Override
            public void run() {
                setNotification.setVisibility(View.INVISIBLE);
                setTime.setEnabled(false);
                imm.hideSoftInputFromWindow(setTime.getWindowToken(), 0);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private boolean wantNotify(){
        int state = notificationNavBar.getSelectedId();

        if(state == 0) return true;
        return false;
    }

    private boolean isStatic(){
        int state = priceNavBar.getSelectedId();

        if(state == 1) return true;
        return false;
    }

    private void save(){
        priceNavBar.saveState("priceNavBar");
        notificationNavBar.saveState("notificationNavBar");

        String timeString = setTime.getText().toString();
        int reminderTime = Integer.parseInt(timeString);
        Storage.saveInt(Storage.REMINDER_TIME, reminderTime);

        String priceString = setPrice.getText().toString();
        float staticPrice = Float.parseFloat(priceString);
        Storage.saveFloat(Storage.STATIC_PRICE, staticPrice);

        Storage.saveBoolean(Storage.NOTIFY_STATE, wantNotify());
        Storage.saveBoolean(Storage.IS_STATIC, isStatic());

    }

    private void load(){
        priceNavBar.loadState("priceNavBar");
        notificationNavBar.loadState("notificationNavBar");

        int reminderTime = Storage.loadInt(Storage.REMINDER_TIME, 15);
        setTime.setText(""+reminderTime);

        float staticPrice = Storage.loadFloat(Storage.STATIC_PRICE, 25);
        setPrice.setText(""+staticPrice);

    }




    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }
}
