package com.sxh.olddriver.bean;

import java.io.Serializable;

/**
 * Created by user on 2016/7/27.
 */
public class VideoShow implements Serializable {
    private String login;//用户
    private String content;//内容
    private String high_url;//播放地址
    private int ammout;//总数
    private int comments_count;//评论数
    private int share_count;//分享数
    private String type;//类型
    private int user_id;//用户id

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private String user_icon;//用户头像

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHigh_url() {
        return high_url;
    }

    public void setHigh_url(String high_url) {
        this.high_url = high_url;
    }

    public int getAmmout() {
        return ammout;
    }

    public void setAmmout(int ammout) {
        this.ammout = ammout;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "login: " + login+ ": content :  " +content;
    }
}
