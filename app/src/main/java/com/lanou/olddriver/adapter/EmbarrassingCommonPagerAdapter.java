package com.lanou.olddriver.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lanou.olddriver.fragment.BaseFragment;

import java.util.List;

/**
 * Created by user on 2016/7/24.
 */
public class EmbarrassingCommonPagerAdapter extends FragmentPagerAdapter {
    List<BaseFragment> list;
    Context context;

    public EmbarrassingCommonPagerAdapter(FragmentManager fm, List<BaseFragment> list, Context context) {
        super(fm);
        this.list = list;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
