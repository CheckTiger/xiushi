package com.lanou.olddriver.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lanou.olddriver.R;
import com.lanou.olddriver.adapter.CommonNextdoorAdapter;
import com.lanou.olddriver.bean.CommentsBean;
import com.lanou.olddriver.bean.Data;
import com.lanou.olddriver.db.MyDBHelper;
import com.lanou.olddriver.utils.UrlUtils;
import com.lanou.olddriver.view.ImageLoaderUtils;
import com.lanou.olddriver.view.PullToRefreshView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 2016/8/5.
 */
public class NextDoorActivity extends Activity{
    private static final int REQUEST = 0;
    private static final int REFRESH = 1;
    private static final int LOAD = 2;
    private int page = 0;
    private Data data;
    private ListView listnextdoor;
    private PullToRefreshView pull;
    private List<CommentsBean> data_list;
    private CommonNextdoorAdapter commonnextdooradapter;
    private ImageView nextdoor_head;
   //private ImageView qiuyoucircle_action;
    private TextView nextdoor_username;
    private TextView nextdoor_sex;
    //private TextView nextdoor_time;
    private TextView nextdoor_text;
    //private TextView nextdoor_endtext;
    private TextView nextdoor_address;
    private TextView nextdoor_like_text;
    private TextView nextdoor_comment_text;
    private LinearLayout nextdoor_ll;
    private ImageView nextdoor_ll_img_1;
    private ImageView nextdoor_ll_img_2;
    private ImageView nextdoor_ll_img_3;
    private ImageView nextdoor_ll_img_4;
    private ImageView nextdoor_ll_img_5;
    private ImageView nextdoor_ll_img_6;
    private LinearLayout nextdoor_ll_img1;
    private LinearLayout nextdoor_ll_img2;
    //private CheckBox nextdoor_like;
    private ImageView iv_back;
    private ArrayList<HashMap<String, String>> list2;
    private ArrayList<HashMap<String, String>> list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextdoor);
        initView();
        setHeaderData();
        setData();
    }
    private void initView() {
        listnextdoor=(ListView)findViewById(R.id.nextdoor_list_view);
        pull=(PullToRefreshView)findViewById(R.id.nextdoor_pull);
        data_list=new ArrayList<>();
        commonnextdooradapter=new CommonNextdoorAdapter(data_list,this);
        View view_head= LayoutInflater.from(this).inflate(R.layout.nextdoor_item,null);
        listnextdoor.addHeaderView(view_head);
        nextdoor_head= (ImageView)findViewById(R.id.id_nextdoor_head);
        //qiuyoucircle_action= (ImageView)findViewById(R.id.qiuyoucircle_action);
        nextdoor_username= (TextView)findViewById(R.id.id_nextdoor_username);
        nextdoor_sex= (TextView)findViewById(R.id.id_nextdoor_sex);
        //nextdoor_time= (TextView)findViewById(R.id.id_nextdoor_time);
        nextdoor_text= (TextView)findViewById(R.id.id_nextdoor_text);
        //nextdoor_endtext= (TextView)findViewById(R.id.id_nextdoor_endtext);
        nextdoor_address= (TextView)findViewById(R.id.id_nextdoor_address);
        nextdoor_like_text= (TextView)findViewById(R.id.id_nextdoor_like_text);
        nextdoor_comment_text= (TextView)findViewById(R.id.id_nextdoor_comment_text);
        nextdoor_ll= (LinearLayout)findViewById(R.id.id_nextdoor_ll_img);
        nextdoor_ll_img_1=(ImageView)findViewById(R.id.id_nextdoor_ll_img_1);
        nextdoor_ll_img_2=(ImageView)findViewById(R.id.id_nextdoor_ll_img_2);
        nextdoor_ll_img_3=(ImageView)findViewById(R.id.id_nextdoor_ll_img_3);
        nextdoor_ll_img_4=(ImageView)findViewById(R.id.id_nextdoor_ll_img_4);
        nextdoor_ll_img_5=(ImageView)findViewById(R.id.id_nextdoor_ll_img_5);
        nextdoor_ll_img_6=(ImageView)findViewById(R.id.id_nextdoor_ll_img_6);
        nextdoor_ll_img1=(LinearLayout)findViewById(R.id.id_nextdoor_ll_img1);
        nextdoor_ll_img2=(LinearLayout)findViewById(R.id.id_nextdoor_ll_img2);
        //nextdoor_like=(CheckBox)findViewById(R.id.id_nextdoor_like);
        iv_back = (ImageView) findViewById(R.id.common_header_back);//返回按钮
        listnextdoor.setAdapter(commonnextdooradapter);
    }
    private void setHeaderData() {
        data=(Data)getIntent().getSerializableExtra("nextdoordata");
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        nextdoor_ll.setVisibility(View.GONE);
        if (data.user.gender.equals("M")){
            nextdoor_sex.setBackgroundResource(R.drawable.nearby_gender_male);
        }else if (data.user.gender.equals("F")){
            nextdoor_sex.setBackgroundResource(R.drawable.nearby_gender_female);
        }
        //使用ImageLoaderUtils加载网络图片
        if (data.pic_urls.isEmpty()){
            nextdoor_ll.setVisibility(View.VISIBLE);
            ImageView[] str={nextdoor_ll_img_1,nextdoor_ll_img_2,nextdoor_ll_img_3,nextdoor_ll_img_4,nextdoor_ll_img_5,nextdoor_ll_img_6};
            nextdoor_ll_img1.setVisibility(View.GONE);
            nextdoor_ll_img2.setVisibility(View.GONE);
            for (int j = 0; j <data.pic_urls.size(); j++) {
                if (data.pic_urls.size()>2){
                    nextdoor_ll_img1.setVisibility(View.VISIBLE);
                    nextdoor_ll_img2.setVisibility(View.VISIBLE);
                    Glide.with(this).load(data.pic_urls.get(j).pic_url).into(str[j]);
                }
                else {
                    nextdoor_ll_img1.setVisibility(View.VISIBLE);
                    nextdoor_ll_img2.setVisibility(View.GONE);
                    Glide.with(this).load(data.pic_urls.get(j).pic_url).into(str[j]);
                }
            }
        }
        nextdoor_like_text.setText(data.like_count+"");
        nextdoor_username.setText(data.user.login);
        ImageLoaderUtils.getImageByloader(UrlUtils.icon(data.user.id+"",data.user.icon),nextdoor_head);
        nextdoor_text.setText(data.content);
        nextdoor_address.setText(data.distance);
        nextdoor_sex.setText(data.user.age+"");
        nextdoor_comment_text.setText(data.comment_count+"");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//关闭当前页面
            }
        });
    }
    private void setData() {
        getNetData(REQUEST);
        setPullListener();

    }

    private void setPullListener() {
        //上拉刷新监听
        pull.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                getNetData(REFRESH);
            }
        });
        //下拉加载监听
        pull.setOnFooterLoadListener(new PullToRefreshView.OnFooterLoadListener() {
            @Override
            public void onFooterLoad(PullToRefreshView view) {
                getNetData(LOAD);
            }
        });
    }
    /**
     * 请求网络数据,显示精华item对应的评论
     *
     * @param type
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
        }
        String id = data.id + "";//拼接id,不是user里的
        String url = "http://circle.qiushibaike.com/article/"+id+"/info?page="+page;
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //data_list= getNextDoorCommon(result);
                if (type==LOAD) {
                    data_list.addAll(getNextDoorCommon(result));
                    pull.onFooterLoadFinish();
                }else{
                    data_list=getNextDoorCommon(result);
                    pull.onHeaderRefreshFinish();
                }
                if (data_list!=null){
                    commonnextdooradapter.setList(data_list);
                }else {

                    return;
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                list3=getData();
                for (int i = 0; i <list3.size(); i++) {
                    String ss;
                    ss=list3.get(i).get("content");
                    data_list.addAll(getNextDoorCommon(ss));
                }
                if (data_list!=null){
                    commonnextdooradapter.setList(data_list);
                }else {

                    return;
                }
            }
        });
    }
    /**
     *获取数据的解析
     **/
    public  List<CommentsBean> getNextDoorCommon(String json){
        List<CommentsBean> commentsBeanlist=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(json);
            if (jsonObject.isNull("comments")) {
                return commentsBeanlist;
            }
            JSONArray array=jsonObject.optJSONArray("comments");
                for (int i = 0; i <array.length(); i++) {
                    JSONObject object=array.optJSONObject(i);
                    CommentsBean commentsbean=new CommentsBean();
                    commentsbean.article_id=object.optInt("article_id");
                    commentsbean.comment_id=object.optInt("comment_id");
                    commentsbean.content=object.optString("content");
                    commentsbean.like_count=object.optInt("like_count");
                    commentsbean.liked=object.optBoolean("liked");
                    commentsbean.created_at=object.optInt("created_at");
                    commentsbean.id=object.optInt("id");
                    JSONObject object1=object.optJSONObject("user");
                    CommentsBean.UserBean  user=new CommentsBean.UserBean();
                    user.age=object1.optInt("age");
                    user.created_at=object1.optInt("created_at");
                    user.gender=object1.optString("gender");
                    user.icon=object1.optString("icon");
                    user.id=object1.optInt("id");
                    user.login=object1.optString("login");
                    user.nick_status=object1.optInt("nick_status");
                    commentsbean.user=user;
                    commentsBeanlist.add(commentsbean);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyDBHelper.getInstances(this).insert("nextdoorcomment",json);
        return commentsBeanlist;
    }
    public ArrayList<HashMap<String, String>> getData() {
        list2 = new ArrayList<>();
        Cursor c = MyDBHelper.getInstances(this).query();
        if (c.moveToFirst()) {

            do {
                HashMap<String, String> map = new HashMap<>();
                String title = c.getString(c.getColumnIndex("title"));
                String content = c.getString(c.getColumnIndex("content"));
                if (title.equals("nextdoorcomment"))
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
