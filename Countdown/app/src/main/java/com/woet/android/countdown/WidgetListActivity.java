package com.woet.android.countdown;

import android.support.v4.app.Fragment;

/**
 * Created by weichengyongxiao on 2018/5/18.
 */

public class WidgetListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WidgetListFragment();
    }
}
