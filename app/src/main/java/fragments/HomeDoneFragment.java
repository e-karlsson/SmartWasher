package fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeDoneFragment extends BaseFragment {
    LinearLayout okButton;

    @Override
    protected void init(View view) {
        okButton = (LinearLayout) view.findViewById(R.id.ll_start_done_ok);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
            }
        });

    }

    public static BaseFragment create() {
        HomeDoneFragment fragment = new HomeDoneFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_done_content, "HEM"));
        return fragment;
    }

}
