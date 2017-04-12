package com.sxh.olddriver.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by user on 2016/7/27.
 */
public class Topic implements Serializable {

    public List<DataBean> data;
    public static class DataBean {
        public boolean is_anonymous;
        public String abstractX;
        public int article_count;
        public String content;
        public int today_article_count;
        public List<AvatarUrlsBean> avatar_urls;

        public int rank=0;
        public static class AvatarUrlsBean {
            public String pic_url;
        }
    }


}
