package com.woet.android.countdown;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by weichengyongxiao on 2018/5/18.
 */

public class WidgetListFragment extends Fragment {

    private RecyclerView mWidgetRecyclerView;
    private WidgetAdapter mWidgetAdapter;

    private static final String EXTRA_WIDGET_ID = "com.woet.android.countdown.widget_id";

    public static Intent newIntent(Context packageContext, int widgetId) {
        Intent intent = new Intent(packageContext, SettingActivity.class);
        intent.putExtra(EXTRA_WIDGET_ID, widgetId);
        return intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_widget_list, container, false);

        mWidgetRecyclerView = view.findViewById(R.id.widget_recycler_view);
        mWidgetRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private class WidgetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Widget mWidget;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public WidgetHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_widget, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.widget_title);
            mDateTextView = itemView.findViewById(R.id.widget_date);
        }

        public void bind(Widget widget) {
            mWidget = widget;
            mTitleTextView.setText(mWidget.getTitle());
            mDateTextView.setText(mWidget.getDate());
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(getActivity(), mWidget.getTitle() + " clicked!", Toast.LENGTH_LONG)
            //        .show();
            Intent intent = newIntent(getActivity(), mWidget.getWidgetId());
            startActivity(intent);
        }
    }

    private class WidgetAdapter extends RecyclerView.Adapter<WidgetHolder> {
        private List<Widget> mWidgets;

        public WidgetAdapter(List<Widget> widgets) {
            mWidgets = widgets;
        }

        @Override
        public WidgetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new WidgetHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(WidgetHolder holder, int position) {
            Widget widget = mWidgets.get(position);
            holder.bind(widget);
        }

        @Override
        public int getItemCount() {
            return mWidgets.size();
        }
    }

    private void updateUI() {
        WidgetLab widgetLab = WidgetLab.get(getActivity());
        List<Widget> widgets = widgetLab.getWidgets();

        mWidgetAdapter = new WidgetAdapter(widgets);
        mWidgetRecyclerView.setAdapter(mWidgetAdapter);
    }
}
