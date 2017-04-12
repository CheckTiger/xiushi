package com.sxh.olddriver.fragment;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.sxh.olddriver.R;
import com.sxh.olddriver.adapter.TopicAdapter;
import com.sxh.olddriver.bean.Topic;
import com.sxh.olddriver.db.MyDBHelper;
import com.sxh.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class TopicFragment extends BaseFragment {
    //	当前请求数据为第几页
    int page=0;
    //直接请求常量
    public static final int REQUEST=0;
    //下拉刷新请求常量
    public static final int REFRESH=1;
    ListView topic_list;
    TopicAdapter adapter;
    List<Topic.DataBean> dataList=new ArrayList<Topic.DataBean>();
    PullToRefreshView pull;
    ArrayList<HashMap<String, String>> list2;
    @Override
    public View initView() {
        View viewhead=View.inflate(getActivity(),R.layout.topic_head,null);
        View view=View.inflate(getActivity(), R.layout.topic,null);
        topic_list=(ListView)view.findViewById(R.id.topic_list);
        adapter=new TopicAdapter(dataList,getActivity());
        topic_list.addHeaderView(viewhead);
        topic_list.setAdapter(adapter);
        pull=(PullToRefreshView)view.findViewById(R.id.id_topic_pull);
        return view;
    }

    @Override
    public void setData() {
        getNetData(0);
        //调用pull绑定监听事件的方法
        setPullListener();
    }

    private void setPullListener() {
        //下拉刷新监听事件
        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {


            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
    }
    /**
     * 获取数据的解析
     * */
    public List<Topic.DataBean> getTopicList(String json){
        List<Topic.DataBean> list=new ArrayList<Topic.DataBean>();
        Log.i("TAG", "getCirclevideoList: "+json);
        try {
            JSONObject object=new JSONObject(json);
            JSONArray array=object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                Topic.DataBean dataBean=new Topic.DataBean();
                JSONObject object1=array.optJSONObject(i);
                    dataBean.abstractX=object1.optString("abstract");
                    dataBean.article_count=object1.optInt("article_count");
                    dataBean.is_anonymous=object1.optBoolean("is_anonymous");
                    dataBean.today_article_count=object1.optInt("today_article_count");
                    dataBean.content=object1.optString("content");
                    JSONArray arrayUrls=object1.optJSONArray("avatar_urls");
                    List<Topic.DataBean.AvatarUrlsBean> avatarUrls=new ArrayList<Topic.DataBean.AvatarUrlsBean>();
               for (int j = 0; j <arrayUrls.length(); j++) {
                    Topic.DataBean.AvatarUrlsBean avatarUrlsBean=new Topic.DataBean.AvatarUrlsBean();
                    JSONObject object2=arrayUrls.optJSONObject(j);
                    avatarUrlsBean.pic_url=object2.optString("pic_url");
                    avatarUrls.add(avatarUrlsBean);
               }
                dataBean.avatar_urls=avatarUrls;
                list.add(dataBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyDBHelper.getInstances(getActivity()).insert("topic",json);
        return list;
    }
    private void getNetData(final int type) {
        switch (type) {
            case REQUEST:
                page=1;
                break;
            case REFRESH:
                page=1;
                break;
            default:
                break;
        }
        String url="http://circle.qiushibaike.com/article/topic/top?page=1&count=100&rqcnt=1275&r=fb55f2771469187997616";
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
//                if (type==REFRESH){
//                    dataList=getTopicList(result);
//                    pull.onHeaderRefreshFinish();
//                }
                dataList=getTopicList(result);
                dataList.get(0).rank=1;
                dataList.get(1).rank=2;
                dataList.get(2).rank=3;
                if (dataList!=null){
                    adapter.setList(dataList);
                }else {
                    return;
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                ArrayList<HashMap<String, String>> list3;
                list3=getData();
                for (int i = 0; i <list3.size(); i++) {
                    String ss;
                    ss=list3.get(i).get("content");

                    adapter.setList(getTopicList(ss));
                }
            }
        });
    }

    public ArrayList<HashMap<String, String>> getData() {
        list2 = new ArrayList<>();
        //myList = new ArrayList<>();
        Cursor c = MyDBHelper.getInstances(getActivity()).query();
        if (c.moveToFirst()) {

            do {
                //Students s = new Students();
                HashMap<String, String> map = new HashMap<>();
                // c.getColumnIndex("title") 获取到title这一列在第几列
                // c.getString()通过列数，得到这一列的这一行的内容
                String title = c.getString(c.getColumnIndex("title"));
                String content = c.getString(c.getColumnIndex("content"));
                if (title.equals("nextdoor"))
                {
                    int id = c.getInt(c.getColumnIndex("id"));
                    map.put("id", "" + id);
                    map.put("title", title);
                    map.put("content", content);
                    list2.add(map);
                }
            } while (c.moveToNext());
        }
        return list2;
    }
}
