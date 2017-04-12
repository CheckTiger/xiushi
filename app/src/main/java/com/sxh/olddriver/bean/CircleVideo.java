package com.sxh.olddriver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class CircleVideo implements Serializable {
    public List<DataBean> data;
    public  static class DataBean {
        public String distance;
        public int comment_count;
        public String content;
        public TopicBean topic;
        public int like_count;
        public int type;
        public VideoBean video;
        public UserBean user;
        public  class TopicBean {
            public List<AvatarUrlsBean> avatar_urls;
            public  class AvatarUrlsBean {
                public String pic_url;
            }
        }
        public  static class VideoBean {
            public int height;
            public int width;
            public String high_url;
            public String low_url;
            public int duration;
            public String pic_url;

        }
        public  static class UserBean {
            public int id;
            public String gender;
            public String login;
            public int age;
            public String icon;
        }
    }
}
