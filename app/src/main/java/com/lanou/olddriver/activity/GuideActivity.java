package com.lanou.olddriver.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.MyGuidePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends AppCompatActivity {

    private static final int SHARE_APP_TAG = 6;
    ViewPager guideVp;
    List<ImageView> guideList;
    MyGuidePagerAdapter guideAdapter;
    private Button button;
    private ViewPager vp;
    private ArrayList<ImageView> list;
    private LinearLayout ll;
    //关联我们布局当中的view
    //float值后面要加f
    //创建一个透明的渐变的动画对象（0.0~1.0）
    final AlphaAnimation al=new AlphaAnimation(0.0f,1.0f);

    SharedPreferences Preferences;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent=new Intent(GuideActivity.this,NavigationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    finish();
                    break;
                case 1:
                    Intent intent1=new Intent(GuideActivity.this,MainActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                    finish();
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);




        initView();

        //取消顶部状态栏
        endng();
    }

    private void endng() {

    }

    private void initView() {

        //使用SharedPreferences判断是否是第一次进入
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);

                    Message message=new Message();
                    //初始化Preferences
                    Preferences = getSharedPreferences("config", MODE_PRIVATE);
                    boolean isfirst = Preferences.getBoolean("isfirst", true);
                    SharedPreferences.Editor editor = Preferences.edit();
                    editor.putBoolean("isfirst", false);
                    editor.commit();

                    if (isfirst) {
                        message.what=0;
                    } else {
                        message.what=1;
                    }

                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();




        //设置动画执行的时间（方法参数单位是毫秒）
        al.setDuration(3000);
        //给布局中的view设置动画

        ll= (LinearLayout) findViewById(R.id.guid_ll);
        button = (Button) findViewById(R.id.guid_btn);
        vp = (ViewPager) findViewById(R.id.guid_vp);
        list = new ArrayList<ImageView>();
        ImageView guideOne = new ImageView(this);

        guideOne.setScaleX(0.7f);
        guideOne.setScaleY(0.7f);
        guideOne.setImageResource(R.drawable.splash_sologan);
        guideOne.setAnimation(al);
//        ImageView guideTwo = new ImageView(this);
//        guideTwo.setImageResource(R.drawable.guide_three);
//        guideTwo.setScaleX(0.6f);
//        guideTwo.setScaleY(0.6f);
//        ImageView guideThree = new ImageView(this);
//        guideThree.setImageResource(R.drawable.splash_sologan);
//        guideThree.setScaleX(0.5f);
//        guideThree.setScaleY(0.5f);
        list.add(guideOne);
//        list.add(guideTwo);
//        list.add(guideThree);
        guideAdapter = new MyGuidePagerAdapter(list);
        vp.setAdapter(guideAdapter);

        //6.0版本用add  一下版本用set
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == list.size() - 1) {
                    ll.setAnimation(al);
                    ll.setVisibility(View.VISIBLE);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }else{
                    ll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
}
