package com.xjb.filter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.LinearLayout;

import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 筛选弹出框
 * Created by xujingbo on 2016/11/16.
 */

public class FilterPopupView extends PopupWindow implements View.OnClickListener {
    private static final String TAG = "=====FilterPopupView";
    private Context context;
    private List<String> data = new ArrayList<>();
    private FilterAdapter adapter;
    private ViewHolder holder;
    private OnFilterPopClickListener listener;

    private List<String> selectedData = new ArrayList<>();
    private boolean isOkClick = false;
    private int screenVisiableHeight = DensityUtil.getScreenHeight() - DensityUtil.getStatusBarHeight();
    private int screenWidth = DensityUtil.getScreenWidth();

    public FilterPopupView(Context context){
        this(context,null);
    }
    public FilterPopupView(Context context, List<String> data) {
        this.context = context;
        if (null == data){
            this.data = new ArrayList<>();
        }else {
            this.data = data;
        }
        init();
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_filter_popup_view, null);
        holder = new ViewHolder(view);
        adapter = new FilterAdapter(context);
        adapter.setDataList(data);
        holder.recycleFilter.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        holder.recycleFilter.setLayoutManager(gridLayoutManager);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: "+position);
                adapter.setSelected(position);
            }
        });
        int h = screenVisiableHeight;
        this.setContentView(view);
        this.setWidth(screenWidth);
        this.setHeight(h);
        ColorDrawable dw = new ColorDrawable(00000000);
        this.setBackgroundDrawable(dw);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        this.update();
        holder.llPop.setOnClickListener(this);
        holder.tvOk.setOnClickListener(this);
        holder.tvReset.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reset:
                reSet();
                break;
            case R.id.tv_ok:

                if (listener != null){
                    selectedData = adapter.getSelectedDatas();

                    listener.onOKClick(holder.tvOk, selectedData);
                    isOkClick = true;
                }
                this.dismiss();
                break;
            case R.id.ll_pop:
                this.dismiss();

                break;
        }
    }

    public void showPop(View view,int offHeightY,List<String> data) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            this.setHeight(screenVisiableHeight - offHeightY);
            adapter.setDataList(data);
            adapter.clearSelectedItems();
            this.showAsDropDown(view);

            this.update();
        }
    }

    public void setListener(OnFilterPopClickListener listener) {
        this.listener = listener;
    }

    /**
     * 重置 清除选择的内容
     */
    private void reSet() {
        adapter.clearSelectedItems();
        selectedData = null;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (isOkClick){

            isOkClick = false;
        }else {

            if (listener != null) {
                listener.onCancelClick(holder.llPop);
            }
        }
    }

    public void setSelectedData(List<String> selectedData) {
        this.selectedData = selectedData;
        if (selectedData != null) {
            adapter.setSelectedItems(selectedData);
        }
    }
    static class ViewHolder {
        @Bind(R.id.recycle_filter)
        RecyclerView recycleFilter;
        @Bind(R.id.tv_reset)
        TextView tvReset;
        @Bind(R.id.tv_ok)
        TextView tvOk;
        @Bind(R.id.ll_pop)
        LinearLayout llPop;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface OnFilterPopClickListener{
        void onOKClick(View view,List<String> selectedData);
        void onCancelClick(View v);
    }
}
