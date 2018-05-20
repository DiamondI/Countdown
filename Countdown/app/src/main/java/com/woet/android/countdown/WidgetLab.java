package com.woet.android.countdown;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichengyongxiao on 2018/5/18.
 */

public class WidgetLab {
    private static WidgetLab sWidgetLab;

    private List<Widget> mWidgets;

    public SharedPreferences pref;

    public static WidgetLab get(Context context) {
        // Every time call WidgetLab, we should update the instance;
        sWidgetLab = new WidgetLab(context);
        return sWidgetLab;
    }

    private WidgetLab(Context context) {
        ComponentName name = new ComponentName(context, WidgetProvider.class);
        int [] widgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        mWidgets = new ArrayList<>();
        for (int widgetId : widgetIds) {
            int bgColor = pref.getInt("bgColor" + widgetId, -1);
            if (bgColor == -1) {
                continue;
            }
            String title = pref.getString("title" + widgetId, "");
            if (title.equals("")) {
                title = "Untitled";
            }
            String date = pref.getString("date" + widgetId, "");
            if (date.equals("")) {
                date = "Please set date";
            } else {
                String [] separated_date = date.split("\n");
                String goal_date = separated_date[0], goal_day_of_week = separated_date[1];
                date = goal_date + "    " + goal_day_of_week;
            }
            String time = pref.getString("time" + widgetId, "");

            Widget widget = new Widget(widgetId, title, date,
                    time, bgColor);
            mWidgets.add(widget);
        }
    }

    public List<Widget> getWidgets() {
        return mWidgets;
    }

    public Widget getWidget(int widgetId) {
        for (Widget widget : mWidgets) {
            if (widget.getWidgetId() == widgetId) {
                return widget;
            }
        }

        return null;
    }
}
