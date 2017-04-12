package com.sxh.olddriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxh.olddriver.R;
import com.sxh.olddriver.bean.Topic;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class TopicAdapter extends BaseAdapter{
    Context context;
    List<Topic.DataBean> dataList;
    private int rank;
    private Topic.DataBean bean;

    public TopicAdapter(List<Topic.DataBean> dataList,Context context) {
        this.context=context;
        this.dataList=dataList;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold vh=null;
        if (convertView==null){
            vh=new ViewHold();
            convertView= LayoutInflater.from(context).inflate(R.layout.topic_item,null);
            vh.topic= (TextView) convertView.findViewById(R.id.id_topic_id);
            vh.topic_dynamic= (TextView) convertView.findViewById(R.id.id_topic_dynamic);
            vh.topic_text= (TextView) convertView.findViewById(R.id.id_topic_text);
            vh.topic_item_img= (ImageView) convertView.findViewById(R.id.id_topic_item_img);
            vh.topic_today= (TextView) convertView.findViewById(R.id.id_topic_today);
            vh.topic_item_img2= (TextView) convertView.findViewById(R.id.id_topic_item_img2);
            vh.topic_anonymous=(ImageView)convertView.findViewById(R.id.id_topic_anonymous);
            convertView.setTag(vh);
        }else {
            vh = (ViewHold) convertView.getTag();
        }
         bean=dataList.get(position);
        vh.topic_anonymous.setVisibility(View.GONE);
        int[] id={R.drawable.group_level_gold,R.drawable.group_level_silver,R.drawable.group_level_copper};

        if (bean.is_anonymous){
            vh.topic_anonymous.setVisibility(View.VISIBLE);
        }
        vh.topic.setText(bean.content+"");
        Glide.with(context).load(bean.avatar_urls.get(0).pic_url).into(vh.topic_item_img);
        vh.topic_text.setText(bean.abstractX+"");
        vh.topic_today.setText(bean.today_article_count+"");
        vh.topic_dynamic.setText(bean.article_count+"");

        if(bean.rank==1){
            vh.topic_item_img2.setText("");
            vh.topic_item_img2.setBackgroundResource(id[0]);
        }
        else if(bean.rank==2){
            vh.topic_item_img2.setText("");
            vh.topic_item_img2.setBackgroundResource(id[1]);
        }
        else if(bean.rank==3){
            vh.topic_item_img2.setText("");
            vh.topic_item_img2.setBackgroundResource(id[2]);
        }
        else{
            vh.topic_item_img2.setBackgroundResource(0);
            vh.topic_item_img2.setText(position+1+"");
        }

        return convertView;
    }

    class ViewHold{
        ImageView topic_item_img;
        TextView  topic;
        TextView  topic_text;
        TextView  topic_dynamic;
        TextView  topic_today;
        TextView   topic_item_img2;
        ImageView   topic_anonymous;
    }
    public void setList(List<Topic.DataBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
