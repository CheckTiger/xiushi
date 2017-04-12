package com.sxh.olddriver.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sxh.olddriver.R;
import com.sxh.olddriver.activity.LiveStreamAcitvity;
import com.sxh.olddriver.bean.Live;

import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class LiveAdapter extends BaseAdapter {
    List<Live> list;
    Context context;

    private String decodeType = "software";  //解码类型，默认软件解码
    private String mediaType = "livestream"; //媒体类型，默认网络直播

    public LiveAdapter(List<Live> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.live_item, null);
            holder.iv_pic = (ImageView) convertView.findViewById(R.id.live_iv_pic);
            holder.tv_content = (TextView) convertView.findViewById(R.id.live_tv_content);
            holder.tv_count = (TextView) convertView.findViewById(R.id.live_tv_count);
            holder.tv_name = (TextView) convertView.findViewById(R.id.live_tv_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //图片加载
        //图片加载
        Glide.with(context).load(list.get(position).getThumbnail_url()).into(holder.iv_pic);
//        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//        ImageLoaderUtils.getImageByloader(list.get(position).getThumbnail_url(),holder.iv_pic);
//        holder.iv_pic.setImageBitmap(list.get(position).getThumbnail_url());
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_content.setText(list.get(position).getContent());
        holder.tv_count.setText(list.get(position).getVisitors_count() + "");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hdl_live_url = list.get(position).getHdl_live_url();
//                Toast.makeText(context, hdl_live_url, Toast.LENGTH_SHORT).show();
//                Log.i("aaaaa", "onClick: "+hdl_live_url);
                Intent intent = new Intent(context, LiveStreamAcitvity.class);
                intent.putExtra("media_type", mediaType);
                intent.putExtra("decode_type", decodeType);
                intent.putExtra("videoPath", hdl_live_url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;//直播间名字
        TextView tv_count;//观看数
        TextView tv_name;//主播名字
        ImageView iv_pic;//主播照片
        ImageView iv_head;
    }

    public void setList(List<Live> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
