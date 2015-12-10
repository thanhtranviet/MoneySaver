package com.k22.nhom1.moneysaver.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.k22.nhom1.moneysaver.R;
import com.k22.nhom1.moneysaver.adapter.ViewPagerAdapter;

/**
 * Created by thanh on 07/12/2015.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = "HOME_FRAGMENT_TAG";
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setClickable(true);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new OverviewTabFragment(), "All");
        adapter.addFragment(new IncomeTabFragment(), "Income");
        viewPager.setAdapter(adapter);
    }
}