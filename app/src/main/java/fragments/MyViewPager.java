package fragments;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import extra.LiveData;

/**
 * Created by xxottosl on 2015-04-13.
 */
public class MyViewPager extends ViewPager {

    public static final int HOME_SLEEP = 0;
    public static final int HOME_SCHEDULE = 1;
    public static final int HOME_WASHING = 2;
    public static final int HOME_DONE = 3;
    public static final int START = 4;
    public static final int PROGRAM = 5;
    public static final int HISTORY = 6;
    public static final int SETTINGS = 7;

    private static final long STICKY_TIME = 10000;
    private static long lastHomeSwitch;

    private int lastItem = 0;
    private TextView title;

    private static MyViewPager self;

    public static void init(MyViewPager instance, TextView titleView){
        self = instance;
        self.setTitle(titleView);
    }

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

        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        BaseFragment lastFragment = MyFragmentAdapter.getFragment(lastItem);
        lastFragment.onPause();
        super.setCurrentItem(item, smoothScroll);
        BaseFragment fragment = MyFragmentAdapter.getFragment(item);
        fragment.onResume();
        lastItem = item;
        title.setText(fragment.getTitleName());
        if(item <= HOME_DONE)
            lastHomeSwitch = System.currentTimeMillis();
    }

    public static MyViewPager getInstance(){
        return self;
    }

    public static void goHome(){
        int current = self.getCurrentItem();
        int homeItem = LiveData.getState();
        if(current != homeItem)
               self.setCurrentItem(homeItem);
    }

    public static void changeHome(){
        int current = self.getCurrentItem();
        int homeItem = LiveData.getState();
        if(current <= HOME_DONE){
            if(current == homeItem){
               MyFragmentAdapter.getFragment(current).update();
            }else if (System.currentTimeMillis() - lastHomeSwitch >= STICKY_TIME){
                self.setCurrentItem(homeItem);
            }
        }
    }

   @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }


}
