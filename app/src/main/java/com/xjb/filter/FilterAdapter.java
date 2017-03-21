package com.xjb.filter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xujingbo on 2016/11/16.
 */

public class FilterAdapter extends ListBaseAdapter {
    private static final String TAG = "====FilterAdapter";
    private List<String> selectedItems = new ArrayList<>();

    public FilterAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filter, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        super.onBindViewHolder(viewHolder, position);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvContent.setText(dataList.get(position).toString());
        holder.tvSelected.setVisibility(View.GONE);
        if (isSelected(position)) {
            holder.tvContent.setSelected(true);
            holder.tvSelected.setVisibility(View.VISIBLE);
        }else {
            holder.tvContent.setSelected(false);
            holder.tvSelected.setVisibility(View.GONE);
        }

    }

    public boolean isSelected(int position) {
        return selectedItems != null ? selectedItems.contains(dataList.get(position)) : false;
    }

    public void unSelected(int position) {
        if (selectedItems == null){
            return;
        }
        selectedItems.remove(dataList.get(position));
    }
    public void setSelected(int position){
        if (isSelected(position)) {
            unSelected(position);
        } else {
            if (selectedItems != null) {
                selectedItems.add((String) dataList.get(position));
            }
        }
        notifyDataSetChanged();
    }
    public void clearSelectedItems(){
        if (selectedItems != null) {
            selectedItems.clear();
        }else {
            selectedItems = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
    /**
     * 获取选择的类型
     * @return 内容集合
     */
    public List<String> getSelectedDatas(){
        return selectedItems;
    }

    public void setSelectedItems(List<String> selectedItems) {
        Log.d(TAG, "setSelectedItems: size " + selectedItems.size());
        for (String str:selectedItems){
            Log.d(TAG, "setSelectedItems: "+str);
        }
        this.selectedItems = selectedItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_selected)
        TextView tvSelected;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
