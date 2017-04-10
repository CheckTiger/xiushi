package com.lanou.olddriver.utils;

import android.util.Log;

import com.lanou.olddriver.bean.Essence;
import com.lanou.olddriver.bean.CommonDetails;
import com.lanou.olddriver.bean.Exclusive;
import com.lanou.olddriver.bean.Live;
import com.lanou.olddriver.bean.PlainImage;
import com.lanou.olddriver.bean.PlainText;
import com.lanou.olddriver.bean.Through;
import com.lanou.olddriver.bean.VideoShow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/7/26.
 */


public class ShowFragmentData {
    /**
     * 解析专享页面
     *
     * @param json
     * @return
     */
    public static List<Exclusive> getExclusive(String json) {
        List<Exclusive> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");

            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                Exclusive exclusive = new Exclusive();
                exclusive.setContent(jsonObject1.optString("content"));
                exclusive.setType(jsonObject1.optString("type"));
                exclusive.setComment_count(jsonObject1.optString("comments_count"));
                exclusive.setShare_count(jsonObject1.optString("share_count"));

                JSONObject userObject = jsonObject1.optJSONObject("user");
                exclusive.setLogin(userObject.optString("login"));
                exclusive.setUser_id(userObject.optInt("id"));
                exclusive.setUser_icon(userObject.optString("icon"));

                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                exclusive.setAmount(votesObject.optString("up"));
                list.add(exclusive);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("123", "123");
        }
        return list;
    }

    /**
     * 解析视频页面
     *
     * @param json
     * @return
     */
    public static List<VideoShow> getVideoPager(String json) {
        List<VideoShow> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                VideoShow videoShow = new VideoShow();
                videoShow.setType(jsonObject1.optString("type"));
                videoShow.setContent(jsonObject1.optString("content"));
                videoShow.setComments_count(jsonObject1.optInt("comments_count"));
                videoShow.setShare_count(jsonObject1.optInt("share_count"));
                videoShow.setHigh_url(jsonObject1.optString("high_url"));

                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                videoShow.setAmmout(votesObject.optInt("up"));

                if (jsonObject1.isNull("user")) {//从jsonBoject1里面判断是否有user这个对象
                    continue;
                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
//                if (userObject.isNull("login")) {
//                    continue;
//                }
                videoShow.setLogin(userObject.optString("login"));
                videoShow.setUser_id(userObject.optInt("id"));
                videoShow.setUser_icon(userObject.optString("icon"));
                list.add(videoShow);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析纯文本页面
     *
     * @param json
     * @return
     */
    public static List<PlainText> getPlainTextPager(String json) {
        List<PlainText> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                PlainText plainText = new PlainText();
                plainText.setContent(jsonObject1.optString("content"));

                plainText.setComments_count(jsonObject1.optInt("comments_count"));
                plainText.setShare_count(jsonObject1.optInt("share_count"));
                plainText.setId(jsonObject1.optInt("id"));//拼接评论用的id
                plainText.setType(jsonObject1.optString("type"));

                if (jsonObject1.isNull("user")) {
                    continue;
                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
                if (userObject.isNull("login")) {
                    continue;
                }
                plainText.setLogin(userObject.optString("login"));
                plainText.setUser_id(userObject.optInt("id"));
                plainText.setUser_icon(userObject.optString("icon"));

                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                plainText.setAmmout(votesObject.optInt("up"));
                list.add(plainText);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析纯图页面
     *
     * @param json
     * @return
     */
    public static List<PlainImage> getPlainImagePager(String json) {
        List<PlainImage> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                PlainImage plainImage = new PlainImage();
                plainImage.setContent(jsonObject1.optString("content"));
                plainImage.setComments_count(jsonObject1.optInt("comments_count"));
                plainImage.setShare_count(jsonObject1.optInt("share_count"));
                plainImage.setType(jsonObject1.optString("type"));
                plainImage.setId(jsonObject1.optInt("id"));
                plainImage.setImage(jsonObject1.optString("image"));

                if (jsonObject1.isNull("user")) {
                    continue;
                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
                if (userObject.isNull("login")) {
                    continue;
                }
                plainImage.setLogin(userObject.optString("login"));
                plainImage.setUser_id(userObject.optInt("id"));
                plainImage.setUser_icon(userObject.optString("icon"));


                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                plainImage.setAmmout(votesObject.optInt("up"));
                list.add(plainImage);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析精华界面
     *
     * @param json
     * @return
     */
    public static List<Essence> getEssencePager(String json) {
        List<Essence> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                Essence essence = new Essence();
                essence.setType(jsonObject1.optString("type"));
                essence.setContent(jsonObject1.optString("content"));
                essence.setComments_count(jsonObject1.optInt("comments_count"));
                essence.setShare_count(jsonObject1.optInt("share_count"));
                essence.setHigh_url(jsonObject1.optString("high_url"));
                essence.setFormat(jsonObject1.optString("format"));//显示内容格式

                essence.setImage_id(jsonObject1.optInt("id"));
                essence.setImage_icon(jsonObject1.optString("icon"));

                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                essence.setAmmout(votesObject.optInt("up"));

//                if (jsonObject1.isNull("user")) {//从jsonBoject1里面判断是否有user这个对象
//                    continue;
//                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
//                if (userObject.isNull("login")) {
//                    continue;
//                }
                essence.setLogin(userObject.optString("login"));
                essence.setUser_id(userObject.optInt("id"));
                essence.setUser_icon(userObject.optString("icon"));
                list.add(essence);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析穿越页面
     *
     * @param json
     * @return
     */
    public static List<Through> getFragmentThrough(String json) {
        List<Through> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                Through through = new Through();
                through.setContent(jsonObject1.optString("content"));
                through.setComments_count(jsonObject1.optInt("comments_count"));
                through.setShare_count(jsonObject1.optInt("share_count"));
                through.setType(jsonObject1.optString("type"));
                through.setId(jsonObject1.optInt("id"));

                if (jsonObject1.isNull("user")) {
                    continue;
                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
                through.setUser_id(userObject.optInt("id"));
                through.setUser_icon(userObject.optString("icon"));
//                if (userObject.isNull("login")){
//                    continue;
//                }
                through.setLogin(userObject.optString("login"));

                JSONObject votesObject = jsonObject1.optJSONObject("votes");
                through.setAmmout(votesObject.optInt("up"));
                list.add(through);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /*
    解析直播界面
     */

    public static List<Live> getLivePager(String json) {
        List<Live> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray arrayItems = jsonObject.optJSONArray("lives");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                Live live = new Live();
                live.setContent(jsonObject1.optString("content"));
                live.setVisitors_count(jsonObject1.optInt("visitors_count"));
                live.setThumbnail_url(jsonObject1.optString("thumbnail_url"));
                live.setHdl_live_url(jsonObject1.optString("hdl_live_url"));
                JSONObject jsonObject2 = jsonObject1.optJSONObject("author");
                live.setName(jsonObject2.optString("name"));
                list.add(live);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 精华二级页面
     *
     * @param json
     * @return
     */
    public static List<CommonDetails> getDetails(String json) {
        List<CommonDetails> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.isNull("items")) {
                return list;
            }
            JSONArray arrayItems = jsonObject.optJSONArray("items");
            for (int i = 0; i < arrayItems.length(); i++) {
                JSONObject jsonObject1 = arrayItems.optJSONObject(i);
                CommonDetails essenceDetails = new CommonDetails();
                essenceDetails.setContent(jsonObject1.optString("content"));
                essenceDetails.setLike_count(jsonObject1.optInt("like_count"));
                if (jsonObject1.isNull("user")) {
                    continue;
                }
                JSONObject userObject = jsonObject1.optJSONObject("user");
                essenceDetails.setLogin(userObject.optString("login"));
                essenceDetails.setComment_id(userObject.optInt("id"));
                if (userObject.isNull("icon")) {
                    continue;
                }
                essenceDetails.setComment_icon(userObject.optString("icon"));
                list.add(essenceDetails);
                Log.i("TAG", "getEssenceDetailsdfsdgfdhfghg: " + essenceDetails.toString());
            }
            return list;
        } catch (JSONException e) {
            Log.i("TAG", "getEssenceDetails: 请求异常");
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    //public static List<>
}
