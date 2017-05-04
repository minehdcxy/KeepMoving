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
    List<String> titleList = new ArrayList<>();

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm);
        this.viewList = list;
        this.titleList = titleList;
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

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
