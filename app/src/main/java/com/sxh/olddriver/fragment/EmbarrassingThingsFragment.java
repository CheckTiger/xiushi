package com.sxh.olddriver.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.EmbarrassingCommonPagerAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/23.
 */
public class EmbarrassingThingsFragment extends BaseFragment implements View.OnClickListener {
    private Button btn_exclusive;
    private Button btn_video;
    private Button btn_plain_text;
    private Button btn_plain_image;
    private Button btn_essence;
    private Button btn_through;
    private Button current_button;
    private ViewPager embarrassing_vp;
    private ArrayList<BaseFragment> embarrassing_list;
    private EmbarrassingCommonPagerAdapter embarrassing_adapter;
    private HorizontalScrollView embarrassing_hs;


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_embarrassing_things, null);
        btn_exclusive = (Button) view.findViewById(R.id.embarrassing_things_btn_exclusive);
        btn_video = (Button) view.findViewById(R.id.embarrassing_things_btn_video);
        btn_plain_text = (Button) view.findViewById(R.id.embarrassing_things_btn_plain_text);
        btn_plain_image = (Button) view.findViewById(R.id.embarrassing_things_btn_plain_image);
        btn_essence = (Button) view.findViewById(R.id.embarrassing_things_btn_essence);
        btn_through = (Button) view.findViewById(R.id.embarrassing_things_btn_through);
        embarrassing_hs = (HorizontalScrollView) view.findViewById(R.id.embarrassing_hs);
        embarrassing_vp = (ViewPager) view.findViewById(R.id.embarrassing_things_vp);
        embarrassing_list = new ArrayList<>();
        setListData();
        embarrassing_adapter = new EmbarrassingCommonPagerAdapter(getChildFragmentManager(), embarrassing_list, getActivity());
        current_button = btn_exclusive;//实例化当前button
        setCurrentIndicator(current_button);//设置当前button属性
        embarrassing_vp.setAdapter(embarrassing_adapter);
        btn_exclusive.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        btn_plain_text.setOnClickListener(this);
        btn_plain_image.setOnClickListener(this);
        btn_essence.setOnClickListener(this);
        btn_through.setOnClickListener(this);
        return view;
    }

    /**
     * 设置当前Fragment的数据
     */
    private void setListData() {
        ExclusiveFragment exclusiveFrag = new ExclusiveFragment();//专享
        VideoFragment videoFrag = new VideoFragment();
        PlainTextFragment plainTextFrag = new PlainTextFragment();
        PlainImageFragment plainImageFrag = new PlainImageFragment();
        EssenceFragment essenceFrag = new EssenceFragment();
        ThroughFragment throughFrag = new ThroughFragment();
        embarrassing_list.add(exclusiveFrag);
        embarrassing_list.add(videoFrag);
        embarrassing_list.add(plainTextFrag);
        embarrassing_list.add(plainImageFrag);
        embarrassing_list.add(essenceFrag);
        embarrassing_list.add(throughFrag);
    }

    @Override
    public void setData() {
        embarrassing_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", "onPageScrolled: " + position + "=============" + positionOffset + "+++++++++++" + positionOffsetPixels);
                boolean isLeft = positionOffsetPixels == 0;//判断是否是停止状态
                View view = ((LinearLayout) embarrassing_hs.getChildAt(0)).getChildAt(0);
                int width = view.getRight() - view.getLeft();
                int moveX = (int) (width * positionOffset);
                animateToTab(position, moveX, isLeft);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "animateToTab:onPageSelected执行 ");
                Button btn = null;
                switch (position) {
                    case 0:
                        btn = btn_exclusive;
                        break;
                    case 1:
                        btn = btn_video;
                        break;
                    case 2:
                        btn = btn_plain_text;
                        break;
                    case 3:
                        btn = btn_plain_image;
                        break;
                    case 4:
                        btn = btn_essence;
                        break;
                    case 5:
                        btn = btn_through;
                        break;
                }
                setCurrentIndicator(btn);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     *滑动vp时联动头标题
     * @param position
     * @param distance
     * @param isLeft
     */
    private void animateToTab(int position, int distance, boolean isLeft) {
        LinearLayout linearLayout = (LinearLayout) embarrassing_hs.getChildAt(0);
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Log.i("TAG", "animateToTab: " + i + ":childCount:" + childCount);
            boolean isChange = i == position;
            if (isChange) {
                View view = linearLayout.getChildAt(i);
                int toX = 0;
                Log.i("TAG", "animateToTab-----"+isLeft);
                if (isLeft) {
                    toX = view.getLeft() - distance;
                } else {
                    toX = view.getLeft() + distance;
                }
                embarrassing_hs.setScrollX(toX);
            }
        }

    }

    @Override
    public void onClick(View view) {
        int position = 0;
        switch (view.getId()) {
            case R.id.embarrassing_things_btn_exclusive:
                position = 0;
                break;
            case R.id.embarrassing_things_btn_video:
                position = 1;
                break;
            case R.id.embarrassing_things_btn_plain_text:
                position = 2;
                break;
            case R.id.embarrassing_things_btn_plain_image:
                position = 3;
                break;
            case R.id.embarrassing_things_btn_essence:
                position = 4;
                break;
            case R.id.embarrassing_things_btn_through:
                position = 5;
                break;
        }
        setFragmentSelected(position);//切换VP显示的Fragment
        setCurrentIndicator((Button) view);
    }

    private void setFragmentSelected(int position) {
        embarrassing_vp.setCurrentItem(position);
    }

    /**
     * 设置当前标题的属性
     *
     * @param button
     */
    private void setCurrentIndicator(Button button) {
        current_button.setTextColor(getResources().getColor(R.color.lightWhite));
        current_button.setTextSize(14);
        current_button = button;
        current_button.setTextColor(getResources().getColor(R.color.white));
        current_button.setTextSize(18);
    }
}
