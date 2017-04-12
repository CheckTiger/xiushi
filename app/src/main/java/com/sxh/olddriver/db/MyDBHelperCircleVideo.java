package com.sxh.olddriver.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sxh on 16/7/22.
 */
public class MyDBHelperCircleVideo extends SQLiteOpenHelper {
    //设置数据库的名字
    public static final String DB_NAME = "CircleVideo.db";
    //设置数据库的版本
    public static final int DB_VERSION = 2;
    //设置表的名字
    public static final String TABLE_NAME = "circlevideo";

    public static MyDBHelperCircleVideo helper;
    // 利用单例模式，调用数据库
    public static MyDBHelperCircleVideo getInstances(Context context) {
        if (helper == null) {
            return new MyDBHelperCircleVideo(context);
        } else {
            return helper;
        }
    }
    // 初始化一些东西
    // 上下文，数据库的名称，数据库的工厂，数据库的版本
    public MyDBHelperCircleVideo(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // 用来创建数据库中的表的回调方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 调用数据库语句的方法
        db.execSQL("create table "+ TABLE_NAME
                +"(id INTEGER PRIMARY KEY AUTOINCREMENT,comment_count integer,like_count integer,content text,high_url text,low_url text,pic_url text,distance text,age integer,gender text,iduser integer,login text,icon text,duration integer);");
    }
    // 用来更新表
    // onCreate方法只在第一次安装APP的时候调用
    // 之后的数据库，如果想更改表的话，必须使数据库版本上升
    // 或者卸载重新安装
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // 删除老的表
            db.execSQL("drop table " + TABLE_NAME);
            // 通过onCreate方法重新创建新的表
            onCreate(db);
        }
    }
    public void insert(int comment_count,int like_count,String content,String high_url,String low_url,String pic_url,String distance,int age,String gender,int iduser,String login,String icon,int duration) {
        SQLiteDatabase db = getWritableDatabase();
        // 类似于HashMap的键值对
        // key - 对应表中的某一列的名称，字段
        // value - 往某一列中插入的值
        ContentValues cv = new ContentValues();
        cv.put("duration",duration);
        cv.put("low_url",low_url);
        cv.put("pic_url",pic_url);
        cv.put("high_url",high_url);
        cv.put("comment_count", comment_count);
        cv.put("like_count",like_count);
        cv.put("content", content);
        cv.put("distance",distance);
        cv.put("age",age);
        cv.put("gender",gender);
        cv.put("iduser",iduser);
        cv.put("login",login);
        cv.put("icon",icon);
        db.insert(TABLE_NAME, null, cv);
    }
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
    //删除
    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "id = ? ";
        String[] whereArgs = {id + ""};
//        String where = "id = ? and title = ?";
//        String[] whereArgs = {id + "",title};
        db.delete(TABLE_NAME, where, whereArgs);
    }
    public void updata(int id,int comment_count,int like_count,String content,String distance,int created_at,int age,String gender,int iduser,String login,String icon){
        String where = "id = ? ";
        String[] whereArgs = {id + ""};
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("comment_count", comment_count);
        cv.put("like_count",like_count);
        cv.put("content", content);
        cv.put("distance",distance);
        cv.put("created_at",created_at);
        cv.put("age",age);
        cv.put("gender",gender);
        cv.put("iduser",iduser);
        cv.put("login",login);
        cv.put("icon",icon);
        // 1.表名 2.修改的内容 3.查找条件 4.查找的值
        db.update(TABLE_NAME,cv,where,whereArgs);
    }
}
