package com.lanou.olddriver.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user on 2016/7/27.
 */
public class NetUtils {

    public static String getUrlSring( String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            String sb = new String();
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(dir, "json.text");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                sb += new String(buffer, 0, len);
                fos.write(buffer, 0, len);
                fos.flush();
            }

            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJsonForFlem(){
        String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(sd, "json.text");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            String json = new String();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                json+= new String(buffer,0,len);
            }
            Log.i("TAG", "getJsonForFlem:  成功");
            return json;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
