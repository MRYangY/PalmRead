package com.example.yangyu.palmread.Models;

import android.os.Parcel;
import android.os.Parcelable;

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
    public static class PageResult{
        @JSONField(name = "stat")
        public int mStat;
        @JSONField(name = "data")
        public PageData[] mData;
    }

    @JSONType
    public static class PageData implements Parcelable{
        public PageData() {
        }

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

        public long mTime;

        protected PageData(Parcel in) {
            mUniquekey = in.readString();
            mTitle = in.readString();
            mData = in.readString();
            mCategory = in.readString();
            mAutorName = in.readString();
            mContent = in.readString();
            mPicOne = in.readString();
            mPicTwo = in.readString();
            mPicThree = in.readString();
            mTime = in.readLong();
        }

        public static final Creator<PageData> CREATOR = new Creator<PageData>() {
            @Override
            public PageData createFromParcel(Parcel in) {
                return new PageData(in);
            }

            @Override
            public PageData[] newArray(int size) {
                return new PageData[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mUniquekey);
            dest.writeString(mTitle);
            dest.writeString(mData);
            dest.writeString(mCategory);
            dest.writeString(mAutorName);
            dest.writeString(mContent);
            dest.writeString(mPicOne);
            dest.writeString(mPicTwo);
            dest.writeString(mPicThree);
            dest.writeLong(mTime);
        }
    }

}
