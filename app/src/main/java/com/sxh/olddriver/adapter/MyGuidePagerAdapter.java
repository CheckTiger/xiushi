package com.sxh.olddriver.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by user on 2016/7/22.
 */
public class MyGuidePagerAdapter extends PagerAdapter {


    List<ImageView> list;

    public MyGuidePagerAdapter(List<ImageView> list) {
        this.list = list;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));

        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
//        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
