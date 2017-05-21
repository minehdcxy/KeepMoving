package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raytine on 2017/4/30.
 */

public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> viewList;

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.viewList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return viewList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }


}
