package com.lanou.olddriver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.CommonDetailsAdapter;
import com.lanou.olddriver.bean.Essence;
import com.lanou.olddriver.bean.CommonDetails;
import com.lanou.olddriver.utils.OneKeyShare;
import com.lanou.olddriver.utils.ShowFragmentData;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/28.
 */
public class EssenceDetailsActivity extends Activity {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    private int page = 0;
    private ListView details_list_view;
    private List<CommonDetails> details_list;
    private CommonDetailsAdapter details_adapter;
    private TextView host_user_name;
    private TextView host_content;
    private TextView host_amount;
    private TextView host_comment;
    private TextView host_share;
    private ImageView host_user_icon;
    private ImageView host_iv_hot;
    private ImageView host_iv_fresh;
    private TextView host_tv_type;
    private Essence essence;//头数据
    private PullToRefreshView pull_to_refresh;
    private Button host_btn_share;
    private ImageView iv_back;
    private TextView header_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essence_details);
        initView();
        setHeaderData();
        setData();
    }
    private void initView() {
        details_list_view = (ListView) findViewById(R.id.essence_details_list_view);
        pull_to_refresh = (PullToRefreshView) findViewById(R.id.essence_details_pull_to_refresh);
        details_list = new ArrayList<>();
        details_adapter = new CommonDetailsAdapter(details_list, this);
        View head_view = LayoutInflater.from(this).inflate(R.layout.item_essence, null);//引入头布局
        details_list_view.addHeaderView(head_view);
        host_user_icon = (ImageView) head_view.findViewById(R.id.item_essence_iv_icon);
        host_user_name = (TextView) head_view.findViewById(R.id.item_essence_tv_user_name);
        host_content = (TextView) head_view.findViewById(R.id.item_essence_tv_content);
        host_amount = (TextView) head_view.findViewById(R.id.item_essence_tv_amount);
        host_comment = (TextView) head_view.findViewById(R.id.item_essence_tv_comment);
        host_share = (TextView) head_view.findViewById(R.id.item_essence_tv_share);
        host_iv_hot = (ImageView) head_view.findViewById(R.id.item_essence_iv_hot_icon);
        host_iv_fresh = (ImageView) head_view.findViewById(R.id.item_essence_iv_refresh);
        host_tv_type = (TextView) head_view.findViewById(R.id.item_essence_tv_type);
        host_btn_share = (Button) head_view.findViewById(R.id.item_essence_btn_share);
        iv_back = (ImageView) findViewById(R.id.common_header_back);//返回按钮
        header_user_id = (TextView) findViewById(R.id.common_header_user_id);
        details_list_view.setAdapter(details_adapter);
    }

    /**
     * 给Activity的头布局设置数据
     */
    private void setHeaderData() {
        essence = (Essence) getIntent().getSerializableExtra("showDetails");
        header_user_id.setText(essence.getUser_id()+"");
        host_user_name.setText(essence.getLogin());
        host_content.setText(essence.getContent());
        host_amount.setText(essence.getAmmout() + "");
        host_comment.setText(essence.getComments_count() + "");
        host_share.setText(essence.getShare_count() + "");
        //设置host 头像
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(essence.getUser_id() + "", essence.getUser_icon()), host_user_icon);
        //设置当前item的类型显示
        String type = essence.getType();
        if (type.equals("hot")) {
            host_iv_hot.setVisibility(View.VISIBLE);
            host_iv_fresh.setVisibility(View.GONE);
            host_tv_type.setVisibility(View.VISIBLE);
            host_tv_type.setText(type);
        } else if (type.equals("fresh")) {
            host_iv_hot.setVisibility(View.GONE);
            host_iv_fresh.setVisibility(View.VISIBLE);
            host_tv_type.setVisibility(View.VISIBLE);
            host_tv_type.setText(type);
        } else {
            host_iv_hot.setVisibility(View.GONE);
            host_iv_fresh.setVisibility(View.GONE);
            host_tv_type.setVisibility(View.GONE);
        }
        //分享
        host_btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(EssenceDetailsActivity.this, essence.getContent());
            }
        });
    }

    private void setData() {
        getNetData(REQUEST);
        setPullListener();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭当前页面
            }
        });
    }

    private void setPullListener() {
        //上拉刷新监听
        pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //下拉加载监听
        pull_to_refresh.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });
    }

    /**
     * 请求网络数据,显示精华item对应的评论
     *
     * @param type
     */
    private void getNetData(final int type) {
        switch (type) {
            case REQUEST:
                page = 1;
                break;
            case REFRESH:
                page = 1;
                break;
            case LOAD:
                page++;
                break;
        }
        String id = essence.getImage_id() + "";//拼接id,不是user里的
        String url = "http://m2.qiushibaike.com/article/" + id + "/latest/comments?page=" + page + "&count=20";
        Log.i("TAG", "getNetData:----- " + url);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (type == LOAD) {
                    details_list.addAll(ShowFragmentData.getDetails(result));
                    pull_to_refresh.onFooterLoadFinish();
                } else {
                    details_list = ShowFragmentData.getDetails(result);
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                details_adapter.setList(details_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

}
