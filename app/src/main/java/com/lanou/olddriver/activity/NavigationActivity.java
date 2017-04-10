package com.lanou.olddriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.ImgVPAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {


    ViewPager vp;
    List<ImageView> list;
    ImgVPAdapter adapter;
    private Button btn;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.navi_btn);
        vp = (ViewPager) findViewById(R.id.navi_vp);
        ll = (LinearLayout) findViewById(R.id.navi_ll);
        //保存vp需要显示的ImageView对象
        list = new ArrayList<ImageView>();
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.qb_04);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.guide_three);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.splash_sologan);
        /**
         *1.操作图片首先要读取到内存中
         *意思就是把图片转换为bitmap格式
         *2.放缩图片
         */

//		Bitmap bitmap=BitmapFactory.decodeResource(res, id);
//		bitmap.getWidth();
        //图片的拉伸
        //设置图片可以充满屏幕
//		iv1.setScaleType(ScaleType.FIT_XY);
//		iv2.setScaleType(ScaleType.FIT_XY);
//		iv3.setScaleType(ScaleType.FIT_XY);
        list.add(iv1);
        list.add(iv2);
        list.add(iv3);
        //实例化adapter
        adapter = new ImgVPAdapter(list);
        vp.setAdapter(adapter);

        //6.0版本用add  一下版本用set
        //确定是否显示进入
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == list.size() - 1) {
                    ll.setVisibility(View.VISIBLE);
                    //在btn中保存是否是第一次开启的变量
                    btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(NavigationActivity.this, MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
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
