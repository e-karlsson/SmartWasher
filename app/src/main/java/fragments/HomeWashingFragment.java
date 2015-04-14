package fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.washer.smart.smartwasher.R;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class HomeWashingFragment extends BaseFragment {
    LinearLayout cancelButton;
    @Override
    protected void init(View view) {
        cancelButton = (LinearLayout) view.findViewById(R.id.ll_start_washing_cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyViewPager.getInstance().setCurrentItem(MyViewPager.HOME_SLEEP,false);
            }
        });
    }

    public static BaseFragment create() {
        HomeWashingFragment fragment = new HomeWashingFragment();
        fragment.setArguments(createBundle(R.layout.layout_home_washing_content, "HEM"));
        return fragment;
    }

}
