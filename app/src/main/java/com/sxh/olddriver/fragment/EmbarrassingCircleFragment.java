package com.sxh.olddriver.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.EmbarrassingCommonPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/23.
 * 糗友圈
 */
public class EmbarrassingCircleFragment extends BaseFragment implements View.OnClickListener {
    private Button btn_next_door;
    private Button btn_have_powder;
    private Button btn_video;
    private Button btn_topic;
    private Button current_button;
    private ViewPager embarrassing_circle_vp;
    private List<BaseFragment> embarrassing_circle_list;
    private EmbarrassingCommonPagerAdapter embarrassing_circle_adapter;
    private Button circle_btn;
    private LinearLayout circle_hello;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_embarrassing_circle, null);
        btn_next_door = (Button) view.findViewById(R.id.embarrassing_circle_btn_next);
        btn_have_powder = (Button) view.findViewById(R.id.embarrassing_circle_btn_have_powder);
        btn_video = (Button) view.findViewById(R.id.embarrassing_circle_btn_video_circle);
        circle_btn = (Button) view.findViewById(R.id.embarrassing_circle_btn_open);
        circle_hello = (LinearLayout) view.findViewById(R.id.embarrassing_circle_ll_hello);
        btn_topic=(Button)view.findViewById(R.id.embarrassing_circle_btn_topic);
        current_button = btn_next_door;
        setCurrentIndicator(current_button);
        embarrassing_circle_vp = (ViewPager) view.findViewById(R.id.embarrassing_circle_vp);
        embarrassing_circle_list = new ArrayList<>();
        setListData();
        embarrassing_circle_adapter = new EmbarrassingCommonPagerAdapter(getChildFragmentManager(), embarrassing_circle_list, getActivity());
        embarrassing_circle_vp.setAdapter(embarrassing_circle_adapter);
        btn_next_door.setOnClickListener(this);
        btn_have_powder.setOnClickListener(this);
        btn_video.setOnClickListener(this);
        btn_topic.setOnClickListener(this);
        //实现欢迎界面的隐藏和ViewPager的显示
        circle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                embarrassing_circle_vp.setVisibility(View.VISIBLE);
                circle_hello.setVisibility(View.GONE);
            }
        });

        return view;
    }

    /**
     * 给adapter设置数据,并添加进去
     */
    private void setListData() {
        NextDoorFragment nextDoorFrag = new NextDoorFragment();
        HavePowderFragment havePowderFrag = new HavePowderFragment();
        CircleVideoFragment circleVideoFrag = new CircleVideoFragment();
        TopicFragment topicFrag = new TopicFragment();
        embarrassing_circle_list.add(nextDoorFrag);
        embarrassing_circle_list.add(havePowderFrag);
        embarrassing_circle_list.add(circleVideoFrag);
        embarrassing_circle_list.add(topicFrag);
    }

    @Override
    public void setData() {
        //糗友圈滑动监听
        embarrassing_circle_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Button button = null;
                switch (position) {
                    case 0:
                        button = btn_next_door;
                        break;
                    case 1:
                        button = btn_have_powder;
                        break;
                    case 2:
                        button = btn_video;
                        break;
                    case 3:
                        button = btn_topic;
                        break;
                }
                setCurrentIndicator(button);//设置当前标题显示样式
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = 0;
        switch (v.getId()) {
            case R.id.embarrassing_circle_btn_next:
                position = 0;
                break;
            case R.id.embarrassing_circle_btn_have_powder:
                position = 1;
                break;
            case R.id.embarrassing_circle_btn_video_circle:
                position = 2;
                break;
            case R.id.embarrassing_circle_btn_topic:
                position = 3;
                break;
        }
        embarrassing_circle_vp.setCurrentItem(position);
        setCurrentIndicator((Button) v);
    }

    /**
     * 设置当前标题的显示样式
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
