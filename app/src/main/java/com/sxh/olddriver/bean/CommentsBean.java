package com.sxh.olddriver.bean;

import java.io.Serializable;

/**
 * Created by user on 2016/8/5.
 */
    public  class CommentsBean implements Serializable{
        public int status;
        public boolean liked;
        public int user_id;
        public int created_at;
        public int comment_id;
        public String content;
        public int like_count;
        public UserBean user;
        public int article_id;
        public int id;
        public static class UserBean {
            public String gender;
            public int created_at;
            public int nick_status;
            public int age;
            public String login;
            public int id;
            public String icon;
        }
    }

