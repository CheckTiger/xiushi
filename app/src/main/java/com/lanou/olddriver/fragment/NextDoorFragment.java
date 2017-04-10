package com.lanou.olddriver.fragment;
import android.view.View;
import android.widget.ListView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.NextDoorAdapter;
import com.lanou.olddriver.bean.Data;
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
public class NextDoorFragment extends BaseFragment {
    //	当前请求数据为第几页
    int page=0;
    //直接请求常量
    public static final int REQUEST=0;
    //下拉刷新请求常量
    public static final int REFRESH=1;
    //上拉加载请求常量
    public static final int LOAD=2;
    private PullToRefreshView pull;
    List<Data> nextDoorlist=new ArrayList<Data>();
    NextDoorAdapter adapter;
    ListView next_door_list_view;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.nextdoor,null);
        next_door_list_view = (ListView)view.findViewById(R.id.list_embarrassing);
        pull= (PullToRefreshView)view.findViewById(R.id.id_embarrassing_pull);
        adapter=new NextDoorAdapter(nextDoorlist,getActivity());
        next_door_list_view.setAdapter(adapter);
        return view;
    }
    /**
     * 获取网络的糗事列表数据
     * 判断当前请求类型：
     *  0：直接请求
     *  1：下拉刷新
     *  2：上拉加载
     * */
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
        String url="http://circle.qiushibaike.com/article/nearby/list?page="+page+"&count=30";
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
                UtilsDB.getNextDoorList(getActivity(),result);
                if (type==LOAD) {
                    nextDoorlist.addAll(UtilsDB.getDataNextDoor(getActivity()));
                    pull.onFooterLoadFinish();
                }else{
                    nextDoorlist=UtilsDB.getDataNextDoor(getActivity());
                    pull.onHeaderRefreshFinish();
                }
                if (nextDoorlist!=null){
                    adapter.setList(nextDoorlist);
                }else {
                    return;
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                nextDoorlist=UtilsDB.getDataNextDoor(getActivity());
                if (nextDoorlist!=null){
                    adapter.setList(nextDoorlist);
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

    @Override
    public void setData() {
        getNetData(0);
        //调用pull绑定监听事件的方法
        setPullListener();
    }
}

