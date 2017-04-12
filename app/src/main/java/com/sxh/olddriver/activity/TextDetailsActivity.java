package com.sxh.olddriver.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.CommonDetailsAdapter;
import com.sxh.olddriver.bean.CommonDetails;
import com.sxh.olddriver.bean.PlainText;
import com.sxh.olddriver.utils.OneKeyShare;
import com.sxh.olddriver.utils.ShowFragmentData;
import com.sxh.olddriver.utils.UrlUtils;
import com.sxh.olddriver.view.ImageLoaderUtils;
import com.sxh.olddriver.view.PullToRefreshView;
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
 * Created by user on 2016/8/2.
 */
public class TextDetailsActivity extends Activity {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    private int page = 0;
    private ListView details_list_view;
    private List<CommonDetails> details_list;
    private CommonDetailsAdapter details_adapter;
    private ImageView host_user_icon;
    private TextView host_user_name;
    private TextView host_content;
    private TextView host_amount;
    private TextView host_comment;
    private TextView host_share;
    private ImageView host_iv_hot;
    private ImageView host_iv_fresh;
    private TextView host_tv_type;
    private Button host_btn_share;
    private PlainText plainText;
    private PullToRefreshView pull_to_refresh;
    private TextView host_tv_smile;
    private TextView host_comment_icon;
    private TextView host_share_icon;
    private ImageView iv_back;
    private TextView header_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_details);
        initView();
        setHeaderData();
        setData();
    }

    private void initView() {
        details_list_view = (ListView) findViewById(R.id.text_details_list_view);
        pull_to_refresh = (PullToRefreshView) findViewById(R.id.text_details_pull_to_refresh);
        details_list = new ArrayList<>();
        details_adapter = new CommonDetailsAdapter(details_list, this);
        //引入纯文本item作为头布局
        View head_view = LayoutInflater.from(this).inflate(R.layout.item_plain_text, null);
        details_list_view.addHeaderView(head_view);
        host_user_icon = (ImageView) head_view.findViewById(R.id.item_plain_text_iv_icon);
        host_user_name = (TextView) head_view.findViewById(R.id.item_plain_text_tv_user_name);
        host_content = (TextView) head_view.findViewById(R.id.item_plain_text_tv_content);

        host_tv_smile = (TextView) head_view.findViewById(R.id.item_plain_text_tv_smile);
        host_amount = (TextView) head_view.findViewById(R.id.item_plain_text_tv_amount);

        host_comment_icon = (TextView) head_view.findViewById(R.id.item_plain_text_tv_comment_icon);
        host_comment = (TextView) head_view.findViewById(R.id.item_plain_text_tv_comment);

        host_share_icon = (TextView) head_view.findViewById(R.id.item_plain_text_tv_share_icon);
        host_share = (TextView) head_view.findViewById(R.id.item_plain_text_tv_share);

        host_iv_hot = (ImageView) head_view.findViewById(R.id.item_plain_text_iv_hot_icon);
        host_iv_fresh = (ImageView) head_view.findViewById(R.id.item_plain_text_iv_refresh);
        host_tv_type = (TextView) head_view.findViewById(R.id.item_plain_text_tv_type);
        host_btn_share = (Button) head_view.findViewById(R.id.item_plain_text_btn_share);

        iv_back = (ImageView) findViewById(R.id.common_header_back);//返回
        header_user_id = (TextView) findViewById(R.id.common_header_user_id);
        details_list_view.setAdapter(details_adapter);
    }

    private void setHeaderData() {
        plainText = (PlainText) getIntent().getSerializableExtra("showDetails");
        header_user_id.setText(plainText.getUser_id() + "");
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(plainText.getUser_id() + "", plainText.getUser_icon()), host_user_icon);
        host_user_name.setText(plainText.getLogin());
        //判断是否有评论,分享
        if (plainText.getAmmout() == 0) {
            host_tv_smile.setVisibility(View.GONE);
            host_amount.setVisibility(View.GONE);
        } else {
            host_tv_smile.setVisibility(View.VISIBLE);
            host_amount.setVisibility(View.VISIBLE);
            host_amount.setText(plainText.getAmmout() + "");
        }
        if (plainText.getComments_count() == 0) {
            host_comment_icon.setVisibility(View.GONE);
            host_comment.setVisibility(View.GONE);
        } else {
            host_comment_icon.setVisibility(View.VISIBLE);
            host_comment.setVisibility(View.VISIBLE);
            host_comment.setText(plainText.getComments_count() + "");
        }
        if (plainText.getShare_count() == 0) {
            host_share_icon.setVisibility(View.GONE);
            host_share.setVisibility(View.GONE);
        } else {
            host_share_icon.setVisibility(View.VISIBLE);
            host_share.setVisibility(View.VISIBLE);
            host_share.setText(plainText.getShare_count() + "");
        }
        host_content.setText(plainText.getContent());//显示内容

        //设置当前item的类型显示
        String type = plainText.getType();
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
        //一键分享
        host_btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneKeyShare.oneKeyShare(TextDetailsActivity.this, plainText.getContent());
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
        //下拉刷新监听
        pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //上拉加载监听
        pull_to_refresh.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });

    }

    /**
     * 获取网络数据----评论
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
        String id = plainText.getId() + "";
        String url = "http://m2.qiushibaike.com/article/" + id + "/latest/comments?page=" + page + "&count=6";
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
                details_adapter.setList(details_list);//刷新adapter
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
