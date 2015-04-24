package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.washer.smart.smartwasher.R;

import java.util.Calendar;

import extra.FontCache;
import extra.LiveData;
import extra.Timer;
import model.LiveRecord;
import model.Status;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeWashingFragment extends BaseFragment {
    LinearLayout cancelButton;
    TextView timeLeft, currentEnergy, extraName, timeDone, extraDescription;
    @Override
    protected void init(View view) {
        cancelButton = (LinearLayout) view.findViewById(R.id.ll_start_washing_cancel);

        timeLeft = (TextView) view.findViewById(R.id.tv_start_running_time_left);
        currentEnergy = (TextView) view.findViewById(R.id.tv_start_running_watt);
        extraName = (TextView) view.findViewById(R.id.tv_start_running_extra);
        timeDone = (TextView) view.findViewById(R.id.tv_start_running_ready_at);
        extraDescription = (TextView) view.findViewById(R.id.tv_start_running_extra_description);
        FontCache.setCustomFont(extraDescription, getResources().getString(R.string.fa), getActivity());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showCancelDialog();

            }
        });
    }

    private void setData(){
        if(LiveData.getLiveRecord() == null) return;

        boolean wind = LiveData.getLiveRecord().getProgramInfo().isWind();
        boolean cheapest = LiveData.getLiveRecord().getProgramInfo().isLowPrice();

        long endTime = LiveData.getLiveRecord().getProgramInfo().getEndTime();

        long diff = (endTime-System.currentTimeMillis()) + 60000;
        long diffMinutes = diff / (60*1000) % 60;
        long diffHours = diff / (60*60*1000) % 24;



        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(endTime);

        timeLeft.setText(Timer.makeTwo(""+diffHours)+":"+Timer.makeTwo(""+diffMinutes));
        currentEnergy.setText(LiveData.getLiveRecord().getEnergy()+" W");
        timeDone.setText(""+Timer.makeTwo(""+calendar.get(Calendar.HOUR_OF_DAY))+":"+Timer.makeTwo(""+calendar.get(Calendar.MINUTE)));

        if(wind){
            extraName.setText("Vindkraft");
            extraDescription.setText(getResources().getText(R.string.wind_icon));
        }else if(cheapest){
            extraName.setText("Billigast el");
            extraDescription.setText(getResources().getText(R.string.money_icon));

        }else{
            extraName.setText("");
            extraDescription.setText("");
        }

    }


    private void showCancelDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Vill du verkligen avbryta den pågående tvätten?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                WasherService.stop(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(WasherError error) {
                        Toast.makeText(getActivity(), "Kunde inte ansluta till servern!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

            }
        });
        builder.setNegativeButton("Nej", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onResume() {
        setData();
        super.onResume();
    }

    @Override
    public void update() {
        setData();
    }

    public static BaseFragment create() {
        HomeWashingFragment fragment = new HomeWashingFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_washing_content, "HEM"));
        return fragment;
    }

}
