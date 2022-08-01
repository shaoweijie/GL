package com.hancher.gamelife.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<CustomTabEntity> mFragments;

    public TabViewPagerAdapter(ArrayList<CustomTabEntity> fragments, @NonNull FragmentManager fm) {
        super(fm);
        mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ((TabEntity) mFragments.get(position)).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public static class TabEntity implements CustomTabEntity {
        public Fragment fragment;
        public String title;
        public int selectedIcon;
        public int unSelectedIcon;

        public TabEntity(String title, Fragment fragment, int selectedIcon, int unSelectedIcon) {
            this.title = title;
            this.selectedIcon = selectedIcon;
            this.unSelectedIcon = unSelectedIcon;
            this.fragment = fragment;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return selectedIcon;
        }

        @Override
        public int getTabUnselectedIcon() {
            return unSelectedIcon;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }
}
