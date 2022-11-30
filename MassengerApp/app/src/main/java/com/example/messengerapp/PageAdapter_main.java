package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter_main extends FragmentPagerAdapter{

    int tabcount;
    public PageAdapter_main(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: return new chatsFragment1();
            case 1: return new groupFragment2();
            case 2: return new cameraFragment3();
            case 3: return new storyFragment3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}

