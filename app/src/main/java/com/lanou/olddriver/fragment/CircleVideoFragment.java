package com.lanou.olddriver.fragment;

import android.view.View;
import android.widget.ListView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.CircleVideoAdapter;
import com.lanou.olddriver.bean.CircleVideo;
import com.lanou.olddriver.db.UtilsDB;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class CircleVideoFragment extends BaseFragment {
    //	当前请求数据为第几页
    int page=0;
    //直接请求常量
    public static final int REQUEST=0;
    //下拉刷新请求常量
    public static final int REFRESH=1;
    //上拉加载请求常量
    public static final int LOAD=2;
    private PullToRefreshView pull;
    CircleVideoAdapter adapter;
    List<CircleVideo.DataBean> circlevideo_list=new ArrayList<CircleVideo.DataBean>();
    ListView circlevideo_listview;
    //ArrayList<HashMap<String, String>> list2;
    @Override
    public View initView() {
        View view=View.inflate(getActivity(), R.layout.circlevideo,null);
        pull= (PullToRefreshView) view.findViewById(R.id.id_circlevideo_pull);
        circlevideo_listview= (ListView) view.findViewById(R.id.id_circlevideo_list);
        adapter=new CircleVideoAdapter(circlevideo_list,getActivity());
        circlevideo_listview.setAdapter(adapter);
        return view;
    }

    @Override
    public void setData() {
        getNetData(0);
        //调用pull绑定监听事件的方法
        setPullListener();
    }

    private void getNetData(final int type) {
        switch (type) {
            case REQUEST:
                page=1;
                break;
            case REFRESH:
                page=1;
                break;
            case LOAD:
                page++;
                break;
            default:
                break;
        }
        String url="http://circle.qiushibaike.com/article/video/list?page="+page;
        HttpUtils utils=new HttpUtils();
		/*
		 * 发出请求并且回调
		 * 参数1：请求方式
		 * 	  2：请求地址
		 * 	  3：回调接口
		 */
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result=responseInfo.result;
               // MyDBHelper.getInstances(getActivity()).insert("nextdoorcommen",result);
                UtilsDB.getCirclevideoList(getActivity(),result);
                if (type==LOAD) {
                    circlevideo_list.addAll(UtilsDB.getDataCircleVideo(getActivity()));
                    pull.onFooterLoadFinish();
                }else{
                    circlevideo_list=UtilsDB.getDataCircleVideo(getActivity());
                    pull.onHeaderRefreshFinish();
                }
                if (circlevideo_list!=null){
                    adapter.setList(circlevideo_list);
                }else {
                    return;
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                circlevideo_list=UtilsDB.getDataCircleVideo(getActivity());
                if (circlevideo_list!=null){
                    adapter.setList(circlevideo_list);
                }else {

                    return;
                }
            }
        });
    }
    /**
     * 添加上拉和下拉的监听事件
     * */
    private void setPullListener(){
        //下拉刷新监听事件
        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {


            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //上拉加载的监听事件
        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });
    }
}
