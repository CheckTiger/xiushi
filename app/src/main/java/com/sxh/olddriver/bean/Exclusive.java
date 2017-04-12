package com.sxh.olddriver.bean;

import java.io.Serializable;

/**
 * Created by user on 2016/7/26.
 * 专享实体类
 */
public class Exclusive implements Serializable {
    private String login;//作者
    private String content;//内容
    private String type;//类型
    private String amount;//总数
    private String comment_count;//评论数
    private String share_count;//分享数
    private int user_id;//用户id
    private String user_icon;//用户标示

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

    public boolean isShowOver() {
        return isShowOver;
    }

    public void setShowOver(boolean showOver) {
        isShowOver = showOver;
    }

    public boolean isShowOver;//显示全部

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getShare_count() {
        return share_count;
    }

    public void setShare_count(String share_count) {
        this.share_count = share_count;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
