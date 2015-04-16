package fragments;

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
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
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
                if(record == null) return;

                Timer.TimeInfo timeInfo = Timer.translate(record.getProgramInfo().getEndTime());



                infoContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(WasherError error) {

            }
        });
    }

    public static BaseFragment create() {
        HomeDoneFragment fragment = new HomeDoneFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_done_content, "HEM"));
        return fragment;
    }

}
