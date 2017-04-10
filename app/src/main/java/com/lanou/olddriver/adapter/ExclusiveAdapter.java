package com.lanou.olddriver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.bean.Exclusive;
import com.lanou.olddriver.utils.OneKeyShare;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by user on 2016/7/26.
 * 专享
 */
public class ExclusiveAdapter extends BaseAdapter {
    List<Exclusive> list;
    Context context;

    public ExclusiveAdapter(Context context, List<Exclusive> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_exclusive, null);
            holder.tv_user_name = (TextView) convertView.findViewById(R.id.item_exclusive_tv_user_name);
            holder.tv_content = (TextView) convertView.findViewById(R.id.item_exclusive_tv_content);
            holder.tv_type = (TextView) convertView.findViewById(R.id.item_exclusive_tv_type);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.item_exclusive_tv_amount);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.item_exclusive_tv_comment);
            holder.tv_share = (TextView) convertView.findViewById(R.id.item_exclusive_tv_share);
            holder.iv_hot = (ImageView) convertView.findViewById(R.id.item_exclusive_iv_hot_icon);
            holder.iv_refresh = (ImageView) convertView.findViewById(R.id.item_exclusive_iv_refresh);
            holder.iv_user = (ImageView) convertView.findViewById(R.id.item_exclusive_iv_icon); //用户头像

            holder.rb_smile = (RadioButton) convertView.findViewById(R.id.item_exclusive_btn_support);
            holder.rb_cry = (RadioButton) convertView.findViewById(R.id.item_exclusive_btn_cry);

            holder.iv_share = (Button) convertView.findViewById(R.id.item_exclusive_btn_share);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Exclusive exclusive = list.get(position);
        holder.tv_user_name.setText(exclusive.getLogin());
        holder.tv_content.setText(exclusive.getContent());
        holder.tv_amount.setText(exclusive.getAmount());
        holder.tv_comment.setText(exclusive.getComment_count());
        holder.tv_share.setText(exclusive.getShare_count());

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));//初始化
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(exclusive.getUser_id() + "", exclusive.getUser_icon()), holder.iv_user);
        String type = list.get(position).getType();
        if (type.equals("hot")) {
            holder.iv_refresh.setVisibility(View.GONE);
            holder.iv_hot.setVisibility(View.VISIBLE);
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type.setText(list.get(position).getType());
        } else if (type.equals("fresh")) {
            holder.iv_refresh.setVisibility(View.VISIBLE);
            holder.iv_hot.setVisibility(View.GONE);
            holder.tv_type.setVisibility(View.VISIBLE);
            holder.tv_type.setText(list.get(position).getType());
        } else {
            holder.iv_hot.setVisibility(View.GONE);
            holder.iv_refresh.setVisibility(View.GONE);
            holder.tv_type.setVisibility(View.GONE);
        }

        holder.rb_smile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String count = list.get(position).getAmount();
                    int num = Integer.parseInt(count) + 1;
                    list.get(position).setAmount(num + "");
                    notifyDataSetChanged();
                }
            }
        });
        holder.rb_cry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String count = list.get(position).getAmount();
                    int num = Integer.parseInt(count) - 1;
                    list.get(position).setAmount(num + "");
                    notifyDataSetChanged();
                }
            }
        });
        //一键分享
        holder.iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(context, list.get(position).getContent());
            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView tv_user_name;//作者
        TextView tv_content;//内容
        TextView tv_type;//类型:热门,新鲜
        TextView tv_amount;//总共,好笑
        TextView tv_comment;//评论数
        TextView tv_share;//分享数
        ImageView iv_hot, iv_refresh, iv_user;
        Button iv_share;
        RadioButton rb_smile, rb_cry;
    }

    public void setList(List<Exclusive> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
