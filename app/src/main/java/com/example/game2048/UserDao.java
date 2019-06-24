package com.example.game2048;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 数据库操作类
 */
public class UserDao {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "sqlite_dbname";
    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static UserDao sUserDao;

    private SQLiteDatabase db;

    private UserDao(Context context) {
        OpenHelper dbHelper = new OpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取SqliteDB实例
     *
     * @param context
     */
    public synchronized static UserDao getInstance(Context context) {
        if (sUserDao == null) {
            sUserDao = new UserDao(context);
        }
        return sUserDao;
    }

    /**
     * 将User实例存储到数据库--注册
     */
    public int saveUser(User user) {
        if (user != null) {

            Cursor cursor = db.rawQuery("select * from User where username=?", new String[]{user.getUsername().toString()});
            if (cursor.getCount() > 0) {
                return -1;
            } else {
                try {
                    db.execSQL("insert into User(username,userpwd) values(?,?) ", new String[]{user.getUsername().toString(), user.getUserpwd().toString()});
                } catch (Exception e) {
                    Log.d("错误", e.getMessage().toString());
                }
                return 1;
            }
        } else {
            return 0;
        }
    }

    /**
     * 从数据库读取User信息--登录
     */
    public User login(String pwd, String name) {

        User user = null;
        Cursor cursor = db.rawQuery("select * from User where userpwd=? and username=?", new String[]{pwd, name});

        if (cursor.moveToNext()) {

            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            user.setUserpwd(cursor.getString(cursor.getColumnIndex("userpwd")));

        }


        return user;

    }

}