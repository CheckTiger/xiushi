package com.lanou.olddriver.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;


public class ImgVPAdapter extends PagerAdapter {

	List<ImageView> list;
	
	public ImgVPAdapter(List<ImageView> list) {
		super();
		this.list = list;
	}
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
		return list.get(position);
	}
	
	@Override

	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView((ImageView) object);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

}
