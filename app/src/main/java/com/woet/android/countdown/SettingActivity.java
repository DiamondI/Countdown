package com.woet.android.countdown;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.appwidget.AppWidgetManager;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by weichengyongxiao on 2018/5/6.
 */

public class SettingActivity extends AppCompatActivity {
    private static final String EXTRA_WIDGET_ID = "com.woet.android.countdown.widget_id";
    int currentWidgetId;
    RemoteViews mRemoteViews;
    AppWidgetManager mgr;
    EditText mEditTextForTitle;
    EditText mEditTextForDate;
    EditText mEditTextForTime;
    Button mButton;
    Resources res;
    // imageView to choose the background color of the widget
    ImageView iv_ff00cc, iv_6e3ad2, iv_6bddcf, iv_ec682e, iv_be3ac8, iv_4497f5;
    String widget_date = "";
    String widget_time = "";
    SharedPreferences pref;
    SharedPreferences.Editor mEditor;

    boolean is_ff00cc = false, is_6e3ad2 = false, is_6bddcf = false;
    boolean is_ec682e = false, is_be3ac8 = false, is_4497f5 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // regReceiver();
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                "#ff00cc")));
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        res = SettingActivity.this.getResources();
        mEditTextForTitle = findViewById(R.id.et1);
        mEditTextForDate = findViewById(R.id.date_et);
        mEditTextForTime = findViewById(R.id.time_et);
        mButton = findViewById(R.id.bt1);
        iv_ff00cc = findViewById(R.id.color_ff00cc);
        iv_6e3ad2 = findViewById(R.id.color_6e3ad2);
        iv_6bddcf = findViewById(R.id.color_6bddcf);
        iv_ec682e = findViewById(R.id.color_ec682e);
        iv_be3ac8 = findViewById(R.id.color_be3ac8);
        iv_4497f5 = findViewById(R.id.color_4497f5);

        currentWidgetId = this.getIntent().getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if (currentWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            currentWidgetId = this.getIntent().getIntExtra(EXTRA_WIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            Log.i("test", "id from main" + currentWidgetId);
        }
        String title = pref.getString("title" + currentWidgetId, "");
        String date = pref.getString("date" + currentWidgetId, "");
        String time = pref.getString("time" + currentWidgetId, "");
        int bgColor = pref.getInt("bgColor" + currentWidgetId, -1);
        if (bgColor != -1) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(bgColor));
        }

        if (!title.equals("")) {
            mEditTextForTitle.setText(title);
        }

        if (!date.equals("")) {
            mEditTextForDate.setText(date);
        }

        if (!time.equals("")) {
            mEditTextForTime.setText(time);
        }

        mEditTextForTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivity.
                            this.INPUT_METHOD_SERVICE);
                    if (imm != null && imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWidgetView();
                finish();
            }
        });

        mEditTextForDate.setInputType(InputType.TYPE_NULL);
        mEditTextForDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickerDialog();
                }
            }
        });

        mEditTextForDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        mEditTextForTime.setInputType(InputType.TYPE_NULL);
        mEditTextForTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showTimePickerDialog();
                }
            }
        });

        mEditTextForTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        iv_ff00cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#ff00cc")));
                is_ff00cc = true;
                is_6bddcf = false;
                is_6e3ad2 = false;
                is_4497f5 = false;
                is_be3ac8 = false;
                is_ec682e = false;
            }
        });

        iv_6e3ad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#6e3ad2"
                )));
                is_ff00cc = false;
                is_6bddcf = false;
                is_6e3ad2 = true;
                is_4497f5 = false;
                is_be3ac8 = false;
                is_ec682e = false;
                Log.i("test", "" + is_6e3ad2);
            }
        });

        iv_6bddcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#6bddcf"
                )));
                is_ff00cc = false;
                is_6bddcf = true;
                is_6e3ad2 = false;
                is_4497f5 = false;
                is_be3ac8 = false;
                is_ec682e = false;
            }
        });

        iv_ec682e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#ec682e"
                )));
                is_ff00cc = false;
                is_6bddcf = false;
                is_6e3ad2 = false;
                is_4497f5 = false;
                is_be3ac8 = false;
                is_ec682e = true;
            }
        });

        iv_be3ac8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#be3ac8"
                )));
                is_ff00cc = false;
                is_6bddcf = false;
                is_6e3ad2 = false;
                is_4497f5 = false;
                is_be3ac8 = true;
                is_ec682e = false;
            }
        });

        iv_4497f5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
                        "#4497f5"
                )));
                is_ff00cc = false;
                is_6bddcf = false;
                is_6e3ad2 = false;
                is_4497f5 = true;
                is_be3ac8 = false;
                is_ec682e = false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.i("test", "onDestroy is called!");
        super.onDestroy();
        // super.unregisterReceiver(timeReceiver);
    }

    private void updateWidgetView() {
        mEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        mRemoteViews = new RemoteViews(WidgetProvider.class.getPackage().getName(),
                R.layout.widget_layout);
        mgr = AppWidgetManager.getInstance(this);
        String title = mEditTextForTitle.getText().toString();
        if (!title.equals("")) {
            mRemoteViews.setTextViewText(R.id.title, title);
            mEditor.putString("title" + currentWidgetId, title);
        }

        if (!widget_date.equals("")) {
            System.out.println("update date!");
            mRemoteViews.setTextViewText(R.id.my_date, widget_date);
            mEditor.putString("date" + currentWidgetId, widget_date);
        }

        if (!widget_time.equals("")) {
            mEditor.putString("time" + currentWidgetId, widget_time);
            if (!widget_date.equals("")) {
                mRemoteViews.setTextViewText(R.id.my_date, widget_date + "  " +
                        widget_time.substring(0, 5));
            }
        }

        if (is_6bddcf) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#6bddcf"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#6bddcf"));
        }

        if (is_6e3ad2) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#6e3ad2"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#6e3ad2"));
        }

        if (is_4497f5) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#4497f5"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#4497f5"));
        }

        if (is_be3ac8) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#be3ac8"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#be3ac8"));
        }

        if (is_ec682e) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#ec682e"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#ec682e"));
        }

        if (!is_ec682e && !is_be3ac8 && !is_4497f5 && !is_6e3ad2 && !is_6bddcf) {
            mEditor.putInt("bgColor" + currentWidgetId, Color.parseColor("#ff00cc"));
            mRemoteViews.setInt(R.id.bgcolor, "setBackgroundColor",
                    Color.parseColor("#ff00cc"));
        }

        mEditor.apply();

        Calendar c = Calendar.getInstance();
        String current_date = pref.getString("date" + currentWidgetId, "");
        String time = pref.getString("time" + currentWidgetId, "");
        if (time.equals("")) {
            time = widget_time;
        }

        if (current_date.equals("")) {
            current_date = widget_date;
        }

        int year, month, dayOfMonth, hourOfDay, minute;
        if (!current_date.equals("") && !time.equals("")) {
            String [] separated = current_date.split("\n");
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
                mRemoteViews.setChronometer(R.id.chronometer, c.getTimeInMillis() -
                                System.currentTimeMillis() + SystemClock.elapsedRealtime(),
                        null, true);
                mRemoteViews.setInt(R.id.chronometer, "setVisibility", View.VISIBLE);
                mRemoteViews.setInt(R.id.show_days, "setVisibility", View.INVISIBLE);
            } else {
                mRemoteViews.setTextViewText(R.id.show_days, days + " Days Left!");
                mRemoteViews.setInt(R.id.show_days, "setVisibility", View.VISIBLE);
                mRemoteViews.setInt(R.id.chronometer, "setVisibility", View.INVISIBLE);
            }
        }

        // Tell the AppWidgetManager to perform an update on the current App Widget
        mgr.updateAppWidget(currentWidgetId, mRemoteViews);
    }

    private void showDatePickerDialog() {
        Calendar c = Calendar.getInstance();
        String current_date = pref.getString("date" + currentWidgetId, "");
        // 日历当前标记点为当前设置的日期；如果还未设置，则为当天的日期
        if (!current_date.equals("")) {
            String [] separated = current_date.split("\n");
            String [] separated_date = separated[0].split("-");
            int year = Integer.parseInt(separated_date[0]);
            int month = Integer.parseInt(separated_date[1]) - 1;
            int dayOfMonth = Integer.parseInt(separated_date[2]);
            c.set(year, month, dayOfMonth);
        }
        final String[] dayOfWeek = res.getStringArray(R.array.day_of_week);
        new DatePickerDialog(SettingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivity.
                        this.INPUT_METHOD_SERVICE);
                if (imm != null && imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
                Calendar selectDate = Calendar.getInstance();
                selectDate.set(year, month, dayOfMonth);
                String date = String.format(Locale.getDefault(), "%d-%02d-%02d\n%s",
                        year, month + 1, dayOfMonth, dayOfWeek[selectDate.
                                get(Calendar.DAY_OF_WEEK) - 1]);
                mEditTextForDate.setText(date);
                widget_date = date;
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePickerDialog() {
        Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        new TimePickerDialog(SettingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                InputMethodManager imm = (InputMethodManager) getSystemService(SettingActivity.
                        this.INPUT_METHOD_SERVICE);
                if (imm != null && imm.isActive()) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                        hourOfDay, minute, 0);
                mEditTextForTime.setText(time);
                widget_time = time;
            }
        }, hour, minute, true).show();
    }
}