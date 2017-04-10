package com.lanou.olddriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.bean.CommonDetails;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/28.
 */
public class CommonDetailsAdapter extends BaseAdapter {
    List<CommonDetails> list;
    Context context;

    public CommonDetailsAdapter(List<CommonDetails> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_essence_details, null);
            holder.tv_user = (TextView) convertView.findViewById(R.id.item_essence_details_user);
            holder.tv_count = (TextView) convertView.findViewById(R.id.item_essence_details_count);
            holder.tv_content = (TextView) convertView.findViewById(R.id.item_essence_details_content);
            holder.iv_user = (ImageView) convertView.findViewById(R.id.item_essence_details_user_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommonDetails essenceDetails = list.get(position);
        holder.tv_user.setText(essenceDetails.getLogin());
        holder.tv_content.setText(essenceDetails.getContent());
        holder.tv_count.setText(essenceDetails.getLike_count() + "");
        //显示头像
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        String url = UrlUtils.icon(essenceDetails.getComment_id() + "", essenceDetails.getComment_icon());
        ImageLoaderUtils.getImageByloader(url, holder.iv_user);
        return convertView;
    }

    class ViewHolder {
        TextView tv_user, tv_count, tv_content;
        ImageView iv_user;
    }

    public void setList(List<CommonDetails> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
