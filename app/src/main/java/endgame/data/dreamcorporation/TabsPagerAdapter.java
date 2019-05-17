package endgame.data.dreamcorporation;

import android.support.v4.app.*;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amal on 27/03/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    private int NUM_ITEMS = 3;
//    private String[] titles= new String[]{"Home", "Network","Profile"};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return  NUM_ITEMS ;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new NetworkFragment();
            case 2:
                return new ProfileFragment();
            default:
                return null;
        }
//        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }


    // Returns the page title for the top indicator
////    @Override
//    public CharSequence getPageTitle(int position) {
//        return  titles[position];
//    }
}

