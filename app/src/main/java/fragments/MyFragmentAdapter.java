package fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by xxottosl on 2015-04-13.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragments;

    private static SparseArray<BaseFragment> initFragments = new SparseArray<>();

    public MyFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments){
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


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseFragment bf = (BaseFragment) super.instantiateItem(container, position);
        initFragments.put(position, bf);
        return bf;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        initFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public static BaseFragment getFragment(int position){
        return initFragments.get(position);
    }
}
