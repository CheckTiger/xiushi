package com.sxh.olddriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.bean.CommentsBean;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/8/5.
 */
public class CommonNextdoorAdapter extends BaseAdapter{
    List<CommentsBean> data_list;
    Context context;
    public CommonNextdoorAdapter(List<CommentsBean> data_list, Context context) {
        this.data_list=data_list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return data_list.size();
    }

    @Override
    public Object getItem(int position) {
        return data_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if (convertView==null){
           vh=new ViewHolder();
           convertView= LayoutInflater.from(context).inflate(R.layout.common_nextdoor_item,null);
            vh.icon= (ImageView) convertView.findViewById(R.id.id_nextdoor_head);
            vh.like=(CheckBox)convertView.findViewById(R.id.id_nextdoor_like);
            vh.like_num= (TextView) convertView.findViewById(R.id.id_nextdoor_like_text);
            vh.name=(TextView)convertView.findViewById(R.id.id_nextdoor_username);
            vh.text=(TextView)convertView.findViewById(R.id.id_nextdoor_text);
            vh.time=(TextView)convertView.findViewById(R.id.id_nextdoor_time);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(data_list.get(position).user.id+"",data_list.get(position).user.icon),vh.icon);
        vh.name.setText(data_list.get(position).user.login);
        vh.text.setText(data_list.get(position).content);
        vh.like_num.setText(data_list.get(position).like_count+"");
        return convertView;
    }
    class ViewHolder{
        ImageView icon;
        TextView  text;
        TextView name;
        TextView time;
        CheckBox like;
        TextView like_num;
    }
    public void setList(List<CommentsBean> list) {
        this.data_list = list;
        notifyDataSetChanged();
    }
}
