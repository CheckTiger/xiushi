package com.lanou.olddriver.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenluyao on 16/7/22.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    //设置数据库的名字
    public static final String DB_NAME = "Students.db";
    //设置数据库的版本
    public static final int DB_VERSION = 2;
    //设置表的名字
    public static final String TABLE_NAME = "student";

    public static final String TABLE_NAME1= "user";
    public static MyDBHelper helper;

    // 利用单例模式，调用数据库
    public static MyDBHelper getInstances(Context context) {
        if (helper == null) {
            return new MyDBHelper(context);
        } else {
            return helper;
        }
    }

    // 初始化一些东西
    // 上下文，数据库的名称，数据库的工厂，数据库的版本
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 用来创建数据库中的表的回调方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 调用数据库语句的方法
        db.execSQL("create table "
                + TABLE_NAME
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,title text , content text);");
        db.execSQL("create table "
                + TABLE_NAME1
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT,name text , icon text);");
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

            db.execSQL("drop table " + TABLE_NAME1);
            // 通过onCreate方法重新创建新的表
            onCreate(db);
        }
    }

    public void insert(String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        // 类似于HashMap的键值对
        // key - 对应表中的某一列的名称，字段
        // value - 往某一列中插入的值
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        db.insert(TABLE_NAME, null, cv);
    }
    public void insert1(String name, String icon) {
        SQLiteDatabase db = getWritableDatabase();
        // 类似于HashMap的键值对
        // key - 对应表中的某一列的名称，字段
        // value - 往某一列中插入的值
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("icon", icon);
        db.insert(TABLE_NAME1, null, cv);
    }
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
    public Cursor query1() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME1, null, null, null, null, null, null);
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
    //删除
    public void delete1(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String where = "id = ? ";
        String[] whereArgs = {id + ""};
//        String where = "id = ? and title = ?";
//        String[] whereArgs = {id + "",title};
        db.delete(TABLE_NAME1, where, whereArgs);
    }

    public void updata(int id, String title, String content) {
        String where = "id = ? ";
        String[] whereArgs = {id + ""};
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        // 1.表名 2.修改的内容 3.查找条件 4.查找的值
        db.update(TABLE_NAME, cv, where, whereArgs);
    }
    public void updata1(int id, String name, String icon) {
        String where = "id = ? ";
        String[] whereArgs = {id + ""};
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("icon", icon);
        // 1.表名 2.修改的内容 3.查找条件 4.查找的值
        db.update(TABLE_NAME1, cv, where, whereArgs);
    }
    /**
     * 查询数据库中存放的key所对应的content
     * @param key
     * @return
     */
    public String query(String key) {
        SQLiteDatabase db = getReadableDatabase();
        String[] name = new String[] {key};
        Cursor cursor = db.query(TABLE_NAME, name, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String json = cursor.getString(cursor.getColumnIndexOrThrow("content"));
            cursor.close();
            return json;
        }
        return null;
    }
}
