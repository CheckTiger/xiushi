package com.sxh.olddriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxh.olddriver.R;
import com.sxh.olddriver.activity.NextDoorActivity;
import com.sxh.olddriver.bean.Data;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/24.
 */
public class NextDoorAdapter extends BaseAdapter {
    List<Data> list;
    Context context;
    private static final int YEAR = 365 * 24 * 60 * 60;
    private static final int MONTH = 30 * 24 * 60 * 60;
    private static final int DAY = 24 * 60 * 60;
    private static final int HOUR = 60 * 60;
    private static final int MINUTE = 60;
    private static final long INTERVAL_TIME = 60 * 1000 * 5;
    public NextDoorAdapter(List<Data> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Viewholder vh=null;
        //if (view==null) {
            vh = new Viewholder();
        View view1=View.inflate(context,R.layout.nextdoor_item,null);
            //view = LayoutInflater.from(context).inflate(R.layout.nextdoor_item, null);
            vh.nextdoor_head = (ImageView) view1.findViewById(R.id.id_nextdoor_head);
            vh.qiuyoucircle_action = (ImageView) view1.findViewById(R.id.qiuyoucircle_action);
            vh.nextdoor_username = (TextView) view1.findViewById(R.id.id_nextdoor_username);
            vh.nextdoor_sex = (TextView) view1.findViewById(R.id.id_nextdoor_sex);
            vh.nextdoor_time = (TextView) view1.findViewById(R.id.id_nextdoor_time);
            vh.nextdoor_text = (TextView) view1.findViewById(R.id.id_nextdoor_text);
            vh.nextdoor_endtext = (TextView) view1.findViewById(R.id.id_nextdoor_endtext);
            vh.nextdoor_address = (TextView) view1.findViewById(R.id.id_nextdoor_address);
            vh.nextdoor_like_text = (TextView) view1.findViewById(R.id.id_nextdoor_like_text);
            vh.nextdoor_comment_text = (TextView) view1.findViewById(R.id.id_nextdoor_comment_text);
            vh.nextdoor_ll = (LinearLayout) view1.findViewById(R.id.id_nextdoor_ll_img);
            vh.nextdoor_ll_img_1 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_1);
            vh.nextdoor_ll_img_2 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_2);
            vh.nextdoor_ll_img_3 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_3);
            vh.nextdoor_ll_img_4 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_4);
            vh.nextdoor_ll_img_5 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_5);
            vh.nextdoor_ll_img_6 = (ImageView) view1.findViewById(R.id.id_nextdoor_ll_img_6);
            vh.nextdoor_ll_img1 = (LinearLayout) view1.findViewById(R.id.id_nextdoor_ll_img1);
            vh.nextdoor_ll_img2 = (LinearLayout) view1.findViewById(R.id.id_nextdoor_ll_img2);
            vh.nextdoor_like = (CheckBox) view1.findViewById(R.id.id_nextdoor_like);
        view1.setTag(vh);
       // }else{
            vh= (Viewholder) view1.getTag();
       // }

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        vh.nextdoor_ll.setVisibility(View.GONE);
        if (list.get(i).user.gender.equals("M")){
            vh.nextdoor_sex.setBackgroundResource(R.drawable.nearby_gender_male);
        }else if (list.get(i).user.gender.equals("F")){
            vh.nextdoor_sex.setBackgroundResource(R.drawable.nearby_gender_female);
        }
        //使用ImageLoaderUtils加载网络图片
        if (!list.get(i).pic_urls.isEmpty()){
            vh.nextdoor_ll.setVisibility(View.VISIBLE);
            ImageView[] str={vh.nextdoor_ll_img_1,vh.nextdoor_ll_img_2,vh.nextdoor_ll_img_3,vh.nextdoor_ll_img_4,vh.nextdoor_ll_img_5,vh.nextdoor_ll_img_6};
            vh.nextdoor_ll_img1.setVisibility(View.GONE);
            vh.nextdoor_ll_img2.setVisibility(View.GONE);
            for (int j = 0; j <list.get(i).pic_urls.size(); j++) {
                Log.i("TAG", "getView: "+list.get(i).pic_urls.size());
                if (list.get(i).pic_urls.size()>2&&list.get(i).pic_urls.size()<6){
                    vh.nextdoor_ll_img1.setVisibility(View.VISIBLE);
                    vh.nextdoor_ll_img2.setVisibility(View.VISIBLE);

                    Glide.with(context).load(list.get(i).pic_urls.get(j).pic_url).into(str[j]);
                }
                else if (list.get(i).pic_urls.size()>=0&&list.get(i).pic_urls.size()<=2){
                    vh.nextdoor_ll_img1.setVisibility(View.VISIBLE);
                    vh.nextdoor_ll_img2.setVisibility(View.GONE);
                    Glide.with(context).load(list.get(i).pic_urls.get(j).pic_url).into(str[j]);
                }
            }
        }
        vh.nextdoor_like_text.setText( list.get(i).like_count+"");
        vh.nextdoor_username.setText(list.get(i).user.login);
        Log.i("TAG", "getView: "+ UrlUtils.icon(list.get(i).user.id+"",list.get(i).user.icon));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(list.get(i).user.id+"",list.get(i).user.icon),vh.nextdoor_head);
        vh.nextdoor_text.setText(list.get(i).content);
        vh.nextdoor_address.setText(list.get(i).distance);
        vh.nextdoor_sex.setText(list.get(i).user.age+"");
        vh.nextdoor_comment_text.setText(list.get(i).comment_count+"");
        //给convertView设置点击事件,将对应的list传递过去(二级页面activity)
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NextDoorActivity.class);
                intent.putExtra("nextdoordata", list.get(i));
                context.startActivity(intent);
            }
        });
        return view1;
    }
    public void setList(List<Data> list){
        this.list = list;
        notifyDataSetChanged();
    }
    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;
        String timeStr = null;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {
            timeStr = timeGap / MINUTE + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;


    }
    class Viewholder{
        ImageView nextdoor_head;
        ImageView qiuyoucircle_action;
        TextView nextdoor_username;
        TextView nextdoor_sex;
        TextView nextdoor_time;
        TextView nextdoor_text;
        TextView nextdoor_endtext;
        TextView nextdoor_address;
        TextView nextdoor_like_text;
        TextView nextdoor_comment_text;
        LinearLayout nextdoor_ll;
        LinearLayout nextdoor_ll_img1;
        LinearLayout nextdoor_ll_img2;
        ImageView nextdoor_ll_img_1;
        ImageView nextdoor_ll_img_2;
        ImageView nextdoor_ll_img_3;
        ImageView nextdoor_ll_img_4;
        ImageView nextdoor_ll_img_5;
        ImageView nextdoor_ll_img_6;
        CheckBox nextdoor_like;
    }
}











