package com.woet.android.countdown;

/**
 * Created by weichengyongxiao on 2018/5/18.
 */

public class Widget {
    private int mWidgetId;
    private String mTitle;
    private String mDate;
    private String mTime;
    private int mBackgroundColor;

    public Widget(int widgetId, String title, String date, String time, int backgroundColor) {
        mWidgetId = widgetId;
        mTitle = title;
        mDate = date;
        mTime = time;
        mBackgroundColor = backgroundColor;
    }

    public int getWidgetId() {
        return mWidgetId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setWidgetId(int widgetId) {
        mWidgetId = widgetId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
}
