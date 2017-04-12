package com.sxh.olddriver.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.PlainTextAdapter;
import com.sxh.olddriver.bean.PlainText;
import com.sxh.olddriver.utils.ShowFragmentData;
import com.sxh.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/24.
 * 糗事-纯文
 */
public class PlainTextFragment extends BaseFragment {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    int page = 0;
    private ListView plain_text_list_view;
    private List<PlainText> plain_text_list;
    private PlainTextAdapter plain_text_adapter;
    private PullToRefreshView pull_to_refresh;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.plain_text, null);
        plain_text_list_view = (ListView) view.findViewById(R.id.plain_text_list_view);
        pull_to_refresh = (PullToRefreshView) view.findViewById(R.id.plain_text_pull_to_refresh);
        plain_text_list = new ArrayList<>();
        plain_text_adapter = new PlainTextAdapter(plain_text_list, getActivity());
        plain_text_list_view.setAdapter(plain_text_adapter);


        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in_t));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        plain_text_list_view.setLayoutAnimation(lac);
        plain_text_list_view.startLayoutAnimation();

        return view;
    }

    @Override
    public void setData() {
        getNetData(REQUEST);
        setPullListener();
    }

    /**
     * 设置下拉刷新和上拉加载的监听
     */
    private void setPullListener() {
        //下拉刷新
        pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //上拉加载
        pull_to_refresh.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });
    }

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
            default:
                break;
        }
        String url = "http://m2.qiushibaike.com/article/list/text?page=" + page + "&count=30";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;//网络请求数据
                if (type == LOAD) {
                    plain_text_list.addAll(ShowFragmentData.getPlainTextPager(result));
                    pull_to_refresh.onFooterLoadFinish();
                } else {
                    plain_text_list = ShowFragmentData.getPlainTextPager(result);
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                plain_text_adapter.setList(plain_text_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }
}
