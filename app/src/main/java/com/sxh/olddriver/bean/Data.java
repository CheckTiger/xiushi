package com.sxh.olddriver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2016/7/25.
 */
public class Data implements Serializable {
    public int status;
    public int created_at;

    public String distance;

    public boolean is_me;

    public String at_users;

    public String content;

    public int comment_count;

    public int like_count;

    public List<Pic_urls> pic_urls ;

    public int topic_id;

    public String vote;

    private int type;

    public int id;

    public User user;


}
