package com.example.yangyu.palmread.Models;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * Created by yangyu on 2017/1/13.
 */
@JSONType
public class GetHomePageresult {
    @JSONField(name = "reason")
    public String mReason;
    @JSONField(name = "error_code")
    public int error_code;
    @JSONField(name = "result")
    public PageResult mResult;

    @JSONType
    public class PageResult{
        @JSONField(name = "stat")
        public int mStat;
        @JSONField(name = "data")
        public PageData[] mData;
    }

    @JSONType
    public class PageData{
        @JSONField(name = "uniquekey")
        public String mUniquekey;
        @JSONField(name = "title")
        public String mTitle;
        @JSONField(name = "date")
        public String mData;
        @JSONField(name = "category")
        public String mCategory;
        @JSONField(name = "author_name")
        public String mAutorName;
        @JSONField(name = "url")
        public String mContent;
        @JSONField(name = "thumbnail_pic_s")
        public String mPicOne;
        @JSONField(name = "thumbnail_pic_s02")
        public String mPicTwo;
        @JSONField(name = "thumbnail_pic_s03")
        public String mPicThree;
    }

}
