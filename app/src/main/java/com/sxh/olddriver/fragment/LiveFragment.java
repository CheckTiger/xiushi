package com.sxh.olddriver.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.LiveAdapter;
import com.sxh.olddriver.bean.Live;
import com.sxh.olddriver.utils.ShowFragmentData;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/23.
 * 直播页面Fragment
 */
public class LiveFragment extends BaseFragment {

    private Button btn_all;
    private ImageView iv_add;
    private GridView live_grid_view;
    LiveAdapter adapter;
    List<Live> list;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.frag_live, null);
        btn_all = (Button) view.findViewById(R.id.live_btn_all);
        iv_add = (ImageView) view.findViewById(R.id.live_iv_add);
        live_grid_view = (GridView) view.findViewById(R.id.live_grid_view);

        list = new ArrayList<>();
        adapter = new LiveAdapter(list, getActivity());
        live_grid_view.setAdapter(adapter);
        return view;
    }

    @Override
    public void setData() {
        getNetData();
    }

    /**
     * 获取网络数据
     */
    private void getNetData() {
        HttpUtils utils = new HttpUtils();
        String url = "http://live.qiushibaike.com/live/all/list?count=30&page=1";
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                list = ShowFragmentData.getLivePager(result);
                adapter.setList(list);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
}
