package com.example.rajeevjha.stackoverflow.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rajeevjha.stackoverflow.fragments.HotQuestionFragment;
import com.example.rajeevjha.stackoverflow.fragments.WeeklyQuestionFragment;
import com.example.rajeevjha.stackoverflow.fragments.YourQuestionFragment;

// custom FragmentPagerAdapter
public class QuestionFragmentsPagerAdapter extends FragmentPagerAdapter {
    private static final int FRAGMENT_COUNT = 3;
    private static String pageTitles[] = {"Your#", "Hot", "Week"};
    Fragment fragment1, fragment2, fragment3;

    public QuestionFragmentsPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {

            case 0:
                if (fragment1 == null) {
                    fragment1 = new YourQuestionFragment();
                }
                return fragment1;

            case 1:
                if (fragment2 == null) {
                    fragment2 = new HotQuestionFragment();
                }
                return fragment2;

            case 2:
                if (fragment3 == null) {
                    fragment3 = new WeeklyQuestionFragment();
                }
                return fragment3;


        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
