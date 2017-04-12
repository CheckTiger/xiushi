package com.sxh.olddriver.bean;

/**
 * Created by user on 2016/7/28.
 */
public class CommonDetails {
    private String login;//评论人名
    private String content;//评论内容
    private int like_count;//点赞数
    private int comment_id;//评论人id
    private String comment_icon;//评论人标示


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

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_icon() {
        return comment_icon;
    }

    public void setComment_icon(String comment_icon) {

        this.comment_icon = comment_icon;
    }

    @Override
    public String toString() {
        return "用户: "+ getLogin()+": 内容: "+getContent();
    }
}

