package com.lanou.olddriver.fragment;

import android.database.Cursor;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.EssenceAdapter;
import com.lanou.olddriver.bean.Essence;
import com.lanou.olddriver.db.MyDBHelper;
import com.lanou.olddriver.utils.ShowFragmentData;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2016/7/24.
 * 糗事-精华
 */
public class EssenceFragment extends BaseFragment {
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    int page = 0;//请求的页数
    private ListView essence_list_view;
    private List<Essence> essence_list;
    private EssenceAdapter essence_adapter;
    private PullToRefreshView pull_to_refresh;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.essence, null);
        essence_list_view = (ListView) view.findViewById(R.id.essence_list_view);
        pull_to_refresh = (PullToRefreshView) view.findViewById(R.id.essence_pull_to_refresh);
        essence_list = new ArrayList<>();
        essence_adapter = new EssenceAdapter(essence_list, getActivity());
        essence_list_view.setAdapter(essence_adapter);

        //动画
        LayoutAnimationController lac=new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(),R.anim.zoom_in_t));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        essence_list_view.setLayoutAnimation(lac);
        essence_list_view.startLayoutAnimation();
        return view;
    }

    @Override
    public void setData() {
        getNetData(REQUEST);
        setPullListener();
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
        String url = "http://m2.qiushibaike.com/article/list/day?page=" + page + "&count=30";
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                if (type == LOAD) {
                    essence_list.addAll(ShowFragmentData.getEssencePager(result));
                    pull_to_refresh.onFooterLoadFinish();
                } else {
                    essence_list = ShowFragmentData.getEssencePager(result);
                    pull_to_refresh.onHeaderRefreshFinish();
                }
                MyDBHelper.getInstances(getActivity()).insert("essence", result);

                essence_adapter.setList(essence_list);//刷新adapter
            }

            @Override
            public void onFailure(HttpException e, String s) {
                try {
                    String json = MyDBHelper.getInstances(getContext()).query("essence");
                    essence_list.addAll(ShowFragmentData.getEssencePager(json));
                    essence_adapter.setList(essence_list);//刷新adapter
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        });
    }
}
