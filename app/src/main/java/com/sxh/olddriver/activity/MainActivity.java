package com.sxh.olddriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.sxh.olddriver.R;
import com.sxh.olddriver.fragment.EmbarrassingCircleFragment;
import com.sxh.olddriver.fragment.EmbarrassingThingsFragment;
import com.sxh.olddriver.fragment.LiveFragment;
import com.sxh.olddriver.fragment.MeFragment;
import com.sxh.olddriver.fragment.SmallPagerFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private ArrayList<Fragment> fragments;//用于存放需要显示的Fragment
    private EmbarrassingThingsFragment embarrassingThingsFragment;
    private EmbarrassingCircleFragment embarrassingCircleFragment;
    private LiveFragment liveFragment;
    private SmallPagerFragment smallPagerFragment;
    private MeFragment meFragment;
    private String icon;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        icon=intent.getStringExtra("icon");
        name=intent.getStringExtra("name");
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_bottom_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_qiushi_normal, "糗事").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_qiuyoucircle_normal, "糗友圈").setActiveColorResource(R.color.grey))
                .addItem(new BottomNavigationItem(R.drawable.ic_live_normal, "直播").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_message_normal, "小纸条").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_mine_normal, "我").setActiveColorResource(R.color.brown))
                .setFirstSelectedPosition(0).setBarBackgroundColor(R.color.kaqibu)
                .initialise();
        fragments = getFragments();
        setDefaultFragment(embarrassingThingsFragment);
        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认显示Fragment
     */
    private void setDefaultFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_frame_layout, fragment);
        transaction.commit();
    }

    /**
     * 给list集合添加数据:Fragment
     * @return
     */
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        embarrassingThingsFragment = new EmbarrassingThingsFragment();
        embarrassingCircleFragment = new EmbarrassingCircleFragment();
        liveFragment = new LiveFragment();
        smallPagerFragment = new SmallPagerFragment();
        meFragment = new MeFragment();
        fragments.add(embarrassingThingsFragment);
        fragments.add(embarrassingCircleFragment);
        fragments.add(liveFragment);
        fragments.add(smallPagerFragment);
        fragments.add(meFragment);

        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    ft.replace(R.id.main_frame_layout, fragment);
                } else {
                    ft.add(R.id.main_frame_layout, fragment);
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    /*@Override
    protected void onResume() {
        super.onResume();
        if (Utilss.Re==1){
            meFragment= (MeFragment) fragments.get(4);
            setDefaultFragment(meFragment);
            onTabSelected(4);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilss.Re=0;
        //onTabSelected(4);
    }*/
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        arguments.someAction(resultCode);
    }
    public interface Arguments{
        void someAction(int a);
    }
    Arguments arguments;
    public void setSomeAction(Arguments action){
        this.arguments = action;
    }
}
