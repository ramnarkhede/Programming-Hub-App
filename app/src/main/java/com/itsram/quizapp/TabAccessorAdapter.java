package com.itsram.quizapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAccessorAdapter extends FragmentPagerAdapter {
    public TabAccessorAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                Java java=new Java();
                return java;
            case 1:
                C c=new C();
                return c;

            case 2:
                Cpp cpp=new Cpp();
                return cpp;

            case 3:
                Python python=new Python();
                return python;

                default:
                    return null;

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:
                return"Java";

            case 1:
                return"C";

            case 2:
                return"Cpp";

            case 3:
                return"Python";
                default:
                    return null;
        }
    }
}
