package com.woet.android.countdown;

/**
 * Created by weichengyongxiao on 2018/4/30.
 */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RemoteViews;

import java.util.Calendar;

public class WidgetProvider extends AppWidgetProvider {
    public static final String SETTING_CLICK_ACTION = "CLICK_SETTING"; // 点击事件的广播ACTION
    public static RemoteViews remoteViews;
    public int year, month, dayOfMonth, hourOfDay, minute;
    public boolean dateIsEmpty = true, timeIsEmpty = true;
    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, SettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setAction(SETTING_CLICK_ACTION);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId
                    , intent, PendingIntent.FLAG_UPDATE_CURRENT);
            System.out.println("id = " + appWidgetId);
            String title = pref.getString("title" + appWidgetId, "");
            String date = pref.getString("date" + appWidgetId, "");
            String time = pref.getString("time" + appWidgetId, "");
            int bgColor = pref.getInt("bgColor" + appWidgetId, -1);
            if (bgColor != -1) {
                remoteViews.setInt(R.id.bgcolor, "setBackgroundColor", bgColor);
            }
            remoteViews.setOnClickPendingIntent(R.id.bgcolor, pendingIntent);
            remoteViews.setChronometerCountDown(R.id.chronometer, true);
            remoteViews.setChronometer(R.id.chronometer, SystemClock.elapsedRealtime() + 1800000, null,
                    true);
            if (!title.equals("")) {
                remoteViews.setTextViewText(R.id.title, title);
            }

            if (!date.equals("")) {
                remoteViews.setTextViewText(R.id.my_date, date);
                String [] separated = date.split("\n");
                String [] separated_date = separated[0].split("-");
                year = Integer.parseInt(separated_date[0]);
                month = Integer.parseInt(separated_date[1]) - 1;
                dayOfMonth = Integer.parseInt(separated_date[2]);
                dateIsEmpty = false;
            }

            if (!time.equals("")) {
                String [] separated = time.split(":");
                hourOfDay = Integer.parseInt(separated[0]);
                minute = Integer.parseInt(separated[1]);
                timeIsEmpty = false;
            }

            if (!date.equals("") && !time.equals("")) {
                remoteViews.setTextViewText(R.id.my_date, date + "  " + time.substring(0, 5));
                Calendar c = Calendar.getInstance();
                String [] separated = date.split("\n");
                String [] separated_date = separated[0].split("-");
                year = Integer.parseInt(separated_date[0]);
                month = Integer.parseInt(separated_date[1]) - 1;
                dayOfMonth = Integer.parseInt(separated_date[2]);
                String [] separated_time = time.split(":");
                hourOfDay = Integer.parseInt(separated_time[0]);
                minute = Integer.parseInt(separated_time[1]);
                c.set(year, month, dayOfMonth, hourOfDay, minute);
                long intervals = c.getTimeInMillis() - System.currentTimeMillis();
                long days = intervals / (24 * 60 * 60 * 1000);
                if (days <= 1) {
                    remoteViews.setChronometer(R.id.chronometer, c.getTimeInMillis() -
                                    System.currentTimeMillis() + SystemClock.elapsedRealtime(),
                            null, true);
                    remoteViews.setInt(R.id.chronometer, "setVisibility", View.VISIBLE);
                    remoteViews.setInt(R.id.show_days, "setVisibility", View.INVISIBLE);
                } else {
                    remoteViews.setTextViewText(R.id.show_days, days + " Days Left!");
                    remoteViews.setInt(R.id.show_days, "setVisibility", View.VISIBLE);
                    remoteViews.setInt(R.id.chronometer, "setVisibility",
                            View.INVISIBLE);
                }

            }

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }
    /**
     * 接收窗口小部件点击时发送的广播
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
    /**
     * 每删除一次窗口小部件就调用一次
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    /**
     * 当小部件大小改变时
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
    /**
     * 当小部件从备份恢复时调用该方法
     */
    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }

    public void setBackgroundColor(Context context, int appWidgetId, int Color) {
        remoteViews.setInt(R.id.bgcolor, "setBackgroundColor", Color);
        updateAppWidget(context, appWidgetId, remoteViews);
    }

    public void updateAppWidget(Context context, int appWidgetID, RemoteViews remoteViews) {
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);
        appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
    }
}