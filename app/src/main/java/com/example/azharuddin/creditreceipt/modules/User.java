package com.example.azharuddin.creditreceipt.modules;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.azharuddin.creditreceipt.R;
import com.example.azharuddin.creditreceipt.activity.LandingActivity;
import com.example.azharuddin.creditreceipt.fragment.addFragment;
import com.example.azharuddin.creditreceipt.fragment.viewFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by azharuddin on 24/7/17.
 */

@SuppressWarnings("unchecked")
public class User {
    public void evaluate(LandingActivity activity, View itemView) {
        ViewPager viewPager = itemView.findViewById(R.id.user_viewpager);
        setupViewPager(activity, viewPager);

        TabLayout tabLayout = itemView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(LandingActivity activity, ViewPager viewPager) {
        User.UserViewPagerAdapter adapter = new User.UserViewPagerAdapter(activity.getSupportFragmentManager());
        adapter.addFragment(addFragment.newInstance("User"), "ADD");
        adapter.addFragment(viewFragment.newInstance("User"), "View");
        viewPager.setAdapter(adapter);
    }

    public class UserViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        UserViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
