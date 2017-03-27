package com.example.yangyu.palmread.Util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yangyu.palmread.Constant.ProjectContent;
import com.example.yangyu.palmread.Db.MySqlOpenHelper;
import com.example.yangyu.palmread.Models.GetHomePageresult;

import java.util.ArrayList;

/**
 * Created by yangyu on 17/3/7.
 */

public class NewsCollectDbUtils {
    private static final String TAB_NEW_COLLECT_NAME = "collectnews.db";

    public static long insertDataNewCollect(Context context, GetHomePageresult.PageData data) {
        if (!isFavoriteNews(context, data)) {
            MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_NEW_COLLECT_NAME, null, 1);
            SQLiteDatabase readableDatabase = helper.getWritableDatabase();
            ContentValues values = new ContentValues(8);
            values.put("title", data.mTitle);
            values.put("image_url_one", data.mPicOne);
            values.put("image_url_two", data.mPicTwo);
            values.put("image_url_three", data.mPicThree);
            values.put("editor", data.mAutorName);
            values.put("link_url", data.mContent);
            values.put("timestamp", System.currentTimeMillis());
            values.put("time", data.mData);
            return readableDatabase.insert("NewsCollect", null, values);
        } else {
            return -1;
        }
    }

    public static void deleteDataNewCollect(Context context, GetHomePageresult.PageData data) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_NEW_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();
        String where = "link_url" + "='" + data.mContent + "'";
        readableDatabase.delete("NewsCollect", where, null);
    }

    public static void deleteAllDataNewCollect(Context context) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_NEW_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getWritableDatabase();
//        String where = "link_url" + "='" + data.mContent + "'";
        readableDatabase.delete("NewsCollect", null, null);
    }

    public static Cursor quaryDataNewCollect(Context context) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_NEW_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        return readableDatabase.query("NewsCollect", null, null, null, null, null, "timestamp DESC");
    }

    public static ArrayList<GetHomePageresult.PageData> cursorToPageData(Cursor cursor, Context context) {
        int len = cursor.getCount();
        ArrayList<GetHomePageresult.PageData> datas = new ArrayList<>();
        cursor.moveToFirst();
        for (int i = 0; i < len; i++) {
            GetHomePageresult.PageData data = new GetHomePageresult.PageData();
            data.mTitle = cursor.getString(cursor.getColumnIndex("title"));
            data.mPicOne = cursor.getString(cursor.getColumnIndex("image_url_one"));
            data.mPicTwo = cursor.getString(cursor.getColumnIndex("image_url_two"));
            data.mPicThree = cursor.getString(cursor.getColumnIndex("image_url_three"));
            data.mAutorName = cursor.getString(cursor.getColumnIndex("editor"));
            data.mContent = cursor.getString(cursor.getColumnIndex("link_url"));
            data.mTime = cursor.getLong(cursor.getColumnIndex("timestamp"));
            data.mData = cursor.getString(cursor.getColumnIndex("time"));
            datas.add(i, data);
            cursor.moveToNext();
        }
        cursor.close();
        Intent intent = new Intent();
        intent.setAction(ProjectContent.ACTION_NEWS_COLLECT_CHANGE);
        context.sendBroadcast(intent);
        return datas;
    }

    public static boolean isFavoriteNews(Context context, GetHomePageresult.PageData data) {
        MySqlOpenHelper helper = new MySqlOpenHelper(context, TAB_NEW_COLLECT_NAME, null, 1);
        SQLiteDatabase readableDatabase = helper.getReadableDatabase();
        String where = "link_url='" + data.mContent + "'";
        Cursor cursor = readableDatabase.query("NewsCollect", new String[]{"link_url"}, where, null, null, null, null);
        boolean b = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) {
            cursor.close();
        }
        return b;
    }
}
