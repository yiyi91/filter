package com.xjb.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 筛选控件
 * Created by xujingbo on 2016/11/15.
 */

public class FilterView extends RelativeLayout {
    private static final String TAG = "=====FilterView";
    private static final int TYPE_SEND = 0; //配送方式
    private static final int TYPE_CATEGORY = 1;//分类
    private static final int TYPE_BLENDS = 2;//品牌
    private static final int TYPE_ALL = 3;//全部商品
    @Bind(R.id.ll_send)
    LinearLayout llSend;
    @Bind(R.id.fl_send)
    FrameLayout flSend;
    @Bind(R.id.ll_category)
    LinearLayout llCategory;
    @Bind(R.id.fl_category)
    FrameLayout flCategory;
    @Bind(R.id.ll_blends)
    LinearLayout llBlends;
    @Bind(R.id.fl_blends)
    FrameLayout flBlends;
    @Bind(R.id.ll_all)
    LinearLayout llAll;
    @Bind(R.id.fl_all)
    FrameLayout flAll;
    @Bind(R.id.tv_send_type)
    TextView tvSendType;
    @Bind(R.id.tv_send_type_more)
    TextView tvSendTypeMore;
    @Bind(R.id.tv_category)
    TextView tvCategory;
    @Bind(R.id.tv_category_more)
    TextView tvCategoryMore;
    @Bind(R.id.tv_blends)
    TextView tvBlends;
    @Bind(R.id.tv_blends_more)
    TextView tvBlendsMore;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_all_more)
    TextView tvAllMore;
    private Context context;
    private int curPosition = -1;
    private FrameLayout flFilter[];
    private LinearLayout llFilter[];
    private OnFilterClickListener onFilterClickListener;
    private FilterPopupView filterPopupView;

    /**
     * 每个分类下已经选择的类别
     */
    private Map<Integer,List<String>> selectedItems = new HashMap<>();

    private List<String> curSelectedData = new ArrayList<>();
    /**
     * 距离屏幕顶端的距离
     */
    private int topHeight = DensityUtil.dip2px(41);
    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_filter_view, this);
        ButterKnife.bind(this, view);
        flFilter = new FrameLayout[4];
        flFilter[0] = flSend;
        flFilter[1] = flCategory;
        flFilter[2] = flBlends;
        flFilter[3] = flAll;
        llFilter = new LinearLayout[4];
        llFilter[0] = llSend;
        llFilter[1] = llCategory;
        llFilter[2] = llBlends;
        llFilter[3] = llAll;
        if (filterPopupView == null) {
            filterPopupView = new FilterPopupView(context);
        }

    }

    @OnClick({R.id.ll_send, R.id.ll_category, R.id.ll_blends, R.id.ll_all})
    public void onClick(View view) {
        final List<String> data = new ArrayList<>();
        data.add("类一");
        data.add("类二");
        data.add("类三");
        data.add("类四");
        data.add("类五");
        data.add("类六");
        data.add("类七");
        switch (view.getId()) {
            case R.id.ll_send:

                onFilterClick(TYPE_SEND,data);
                break;
            case R.id.ll_category:

                onFilterClick(TYPE_CATEGORY,data);
                break;
            case R.id.ll_blends:
                onFilterClick(TYPE_BLENDS,data);
                break;
            case R.id.ll_all:
                onFilterClick(TYPE_ALL,data);
                break;
        }
    }

    /**
     * 按钮点击事件
     * @param position
     * @param data 该项item对应的类别列表（可从网络或本地获取）
     */
    public void onFilterClick(final int position,List<String> data) {

        curPosition = position;
        if (onFilterClickListener != null) {
            refreshView();
            filterPopupView.showPop(this, topHeight ,data);
            curSelectedData.clear();
            if (selectedItems.get(curPosition) != null ) {
                for (String str : selectedItems.get(curPosition)) {
                    curSelectedData.add(str);
                }
            }
            filterPopupView.setSelectedData(curSelectedData);

            filterPopupView.setListener(new FilterPopupView.OnFilterPopClickListener() {
                @Override
                public void onOKClick(View view, List<String> selectedData) {
                    Log.d(TAG, "onOKClick: " + curPosition);

                    List<String> list = new ArrayList<String>();
                    for (String str:selectedData){
                        list.add(str);
                    }
                    selectedItems.put(curPosition,list);

                    onFilterClickListener.onFilterClick(position,list);
                    curPosition = -1;
                    refreshView();

                }

                @Override
                public void onCancelClick(View v) {
                    curPosition = -1;
                    refreshView();
                }
            });
        }
    }

    private void refreshView() {

        for (int i = 0; i < 4; i++) {
            if (i == curPosition) {
                flFilter[i].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.filter_rect_line_bg));
                llFilter[i].setBackgroundColor(context.getResources().getColor(R.color.transparent));
                //setFiltered(i, selectedItems.get(i));

            }else {
                if (selectedItems.get(i) == null || selectedItems.get(i).size() <= 0) {
                    reSetFilterText(i);
                } else {
                    setFiltered(i, selectedItems.get(i));
                }
            }
        }
    }

    /**
     * 筛选出来的选项
     * @param position
     * @param selectedData
     */
    public void setFiltered(int position, List<String> selectedData) {
        selectedItems.put(position,selectedData);
        if (selectedData == null || selectedData.size() <= 0){
            curPosition = -1;
            refreshView();
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < selectedData.size(); i++){
            builder.append(selectedData.get(i));
            if (i < selectedData.size() - 1) {
                builder.append(",");
            }
        }
        String text = builder.toString();
        llFilter[position].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.rect_red_bg));
        if (position == curPosition){
            flFilter[position].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.filter_rect_line_bg));
            llFilter[position].setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }else {
            flFilter[position].setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
        switch (position){
            case 0:

                tvSendType.setText(text);
                tvSendTypeMore.setVisibility(GONE);
                break;
            case 1:

                tvCategory.setText(text);
                tvCategoryMore.setVisibility(GONE);
                break;
            case 2:

                tvBlends.setText(text);
                tvBlendsMore.setVisibility(GONE);
                break;
            case 3:

                tvAll.setText(text);
                tvAllMore.setVisibility(GONE);
                break;
        }
    }

    public void reSetFilterText(int position){

        flFilter[position].setBackgroundColor(context.getResources().getColor(R.color.transparent));
        llFilter[position].setBackgroundColor(context.getResources().getColor(R.color.filter_color_gray));
        switch (position){
            case 0:
                tvSendType.setText("配送");
                tvSendTypeMore.setVisibility(VISIBLE);
                break;
            case 1:
                tvCategory.setText("分类");
                tvCategoryMore.setVisibility(VISIBLE);
                break;
            case 2:
                tvBlends.setText("品牌");
                tvBlendsMore.setVisibility(VISIBLE);
                break;
            case 3:
                tvAll.setText("全部商品");
                tvAllMore.setVisibility(VISIBLE);
                break;
        }
    }
    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }

    public void setTopHeight(int topHeight) {

        this.topHeight = topHeight;
    }

    public interface OnFilterClickListener {
        void onFilterClick(int position,List<String> selectedItem);
    }
}
