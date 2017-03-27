package com.example.yangyu.palmread.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.example.yangyu.palmread.Db.MySqlOpenHelper;
import com.example.yangyu.palmread.Fragment.VideoCollectedFragment;
import com.example.yangyu.palmread.Models.GetVideoResult;

import java.util.ArrayList;

/**
 * Created by yangyu on 17/3/7.
 */

public class VideoCollectDbUtils {
    private static final String TAB_VIDEO_COLLECT_NAME = "collectvideo.db";

    public static long insertDataVideoCollect(Context context, GetVideoResult data) {
        if (!isFavoriteNews(context, data)) {
            MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_VIDEO_COLLECT_NAME, null, 1);
            SQLiteDatabase readableDatabase = helper.getWritableDatabase();
            ContentValues values = new ContentValues(10);
            values.put("title", data.getCaption());
            values.put("image_url", data.getCover_pic());
            values.put("editor_name", data.getScreen_name());
            values.put("editor_photo", data.getAvatar());
            values.put("click_url", data.getUrl());
            values.put("like_count", data.getLikes_count());
            values.put("comment_count", data.getComments_count());
            values.put("look_count", data.getPlays_count());
            values.put("timestamp", System.currentTimeMillis());
            values.put("time", data.getCreated_at());
            return readableDatabase.insert("VideoCollect", null, values);
        } else {
            return -1;
        }
    }

    public static void deleteDataVideoCollect(Context context, GetVideoResult data) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_VIDEO_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();
        String where = "click_url" + "='" + data.getUrl() + "'";
        readableDatabase.delete("VideoCollect", where, null);
    }

    public static void deleteAllDataVideoCollect(Context context) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_VIDEO_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();
//        String where = "click_url" + "='" + data.getUrl() + "'";
        readableDatabase.delete("VideoCollect", null, null);
    }

    public static Cursor quaryDataVideoCollect(Context context) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_VIDEO_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        return readableDatabase.query("VideoCollect", null, null, null, null, null, "timestamp DESC");
    }

    public static ArrayList<GetVideoResult> cursorToPageData(Cursor cursor, Handler handler) {
        int len = cursor.getCount();
        ArrayList<GetVideoResult> datas = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < len; i++) {
            GetVideoResult data = new GetVideoResult();
            data.setCaption(cursor.getString(cursor.getColumnIndex("title")));
            data.setCover_pic(cursor.getString(cursor.getColumnIndex("image_url")));
            data.setScreen_name(cursor.getString(cursor.getColumnIndex("editor_name")));
            data.setAvatar(cursor.getString(cursor.getColumnIndex("editor_photo")));
            data.setUrl(cursor.getString(cursor.getColumnIndex("click_url")));
            data.setLikes_count(cursor.getInt(cursor.getColumnIndex("like_count")));
            data.setComments_count(cursor.getInt(cursor.getColumnIndex("comment_count")));
            data.setPlays_count(cursor.getInt(cursor.getColumnIndex("look_count")));
            data.setTimestamp(cursor.getLong(cursor.getColumnIndex("timestamp")));
            data.setCreated_at(cursor.getString(cursor.getColumnIndex("time")));
            datas.add(i, data);
            cursor.moveToNext();
        }
        cursor.close();
        handler.sendEmptyMessage(VideoCollectedFragment.MSG_NET_NULL);
        return datas;
    }

    public static boolean isFavoriteNews(Context context, GetVideoResult data) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_VIDEO_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        String where = "click_url='" + data.getUrl() + "'";
        Cursor cursor = readableDatabase.query("VideoCollect", new String[]{"click_url"}, where, null, null, null, null);
        boolean b = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return b;
    }
}
