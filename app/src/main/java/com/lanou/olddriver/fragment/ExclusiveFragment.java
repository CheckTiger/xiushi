package com.lanou.olddriver.fragment;

import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.ExclusiveAdapter;
import com.lanou.olddriver.bean.Exclusive;
import com.lanou.olddriver.utils.ShowFragmentData;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/24.
 * 糗事-专享页面
 */
public class ExclusiveFragment extends BaseFragment {
    // 直接请求常量
    public static final int REQUEST = 0;
    // 下拉刷新常量
    public static final int REFRESH = 1;
    // 上拉加载常量
    public static final int LOAD = 2;
    // 当前请求数据的页数为第几页
    int page = 0;
    private ListView exclusive_list_view;
    private List<Exclusive> exclusive_list;
    private ExclusiveAdapter exclusive_adapter;
    private PullToRefreshView pull_to_refresh;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.exclusive, null);
        exclusive_list_view = (ListView) view.findViewById(R.id.exclusive_list_view);
        pull_to_refresh = (PullToRefreshView) view.findViewById(R.id.exclsive_pull_to_refresh);
        exclusive_list = new ArrayList<>();
        exclusive_adapter = new ExclusiveAdapter(getActivity(), exclusive_list);
        exclusive_list_view.setAdapter(exclusive_adapter);

        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in_t));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        exclusive_list_view.setLayoutAnimation(lac);
        exclusive_list_view.startLayoutAnimation();
        return view;
    }

    @Override
    public void setData() {
        setPullListener();
        getNetData(REQUEST);
    }

    /**
     * 添加上拉刷新和下拉加载的监听
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
     * 获取网络数据
     *
     * @param type:静态常量
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
        String url = "http://m2.qiushibaike.com/article/list/text?page="+page+"&count=30";

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String json = responseInfo.result;
                Log.e("TAG", "getNetData: 第一次就"+json);
                    if (type == LOAD) {
                    exclusive_list.addAll(ShowFragmentData.getExclusive(json));
                    //加载方式请求结束和,关闭加载视图
                    pull_to_refresh.onFooterLoadFinish();
                } else {
                    // 把newAlbum对象中的data数据集合赋值给adapter使用的list集合
                    exclusive_list = ShowFragmentData.getExclusive(json);
                    //刷新方式请求结束后,关闭刷新视图
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                //刷新adapter
                exclusive_adapter.setList(exclusive_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


}
