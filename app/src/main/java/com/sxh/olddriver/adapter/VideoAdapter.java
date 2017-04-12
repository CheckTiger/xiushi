package com.sxh.olddriver.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.bean.VideoShow;
import com.sxh.olddriver.utils.OneKeyShare;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.sxh.olddriver.view.NEVideoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class VideoAdapter extends BaseAdapter {
    List<VideoShow> list;
    Context context;

    public VideoAdapter(List<VideoShow> list, Context context) {
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
        //if (convertView == null) {
            holder = new ViewHolder();
        View view=View.inflate(context,R.layout.item_video,null);
            //convertView = LayoutInflater.from(context).inflate(R.layout.item_video, null);
            holder.tv_user = (TextView) view.findViewById(R.id.item_video_tv_user_name);
            holder.tv_content = (TextView) view.findViewById(R.id.item_video_tv_content);
            holder.tv_amount = (TextView) view.findViewById(R.id.item_video_tv_amount);
            holder.tv_comment = (TextView) view.findViewById(R.id.item_video_tv_comment);
            holder.tv_share = (TextView) view.findViewById(R.id.item_video_tv_share);

            holder.tv_type = (TextView) view.findViewById(R.id.item_video_tv_type);
            holder.iv_hot = (ImageView) view.findViewById(R.id.item_video_iv_hot_icon);
            holder.iv_refresh = (ImageView) view.findViewById(R.id.item_video_iv_refresh);
            holder.iv_user = (ImageView) view.findViewById(R.id.item_video_iv_icon);
            holder.videoView = (NEVideoView)view.findViewById(R.id.item_ne_video_view);
            holder.rb_smile = (RadioButton) view.findViewById(R.id.item_video_rb_support);
            holder.rb_cry = (RadioButton) view.findViewById(R.id.item_video_rb_cry);
            holder.btn_share = (Button) view.findViewById(R.id.item_video_btn_share);
        view.setTag(holder);
        //} else {
            holder = (ViewHolder) view.getTag();
        //}
        Log.i("TAG", "getViewAdapter: " + list.get(position).toString());
        final VideoShow videoShow = list.get(position);

        holder.tv_user.setText(videoShow.getLogin());
        holder.tv_content.setText(videoShow.getContent());
        holder.tv_amount.setText(videoShow.getAmmout() + "");
        holder.tv_comment.setText(videoShow.getComments_count() + "");
        holder.tv_share.setText(videoShow.getShare_count() + "");
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));//初始化
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(videoShow.getUser_id() + "", videoShow.getUser_icon()), holder.iv_user);

        final String type = videoShow.getType();
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
        //url不为空的时候执行:播放小视频
        if (!TextUtils.isEmpty(videoShow.getHigh_url())) {
            holder.videoView.setVisibility(View.VISIBLE);
            holder.videoView.setVideoPath(videoShow.getHigh_url());
        }
        //点击笑脸+1
        holder.rb_smile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int ammout = videoShow.getAmmout() + 1;
                    videoShow.setAmmout(ammout);
                    notifyDataSetChanged();
                }
            }
        });
        //点击哭脸-1
        holder.rb_cry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int ammout = videoShow.getAmmout() - 1;
                    videoShow.setAmmout(ammout);
                    notifyDataSetChanged();
                }
            }
        });
        //一键分享
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(context, videoShow.getContent());
            }
        });

        return view;
    }

    class ViewHolder {
        ImageView iv_hot, iv_refresh, iv_user;
        TextView tv_user, tv_type, tv_content, tv_amount, tv_comment, tv_share;
        NEVideoView videoView;
        RadioButton rb_smile, rb_cry;
        Button btn_share;
    }

    public void setList(List<VideoShow> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
