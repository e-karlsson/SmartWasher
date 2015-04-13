package fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xxkarlue on 2015-04-13.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private static int position = 0;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public static int getPosition(){
        return position;
    }

    public static void setPosition(int position){
        MyFragmentAdapter.position = position;
    }
}
