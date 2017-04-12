package com.sxh.olddriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.activity.TextDetailsActivity;
import com.sxh.olddriver.bean.PlainText;
import com.sxh.olddriver.utils.OneKeyShare;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 * 纯文adapter
 */
public class PlainTextAdapter extends BaseAdapter {
    List<PlainText> list;
    Context context;

    public PlainTextAdapter(List<PlainText> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_plain_text, null);
            holder.tv_user = (TextView) convertView.findViewById(R.id.item_plain_text_tv_user_name);
            holder.tv_type = (TextView) convertView.findViewById(R.id.item_plain_text_tv_type);
            holder.tv_content = (TextView) convertView.findViewById(R.id.item_plain_text_tv_content);

            holder.tv_smile = (TextView) convertView.findViewById(R.id.item_plain_text_tv_share);
            holder.tv_comment_icon = (TextView) convertView.findViewById(R.id.item_plain_text_tv_comment_icon);
            holder.tv_share_icon = (TextView) convertView.findViewById(R.id.item_plain_text_tv_share_icon);

            holder.tv_amount = (TextView) convertView.findViewById(R.id.item_plain_text_tv_amount);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.item_plain_text_tv_comment);
            holder.tv_share = (TextView) convertView.findViewById(R.id.item_plain_text_tv_share);

            holder.iv_hot = (ImageView) convertView.findViewById(R.id.item_plain_text_iv_hot_icon);
            holder.iv_refresh = (ImageView) convertView.findViewById(R.id.item_plain_text_iv_refresh);
            holder.iv_user = (ImageView) convertView.findViewById(R.id.item_plain_text_iv_icon);
            holder.rb_smile = (RadioButton) convertView.findViewById(R.id.item_plain_text_rb_support);
            holder.rb_cry = (RadioButton) convertView.findViewById(R.id.item_plain_text_rb_cry);
            holder.btn_share = (Button) convertView.findViewById(R.id.item_plain_text_btn_share);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PlainText plainText = list.get(position);
        holder.tv_user.setText(plainText.getLogin());
        holder.tv_content.setText(plainText.getContent());
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));//初始化
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(plainText.getUser_id() + "", plainText.getUser_icon()), holder.iv_user);
        if (plainText.getAmmout() == 0) {
            holder.tv_smile.setVisibility(View.GONE);
            holder.tv_amount.setVisibility(View.GONE);
        } else {
            holder.tv_smile.setVisibility(View.VISIBLE);
            holder.tv_amount.setVisibility(View.VISIBLE);
            holder.tv_amount.setText(plainText.getAmmout() + "");
        }
        if (plainText.getComments_count() == 0) {
            holder.tv_comment_icon.setVisibility(View.GONE);
            holder.tv_comment.setVisibility(View.GONE);
        } else {
            holder.tv_comment_icon.setVisibility(View.VISIBLE);
            holder.tv_comment.setVisibility(View.VISIBLE);
            holder.tv_comment.setText(plainText.getComments_count() + "");
        }
        if (plainText.getShare_count() == 0) {
            holder.tv_share_icon.setVisibility(View.GONE);
            holder.tv_share.setVisibility(View.GONE);
        } else {
            holder.tv_share_icon.setVisibility(View.VISIBLE);
            holder.tv_share.setVisibility(View.VISIBLE);
            holder.tv_share.setText(plainText.getShare_count() + "");
        }
        //区分item的类型,并显示各自样式
        String type = plainText.getType();
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
                    int i = plainText.getAmmout() + 1;
                    plainText.setAmmout(i);
                    notifyDataSetChanged();
                }
            }
        });
        //哭脸-1
        holder.rb_cry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int i = plainText.getAmmout() - 1;
                    plainText.setAmmout(i);
                    notifyDataSetChanged();//刷新
                }
            }
        });
        //一键分享
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(context, plainText.getContent());
            }
        });
        //点击纯文item,跳转到对应二级详情页
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TextDetailsActivity.class);
                intent.putExtra("showDetails",list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_hot, iv_refresh, iv_user;
        TextView tv_user, tv_type, tv_content, tv_amount, tv_comment, tv_share, tv_smile, tv_comment_icon, tv_share_icon;
        RadioButton rb_smile, rb_cry;
        Button btn_share;
    }

    public void setList(List<PlainText> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
