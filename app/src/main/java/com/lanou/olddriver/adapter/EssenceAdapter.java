package com.lanou.olddriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.lanou.olddriver.R;
import com.lanou.olddriver.activity.EssenceDetailsActivity;
import com.lanou.olddriver.bean.Essence;
import com.lanou.olddriver.utils.OneKeyShare;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 * 糗事-精华adapter
 */
public class EssenceAdapter extends BaseAdapter {
    List<Essence> list;
    Context context;

    public EssenceAdapter(List<Essence> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_essence, null);
            holder.tv_user = (TextView) convertView.findViewById(R.id.item_essence_tv_user_name);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.item_essence_tv_amount);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.item_essence_tv_comment);
            holder.tv_share = (TextView) convertView.findViewById(R.id.item_essence_tv_share);
            holder.tv_type = (TextView) convertView.findViewById(R.id.item_essence_tv_type);
            holder.tv_content = (TextView) convertView.findViewById(R.id.item_essence_tv_content);//内容
            holder.iv_hot = (ImageView) convertView.findViewById(R.id.item_essence_iv_hot_icon);
            holder.iv_refresh = (ImageView) convertView.findViewById(R.id.item_essence_iv_refresh);
            holder.iv_user = (ImageView) convertView.findViewById(R.id.item_essence_iv_icon);//用户头像
            holder.videoView = (VideoView) convertView.findViewById(R.id.item_essence_video_view);//小视频
            holder.iv_show = (ImageView) convertView.findViewById(R.id.item_essence_iv_show);//显示图片
            holder.rb_smile = (RadioButton) convertView.findViewById(R.id.item_essence_rb_support);
            holder.rb_cry = (RadioButton) convertView.findViewById(R.id.item_essence_rb_cry);
            holder.btn_share = (Button) convertView.findViewById(R.id.item_essence_btn_share);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("TAG", "getViewAdapter: " + list.get(position).toString());
        final Essence essence = list.get(position);


        holder.tv_user.setText(essence.getLogin());
        holder.tv_content.setText(essence.getContent());
        holder.tv_amount.setText(essence.getAmmout() + "");
        holder.tv_comment.setText(essence.getComments_count() + "");
        holder.tv_share.setText(essence.getShare_count() + "");
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));//初始化
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(essence.getUser_id() + "", essence.getUser_icon()), holder.iv_user);

        final String type = essence.getType();
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
        holder.videoView.setVisibility(View.GONE);
        holder.iv_show.setVisibility(View.GONE);

        //判断item格式
        if (essence.getFormat().equals("word")) {//当前item为纯文本
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            holder.iv_show.setVisibility(View.GONE);
            holder.tv_content.setText(essence.getContent());
        } else if (essence.getFormat().equals("image")) {//当前item有图片
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            holder.iv_show.setVisibility(View.VISIBLE);
            holder.tv_content.setText(essence.getContent());
            //之前用Glide加载图片的时候,后边拼接出现问题,打log发现的:解决:在调方法时,直接拼接上去图片标示
            Glide.with(context).load(UrlUtils.pic(essence.getImage_id() + "")).into(holder.iv_show);
        } else {//当前item包含video
            holder.tv_content.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.iv_show.setVisibility(View.GONE);
            holder.tv_content.setText(essence.getContent());
            if (!TextUtils.isEmpty(essence.getHigh_url())) {
                holder.videoView.setVideoPath(essence.getHigh_url());
                /**
                 * w为其提供一个控制器，控制其暂停、播放……等功能
                 */
                holder.videoView.setMediaController(new MediaController(context));

                /**
                 * 视频或者音频到结尾时触发的方法
                 */
                holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.i("通知", "完成");
                    }
                });

                holder.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        Log.i("通知", "播放中出现错误");
                        return false;
                    }
                });
            }
        }

        //点击笑脸+1
        holder.rb_smile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int ammout = essence.getAmmout() + 1;
                    essence.setAmmout(ammout);
                    list.set(position,essence);
                    notifyDataSetChanged();
                }
            }
        });
        //点击哭脸-1
        holder.rb_cry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int ammout = essence.getAmmout() - 1;
                    essence.setAmmout(ammout);

                    list.set(position,essence);
                    notifyDataSetChanged();
                }
            }
        });
        //一键分享
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(context, essence.getContent());
            }
        });
        //给convertView设置点击事件,将对应的list传递过去(二级页面activity)
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EssenceDetailsActivity.class);
                intent.putExtra("showDetails", list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iv_hot, iv_refresh, iv_user, iv_show;
        TextView tv_user, tv_type, tv_content, tv_amount, tv_comment, tv_share;
        VideoView videoView;
        RadioButton rb_smile, rb_cry;
        Button btn_share;
    }

    public void setList(List<Essence> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
