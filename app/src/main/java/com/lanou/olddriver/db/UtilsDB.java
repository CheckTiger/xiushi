package com.lanou.olddriver.db;

import android.content.Context;
import android.database.Cursor;

import com.lanou.olddriver.bean.CircleVideo;
import com.lanou.olddriver.bean.Data;
import com.lanou.olddriver.bean.Pic_urls;
import com.lanou.olddriver.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/8/2.
 */
public class UtilsDB {

   public static ArrayList<Data> myList;
   public static ArrayList<CircleVideo.DataBean> myList1;
   public static List<Pic_urls> listpic;
    /**
     * 获取数据的解析
     * */
    public static void getNextDoorList(Context context, String json){
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray array=jsonObject.optJSONArray("data");
            for (int i = 0; i <array.length(); i++) {

                Data data=new Data();
                int s;
                JSONObject object=array.optJSONObject(i);
                if (object.optInt("type")==1||object.optInt("type")==2||object.optInt("type")==0) {
                    data.comment_count = object.optInt("comment_count");
                    data.like_count = object.optInt("like_count");
                    data.content = object.optString("content");
                    data.distance = object.optString("distance");
                    data.created_at=object.optInt("created_at");
                    data.is_me=object.optBoolean("is_me");
                    if (data.is_me){
                       s=1;
                    }else {
                        s=2;
                    }
                    data.id=object.optInt("id");
                    JSONObject objectUser = object.optJSONObject("user");
                    data.user = new User();
                    data.user.age = objectUser.optInt("age");
                    data.user.gender=objectUser.optString("gender");
                    data.user.id=objectUser.optInt("id");
                    data.user.login=objectUser.optString("login");
                    data.user.icon=objectUser.optString("icon");
                    JSONArray objectPic = object.optJSONArray("pic_urls");
                    for (int j = 0; j < objectPic.length(); j++) {
                        Pic_urls pic=new Pic_urls();
                        JSONObject object2=objectPic.optJSONObject(j);
                        pic.pic_url=object2.optString("pic_url");
                        MyDBHelperPic.getInstances(context).insert(data.id,pic.pic_url);
                    }
                    MyDBHelperData.getInstances(context).insert(data.comment_count, data.like_count,data.content,data.distance,data.created_at,data.user.age, data.user.gender,data.user.id,data.user.login,data.user.icon,s,data.id);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Data> getDataNextDoor(Context context) {
        ArrayList<Data> myList = new ArrayList<>();
        Cursor c = MyDBHelperData.getInstances(context).query();
        if (c.moveToFirst()) {
            do {
                Data s = new Data();
                int id = c.getInt(c.getColumnIndex("id"));
                int comment_count = c.getInt(c.getColumnIndex("comment_count"));
                int like_count = c.getInt(c.getColumnIndex("like_count"));
                String content=c.getString(c.getColumnIndex("content"));
                String distance=c.getString(c.getColumnIndex("distance"));
                int created_at=c.getInt(c.getColumnIndex("created_at"));
                int age=c.getInt(c.getColumnIndex("age"));
                String gender=c.getString(c.getColumnIndex("gender"));
                int iduser=c.getInt(c.getColumnIndex("iduser"));
                String login=c.getString(c.getColumnIndex("login"));
                String icon=c.getString(c.getColumnIndex("icon"));
                int pic=c.getInt(c.getColumnIndex("pic"));
                int is_me=c.getInt(c.getColumnIndex("is_me"));
                Cursor d=MyDBHelperPic.getInstances(context).query(pic);
                List<Pic_urls> listpic=new ArrayList<>();
                if (d.moveToFirst()){
                    do {
                        Pic_urls p=new Pic_urls();
                        p.pic_url=d.getString(d.getColumnIndex("content"));
                        listpic.add(p);
                    }while (d.moveToNext());
                }
                //HashMap<String, String> map = new HashMap<>();
                // c.getColumnIndex("title") 获取到title这一列在第几列
                // c.getString()通过列数，得到这一列的这一行的内容
                s.id=pic;
                s.comment_count=comment_count;
                s.like_count=like_count;
                s.content=content;
                if (is_me==1){
                    s.is_me=true;
                }else {
                    s.is_me=false;
                }
                s.distance=distance;
                s.created_at=created_at;
                s.user = new User();
                s.user.gender=gender;
                s.user.age=age;
                s.user.id=iduser;
                s.user.login=login;
                s.user.icon=icon;
                s.pic_urls=listpic;
                //将对象添加到list中
                myList.add(s);
            } while (c.moveToNext());
        }
        return myList;
    }
    /**
     * 获取数据的解析
     * */
    public static void getCirclevideoList(Context context, String json){
        try {
            JSONObject object=new JSONObject(json);
            JSONArray array=object.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                CircleVideo.DataBean dataBean=new CircleVideo.DataBean();
                JSONObject object1=array.optJSONObject(i);
                if (object1.optInt("type")==10){
                    dataBean.comment_count=object1.optInt("comment_count");
                    dataBean.content=object1.optString("content");
                    dataBean.distance=object1.optString("distance");
                    dataBean.like_count=object1.optInt("like_count");
                    JSONObject objectvideo=object1.optJSONObject("video");
                    dataBean.video=new CircleVideo.DataBean.VideoBean();
                    dataBean.video.height=objectvideo.optInt("height");
                    dataBean.video.width=objectvideo.optInt("width");
                    dataBean.video.duration=objectvideo.getInt("duration");
                    dataBean.video.high_url=objectvideo.optString("high_url");
                    dataBean.video.low_url=objectvideo.optString("low_url");
                    dataBean.video.pic_url=objectvideo.optString("pic_url");
                    JSONObject objectUser=object1.optJSONObject("user");
                    dataBean.user=new CircleVideo.DataBean.UserBean();
                    dataBean.user.age=objectUser.optInt("age");
                    dataBean.user.login=objectUser.optString("login");
                    dataBean.user.id=objectUser.optInt("id");
                    dataBean.user.gender=objectUser.optString("gender");
                    dataBean.user.icon=objectUser.optString("icon");
                    MyDBHelperCircleVideo.getInstances(context).insert(
                            dataBean.comment_count,
                            dataBean.like_count,dataBean.content,
                            dataBean.video.high_url,dataBean.video.low_url,
                            dataBean.video.pic_url,dataBean.distance,
                            dataBean.user.age, dataBean.user.gender,
                            dataBean.user.id,dataBean.user.login,
                            dataBean.user.icon,dataBean.video.duration);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //根据查询数据库，寻找circlevideo内容

    public static ArrayList<CircleVideo.DataBean> getDataCircleVideo(Context context) {
        ArrayList<CircleVideo.DataBean> myList1 = new ArrayList<>();
        Cursor c = MyDBHelperCircleVideo.getInstances(context).query();
        if (c.moveToFirst()) {

            do {
                CircleVideo.DataBean s = new CircleVideo.DataBean();
                // c.getColumnIndex("title") 获取到title这一列在第几列
                // c.getString()通过列数，得到这一列的这一行的内容
                int comment_count=c.getInt(c.getColumnIndex("comment_count"));
                int like_count=c.getInt(c.getColumnIndex("like_count"));
                String content = c.getString(c.getColumnIndex("content"));
                String high_url=c.getString(c.getColumnIndex("high_url"));
                String low_url=c.getString(c.getColumnIndex("low_url"));
                String pic_url=c.getString(c.getColumnIndex("pic_url"));
                String distance=c.getString(c.getColumnIndex("distance"));
                int age=c.getInt(c.getColumnIndex("age"));
                String gender=c.getString(c.getColumnIndex("gender"));
                int iduser=c.getInt(c.getColumnIndex("iduser"));
                String login=c.getString(c.getColumnIndex("login"));
                String icon=c.getString(c.getColumnIndex("icon"));
                int duration=c.getInt(c.getColumnIndex("duration"));
                int id = c.getInt(c.getColumnIndex("id"));
                s.comment_count=comment_count;
                s.like_count=like_count;
                s.content=content;
                s.distance=distance;
                CircleVideo.DataBean.VideoBean video=new CircleVideo.DataBean.VideoBean();
                CircleVideo.DataBean.UserBean user=new CircleVideo.DataBean.UserBean();
                video.high_url=high_url;
                video.low_url=low_url;
                video.pic_url=pic_url;
                user.age=age;
                user.gender=gender;
                user.id=iduser;
                user.login=login;
                user.icon=icon;
                video.duration=duration;
                s.user=user;
                s.video=video;
                myList1.add(s);
            } while (c.moveToNext());
        }
        return myList1;
    }
}
