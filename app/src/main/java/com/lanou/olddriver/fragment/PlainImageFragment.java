package com.lanou.olddriver.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.PlainImageAdapter;
import com.lanou.olddriver.bean.PlainImage;
import com.lanou.olddriver.utils.ShowFragmentData;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/24.
 */
public class PlainImageFragment extends BaseFragment {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    int page = 0;
    private ListView plain_image_list_view;
    private List<PlainImage> plain_image_list;
    private PlainImageAdapter plain_image_adapter;
    private PullToRefreshView pull_to_refresh;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.plain_image, null);
        plain_image_list_view = (ListView) view.findViewById(R.id.plain_image_list_view);
        pull_to_refresh = (PullToRefreshView) view.findViewById(R.id.plain_image_pull_to_refresh);
        plain_image_list = new ArrayList<>();
        plain_image_adapter = new PlainImageAdapter(plain_image_list, getActivity());
        plain_image_list_view.setAdapter(plain_image_adapter);

        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        plain_image_list_view.setLayoutAnimation(lac);
        plain_image_list_view.startLayoutAnimation();
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
        String url = "http://m2.qiushibaike.com/article/list/imgrank?page=" + page + "&count=30";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (type == LOAD) {
                    plain_image_list.addAll(ShowFragmentData.getPlainImagePager(result));
                    pull_to_refresh.onFooterLoadFinish();
                } else {
                    plain_image_list = ShowFragmentData.getPlainImagePager(result);
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                plain_image_adapter.setList(plain_image_list);//刷新adapter
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }
}
