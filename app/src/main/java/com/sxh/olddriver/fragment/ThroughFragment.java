package com.sxh.olddriver.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.FragmentThroughAdapter;
import com.sxh.olddriver.bean.Through;
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
 * Created by zzl on 2016/7/24.
 */
public class ThroughFragment extends BaseFragment {

    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    int page = 0;

    private ListView fragment_through_list_view;
    private PullToRefreshView fragment_through_pull_to_refresh;
    FragmentThroughAdapter adapter;
    List<Through> fragment_through_list;
    @Override
    public View initView() {
        View view=View.inflate(getActivity(),R.layout.fragment_through,null);
        fragment_through_list_view = (ListView) view.findViewById(R.id.fragment_through_list_view);
        fragment_through_pull_to_refresh = (PullToRefreshView) view.findViewById(R.id.fragment_through_pull_to_refresh);
        fragment_through_list=new ArrayList<>();
        adapter=new FragmentThroughAdapter(fragment_through_list,getActivity());
        fragment_through_list_view.setAdapter(adapter);

        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        fragment_through_list_view.setLayoutAnimation(lac);
        fragment_through_list_view.startLayoutAnimation();
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
        fragment_through_pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //上拉加载
        fragment_through_pull_to_refresh.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });
    }

    /**
     * 请求网络数据
     *
     * @param type:请求类型
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
            default:
                break;
        }
        String url = "http://m2.qiushibaike.com/article/history?page=" + page + "&count=30";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (type == LOAD) {
                    fragment_through_list.addAll(ShowFragmentData.getFragmentThrough(result));
                    fragment_through_pull_to_refresh.onFooterLoadFinish();
                } else {
                    fragment_through_list = ShowFragmentData.getFragmentThrough(result);
                    fragment_through_pull_to_refresh.onHeaderRefreshFinish();
                }
                adapter.setList(fragment_through_list);//刷新adapter
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }
}
