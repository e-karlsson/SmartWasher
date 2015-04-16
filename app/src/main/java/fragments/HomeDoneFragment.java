package fragments;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.washer.smart.smartwasher.R;

import extra.Timer;
import model.WashRecord;
import model.WashRecords;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

import model.Status;
import sdk.CallBack;
import sdk.WasherError;
import sdk.WasherService;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeDoneFragment extends BaseFragment {
    LinearLayout okButton, infoContainer;

    TextView wattText, priceText, endText, extraText, extraTitle;


    @Override
    protected void init(View view) {
        okButton = (LinearLayout) view.findViewById(R.id.ll_start_done_ok);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WasherService.setDone(new CallBack<Status>() {
                    @Override
                    public void onSuccess(Status status) {
                        Log.d("Washer", "I've sent DONE.");
                    }

                    @Override
                    public void onError(WasherError error) {
                        Log.d("Washer", "Unsuccessfully sent DONE.");
                    }
                });
            }
        });

        wattText = (TextView) view.findViewById(R.id.tv_start_done_watt);
        priceText = (TextView) view.findViewById(R.id.tv_start_done_cost);
        endText = (TextView) view.findViewById(R.id.tv_start_done_ready_time);
        extraText = (TextView) view.findViewById(R.id.tv_start_done_extra);
        extraTitle = (TextView) view.findViewById(R.id.tv_start_done_extra_title);
        infoContainer = (LinearLayout) view.findViewById(R.id.ll_start_done_info_container);

    }

    private void updateInfo(){
        infoContainer.setVisibility(View.INVISIBLE);
        WasherService.getHistoryByAmount(1, new CallBack<WashRecords>() {
            @Override
            public void onSuccess(WashRecords washRecords) {

                WashRecord record = washRecords.getRecords().get(0);
                if (record == null) return;

                Timer.TimeInfo timeInfo = Timer.translate(record.getProgramInfo().getEndTime());

                float energy = record.getTotalEnergy();
                float price = record.getPrice();
                boolean wind = record.getProgramInfo().isWind();
                boolean lowPrice = record.getProgramInfo().isLowPrice();

                if (!wind && !lowPrice) {
                    extraTitle.setVisibility(View.INVISIBLE);
                    extraText.setText("");
                } else {
                    extraTitle.setVisibility(View.VISIBLE);
                }

                if (wind) extraText.setText("Vindkraft");
                if (lowPrice) extraText.setText("Billigast el");

                endText.setText(timeInfo.getHour() + ":" + timeInfo.getMinute());

                wattText.setText(energy / 1000 / 3600 + " kWh");

                float totalPrice = energy * price / 1000 / 3600;

                priceText.setText(totalPrice + " öre");


                infoContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(WasherError error) {
                Log.d("ERROR", error.getReason());
            }
        });
    }

    @Override
    public void onResume() {
        updateInfo();
        super.onResume();

    }

    public static BaseFragment create() {
        HomeDoneFragment fragment = new HomeDoneFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_done_content, "HEM"));
        return fragment;
    }

}
