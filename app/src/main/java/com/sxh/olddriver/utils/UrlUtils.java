package com.sxh.olddriver.utils;

/**
 * Created by user on 2016/7/28.
 */
public class UrlUtils {
    public static String ICON_URL = "http://pic.qiushibaike.com/system/avtnew/";
    public static String PiC_UPL = "http://pic.qiushibaike.com/system/pictures/";

    /**
     * 返回用户头像
     * @param icon_id
     * @param icon
     * @return
     */
    public static String icon(String icon_id, String icon) {
        String id = icon_id.substring(0, 4);
        String str = ICON_URL + id + "/" + icon_id + "/thumb/" + icon;

        return str;
    }

    /**
     * 返回需要加载的网上图片
     * @param pic_id
     * @return
     */
    public static String pic(String pic_id) {
        String id = pic_id.substring(0, 5);//前5位
        String str = PiC_UPL + id + "/" + pic_id + "/medium/app" + pic_id+".jpg";
        return str;
    }
}
