package com.example.kakao.seoul_app.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kakao on 2017. 10. 1..
 */

public class ListPagerAdapter extends FragmentPagerAdapter {

    private static int PAGE_NUMBER = 2;

    public ListPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return ListFragment.newInstance();
            case 1:
                return MapFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_NUMBER;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "목록";
            case 1:
                return "지도";
            default:
                return null;
        }
    }

}
