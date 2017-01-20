package com.example.yangyu.palmread.Logic;

import java.util.HashMap;

/**
 * Created by yangyu on 2017/1/18.
 */

public class WebCache {
    public static HashMap<String,DataCache> mCacheMap=new HashMap<String, DataCache>();
    public static Object saveMemoryCache(String key,Object data){
        DataCache cache = mCacheMap.get(key);
        if(cache==null){
            cache=new DataCache(data,System.currentTimeMillis()/1000);
        }else {
            cache.resetData();
            cache.setData(data);
            cache.setmTime(System.currentTimeMillis()/1000);
        }
        mCacheMap.put(key,cache);
        return data;
    }

    public static Object loadMemoryCache(String key){
        DataCache cache = mCacheMap.get(key);
        if(cache!=null){
            return cache.getData();
        }
        return null;
    }

    public static void resetMemory(String key){
        DataCache dataCache = mCacheMap.get(key);
        if(dataCache==null)return;
        dataCache.resetData();
    }

    static class DataCache{
        private Object data;
        private long mTime;

        public DataCache() {
        }

        public DataCache(Object data, long mTime) {
            this.data = data;
            this.mTime = mTime;
        }

        public void resetData(){
            data=null;
            mTime=0;
        }
        public void setmTime(long mTime) {
            this.mTime = mTime;
        }

        public long getmTime() {
            return mTime;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            if((mTime+3600)>System.currentTimeMillis()/1000) return data;
            return null;
        }
    }
}
