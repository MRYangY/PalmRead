package com.example.yangyu.palmread.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangyu on 17/2/20.
 */

public class MySqlOpenHelper extends SQLiteOpenHelper {
    private static final String TAB_HISTORY_NEWS = "create table NewsHistory("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "image_url_one text,"
            + "image_url_two text,"
            + "image_url_three text,"
            + "editor text,"
            + "link_url text,"
            + "timestamp long,"
            + "time text)";
    private static final String TAB_COLLECT_NEWS = "create table NewsCollect("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "image_url_one text,"
            + "image_url_two text,"
            + "image_url_three text,"
            + "editor text,"
            + "link_url text,"
            + "timestamp long,"
            + "time text)";
    private static final String TAB_HISTORY_VIDEO = "create table VideoHistory("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "image_url text,"
            + "editor_name text,"
            + "editor_photo text,"
            + "click_url text,"
            + "like_count integer,"
            + "comment_count integer,"
            + "look_count integer,"
            + "timestamp long,"
            + "time text)";
    private static final String TAB_COLLECT_VIDEO = "create table VideoCollect("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "image_url text,"
            + "editor_name text,"
            + "editor_photo text,"
            + "click_url text,"
            + "like_count integer,"
            + "comment_count integer,"
            + "look_count integer,"
            + "timestamp long,"
            + "time long)";

    public MySqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TAB_HISTORY_NEWS);
        db.execSQL(TAB_HISTORY_VIDEO);
        db.execSQL(TAB_COLLECT_NEWS);
        db.execSQL(TAB_COLLECT_VIDEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onInsert(SQLiteDatabase db){

    }


}
