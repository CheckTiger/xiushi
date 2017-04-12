package com.sxh.olddriver.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.VideoAdapter;
import com.sxh.olddriver.bean.VideoShow;
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
 * 糗事-视频
 */
public class VideoFragment extends BaseFragment {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    int page = 0;//当前请求数据为第几页
    private ListView video_list_view;
    private List<VideoShow>video_list;
    private VideoAdapter video_adapter;
    private PullToRefreshView pull_to_refresh;


    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.video,null);
        video_list_view = (ListView) view.findViewById(R.id.video_list_view);
        pull_to_refresh = (PullToRefreshView)view.findViewById(R.id.video_pull_to_refresh);
        video_list = new ArrayList<>();
        video_adapter = new VideoAdapter(video_list,getActivity());
        video_list_view.setAdapter(video_adapter);

        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        video_list_view.setLayoutAnimation(lac);
        video_list_view.startLayoutAnimation();
        return view;
    }

    @Override
    public void setData() {
        getNetData(REQUEST);//直接请求
        setPullListener();
    }

    private void setPullListener() {
        //下拉刷新监听
        pull_to_refresh.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);//下拉刷新
            }
        });
        //上拉加载监听
        pull_to_refresh.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);//上拉加载
            }
        });
    }

    private void getNetData(final int type){
        switch (type){
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
        String url = "http://m2.qiushibaike.com/article/list/video?page="+page+"&count=30";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (type == LOAD){
                    video_list.addAll(ShowFragmentData.getVideoPager(result));
                    pull_to_refresh.onFooterLoadFinish();
                }else {
                    video_list = ShowFragmentData.getVideoPager(result);
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                //刷新adapter
                video_adapter.setList(video_list);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();//打印请求失败的异常信息
            }
        });
    }
}
