package com.lanou.olddriver.bean;

import java.io.Serializable;

/**
 * Created by zzl on 2016/7/27.
 * 直播数据bean
 */
public class Live implements Serializable {

    private String name;
    private String content;
    private int visitors_count;
    private String thumbnail_url;
    private String hdl_live_url;

    public String getHdl_live_url() {
        return hdl_live_url;
    }

    public void setHdl_live_url(String hdl_live_url) {
        this.hdl_live_url = hdl_live_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getVisitors_count() {
        return visitors_count;
    }

    public void setVisitors_count(int visitors_count) {
        this.visitors_count = visitors_count;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }
}
