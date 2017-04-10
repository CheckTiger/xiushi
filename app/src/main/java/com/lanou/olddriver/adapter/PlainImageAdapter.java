package com.lanou.olddriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanou.olddriver.R;
import com.lanou.olddriver.activity.ImageDetailsActivity;
import com.lanou.olddriver.bean.PlainImage;
import com.lanou.olddriver.utils.OneKeyShare;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class PlainImageAdapter extends BaseAdapter {
    List<PlainImage> list;
    Context context;

    public PlainImageAdapter(List<PlainImage> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plain_image, null);
            holder.tv_user = (TextView) convertView.findViewById(R.id.item_plain_image_tv_user_name);
            holder.tv_type = (TextView) convertView.findViewById(R.id.item_plain_image_tv_type);
            holder.tv_content = (TextView) convertView.findViewById(R.id.item_plain_image_tv_content);

            holder.tv_smile = (TextView) convertView.findViewById(R.id.item_plain_image_tv_smile);
            holder.tv_comment_icon = (TextView) convertView.findViewById(R.id.item_plain_image_tv_comment_icon);
            holder.tv_share_icon = (TextView) convertView.findViewById(R.id.item_plain_image_tv_share_icon);

            holder.tv_amount = (TextView) convertView.findViewById(R.id.item_plain_image_tv_amount);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.item_plain_image_tv_comment);
            holder.tv_share = (TextView) convertView.findViewById(R.id.item_plain_image_tv_share);

            holder.iv_user = (ImageView)convertView.findViewById(R.id.item_plain_image_iv_icon);
            holder.iv_show  = (ImageView)convertView.findViewById(R.id.item_plain_image_iv_show);
            holder.iv_hot = (ImageView) convertView.findViewById(R.id.item_plain_image_iv_hot_icon);
            holder.iv_refresh = (ImageView) convertView.findViewById(R.id.item_plain_image_iv_refresh);
            holder.rb_smile = (RadioButton) convertView.findViewById(R.id.item_plain_image_rb_support);
            holder.rb_cry = (RadioButton) convertView.findViewById(R.id.item_plain_image_rb_cry);
            holder.btn_share = (Button) convertView.findViewById(R.id.item_plain_image_btn_share);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PlainImage plainImage = list.get(position);
        holder.tv_user.setText(plainImage.getLogin());
        holder.tv_content.setText(plainImage.getContent());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(plainImage.getUser_id()+"",plainImage.getUser_icon()),holder.iv_user);//设置用户头像
        //之前用Glide加载图片的时候,后边拼接出现问题,打log发现的:解决:在调方法时,直接拼接上去图片标示
        Glide.with(context).load(UrlUtils.pic(plainImage.getId()+"")).into(holder.iv_show);
        if (plainImage.getAmmout() == 0) {
            holder.tv_smile.setVisibility(View.GONE);
            holder.tv_amount.setVisibility(View.GONE);
        } else {
            holder.tv_smile.setVisibility(View.VISIBLE);
            holder.tv_amount.setVisibility(View.VISIBLE);
            holder.tv_amount.setText(plainImage.getAmmout() + "");
        }
        if (plainImage.getComments_count() == 0) {
            holder.tv_comment_icon.setVisibility(View.GONE);
            holder.tv_comment.setVisibility(View.GONE);
        } else {
            holder.tv_comment_icon.setVisibility(View.VISIBLE);
            holder.tv_comment.setVisibility(View.VISIBLE);
            holder.tv_comment.setText(plainImage.getComments_count() + "");
        }
        if (plainImage.getShare_count() == 0) {
            holder.tv_share_icon.setVisibility(View.GONE);
            holder.tv_share.setVisibility(View.GONE);
        } else {
            holder.tv_share_icon.setVisibility(View.VISIBLE);
            holder.tv_share.setVisibility(View.VISIBLE);
            holder.tv_share.setText(plainImage.getShare_count() + "");
        }
        //区分item的类型,并显示各自样式
        String type = plainImage.getType();
        if (type.equals("hot")) {
            holder.iv_hot.setVisibility(View.VISIBLE);
            holder.iv_refresh.setVisibility(View.GONE);
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type.setText(type);
        } else if (type.equals("fresh")) {
            holder.iv_hot.setVisibility(View.GONE);
            holder.iv_refresh.setVisibility(View.VISIBLE);
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type.setText(type);
        } else {
            holder.iv_hot.setVisibility(View.GONE);
            holder.iv_refresh.setVisibility(View.GONE);
            holder.tv_type.setVisibility(View.GONE);
        }
        //笑脸+1
        holder.rb_smile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int i = plainImage.getAmmout() + 1;
                    plainImage.setAmmout(i);
                    notifyDataSetChanged();
                }
            }
        });
        //哭脸-1
        holder.rb_cry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int i = plainImage.getAmmout() - 1;
                    plainImage.setAmmout(i) ;
                    notifyDataSetChanged();//刷新
                }
            }
        });
        //一键分享
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(context, plainImage.getContent());
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ImageDetailsActivity.class);
                intent.putExtra("showDetails",list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_hot, iv_refresh,iv_show,iv_user;
        TextView tv_user, tv_type, tv_content, tv_amount, tv_comment, tv_share, tv_smile, tv_comment_icon, tv_share_icon;
        RadioButton rb_smile, rb_cry;
        Button btn_share;
    }

    public void setList(List<PlainImage> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
