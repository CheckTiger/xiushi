package com.sxh.olddriver.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.bean.CircleVideo;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.sxh.olddriver.view.NEVideoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class CircleVideoAdapter extends BaseAdapter{
    List<CircleVideo.DataBean> circlevideo_list;

    //
    Context context;

    public CircleVideoAdapter(List<CircleVideo.DataBean> circlevideo_list,Context context) {
        this.circlevideo_list=circlevideo_list;
        this.context=context;
    }

    @Override
    public int getCount() {
        return circlevideo_list.size();
    }

    @Override
    public Object getItem(int position) {
        return circlevideo_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        //if (convertView==null){
            vh=new ViewHolder();
            View view=View.inflate(context,R.layout.circlevideo_item,null);
            //convertView = LayoutInflater.from(context).inflate(R.layout.circlevideo_item, null);
            vh.circlevideo_head= (ImageView) view.findViewById(R.id.id_circlevideo_head);
            vh.circlevideo_action= (ImageView) view.findViewById(R.id.circlevideo_action);
            vh.circlevideo_username= (TextView) view.findViewById(R.id.id_circlevideo_username);
            vh.circlevideo_sex= (TextView) view.findViewById(R.id.id_circlevideo_sex);
            vh.circlevideo_time= (TextView) view.findViewById(R.id.id_circlevideo_time);
            vh.circlevideo_text= (TextView) view.findViewById(R.id.id_circlevideo_text);
            vh.circlevideo_endtext= (TextView) view.findViewById(R.id.id_circlevideo_endtext);
            vh.circlevideo_address= (TextView) view.findViewById(R.id.id_circlevideo_address);
            vh.circlevideo_like_text= (TextView) view.findViewById(R.id.id_circlevideo_like_text);
            vh.circlevideo_comment_text= (TextView) view.findViewById(R.id.id_circlevideo_comment_text);
            vh.circlevideo_ll= (LinearLayout) view.findViewById(R.id.id_circlevideo_ll_video);
            vh.circlevideo_videoView1= (NEVideoView) view.findViewById(R.id.circlevideo_videoView1);
        view.setTag(vh);
        //}else {
            vh= (ViewHolder)view.getTag();
        //}
        vh.circlevideo_ll.setVisibility(View.GONE);
        vh.circlevideo_username.setText(circlevideo_list.get(position).user.login);
        vh.circlevideo_text.setText(circlevideo_list.get(position).content);
        vh.circlevideo_address.setText(circlevideo_list.get(position).distance);
        vh.circlevideo_sex.setText(circlevideo_list.get(position).user.age+"");
        if (circlevideo_list.get(position).user.gender.equals("F")){
            vh.circlevideo_sex.setBackgroundResource(R.drawable.nearby_gender_female);
        }else if (circlevideo_list.get(position).user.gender.equals("M")){
            vh.circlevideo_sex.setBackgroundResource(R.drawable.nearby_gender_male);
        }
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        Log.i("TAG", "getView: "+ UrlUtils.icon(circlevideo_list.get(position).user.id+"",circlevideo_list.get(position).user.icon));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(circlevideo_list.get(position).user.id+"",circlevideo_list.get(position).user.icon),vh.circlevideo_head);
        vh.circlevideo_like_text.setText(circlevideo_list.get(position).like_count+"");
        if (!TextUtils.isEmpty(circlevideo_list.get(position).video.low_url)){
            vh.circlevideo_ll.setVisibility(View.VISIBLE);
            vh.circlevideo_videoView1.setVideoPath(circlevideo_list.get(position).video.high_url);
        }
        //vh.circlevideo_comment_text.setText(circlevideo_list.get(position).comment_count+"");
        return view;
    }
    public void setList(List<CircleVideo.DataBean> list){
        this.circlevideo_list = list;
        notifyDataSetChanged();
    }
    class ViewHolder{
        ImageView circlevideo_head;
        ImageView circlevideo_action;
        TextView circlevideo_username;
        TextView circlevideo_sex;
        TextView circlevideo_time;
        TextView circlevideo_text;
        TextView circlevideo_endtext;
        TextView circlevideo_address;
        TextView circlevideo_like_text;
        TextView circlevideo_comment_text;
        LinearLayout circlevideo_ll;
        NEVideoView circlevideo_videoView1;
    }

}
