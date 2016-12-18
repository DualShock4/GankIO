package dualshock.gabriel.gankio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import dualshock.gabriel.gankio.ui.fragment.BaseFragment;
import dualshock.gabriel.gankio.ui.fragment.GirlsFragment;
import dualshock.gabriel.gankio.ui.fragment.KnowledgeFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {

    private final String[] mTitles  =new String[]{"安卓", "iOS", "前端", "图片", "视频"};
    private final String[] mType = new String[]{"Android", "iOS", "前端", "福利", "休息视频"};
    private final FragmentManager fragmentManager;

    public HomeAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 3) {
            return GirlsFragment.newInstance(mType[position]);
        }
        return KnowledgeFragment.newInstance(mType[position]);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
