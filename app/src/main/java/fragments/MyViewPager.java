package fragments;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by xxottosl on 2015-04-13.
 */
public class MyViewPager extends ViewPager {

    private int lastItem = 0;
    private TextView title;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }

    public void setTitle(TextView titleTv){
        title = titleTv;
    }


    @Override
    public void setCurrentItem(int item) {
        BaseFragment lastFragment = MyFragmentAdapter.getFragment(lastItem);
        lastFragment.onPause();      
        super.setCurrentItem(item);
        BaseFragment fragment = MyFragmentAdapter.getFragment(item);
        fragment.onResume();
        lastItem = item;
        title.setText(fragment.getTitleName());
    }
}
