package com.xjb.filter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.HeaderSpanSizeLookup;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 筛选界面
 *
 * @author xujingbo
 * @date 2017/3/7 16:53
 */
public class FilterActivity extends AppCompatActivity implements FilterView.OnFilterClickListener, SortView.onGeneralClickListener, SortView.onSaveMoneyClickListener, SortView.onPriceClickListener {
    @Bind(R.id.recycle_goods)
    LRecyclerView recycleGoods;
    @Bind(R.id.filter_view_top)
    FilterView filterViewTop;
    @Bind(R.id.iv_back_top)
    ImageView ivBackTop;
    @Bind(R.id.iv_history)
    ImageView ivHistory;
    //筛选布局距离顶部的距离
    Header header;
    private int rlFilterBtnsTop = DensityUtil.dip2px(86);
    //商品列表的adapter
    private GoodsAdapter goodsAdapter;
    /**
     * 当前筛选类型 0：配送方式 1：分类 2：品牌 3：全部商品
     * 默认0
     */
    private int curFilterType = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        initView();
        initHeaderView();
    }
    private void initView() {
        filterViewTop.setVisibility(View.GONE);
        filterViewTop.setOnFilterClickListener(this);
        //初始化商品列表
        goodsAdapter = new GoodsAdapter(this);
        LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(goodsAdapter);
        recycleGoods.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) recycleGoods.getAdapter(), gridLayoutManager.getSpanCount()));
        recycleGoods.setLayoutManager(gridLayoutManager);
        //设置间距
        recycleGoods.addItemDecoration(new GridSpacingItemDecoration(2, 5, false, true));

        final View headerView = LayoutInflater.from(this).inflate(R.layout.header_filter_layout, null);
        header = new Header(headerView);
        //添加头部
        adapter.addHeaderView(headerView);
        recycleGoods.setPullRefreshEnabled(false);
        //滑动事件
        recycleGoods.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                View view = layoutManager.findViewByPosition(position);
                if (position == 1) {
                    if (rlFilterBtnsTop + view.getTop() >= 0) {
                        filterViewTop.setVisibility(View.GONE);
                        header.filterView.setTopHeight(rlFilterBtnsTop +
                                view.getTop() + header.filterView.getHeight());

                    } else {
                        filterViewTop.setVisibility(View.VISIBLE);

                    }

                } else {
                    filterViewTop.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    private void initHeaderView() {
        header.filterView.setOnFilterClickListener(this);
        header.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 0) {
                    search();

                    return true;
                }
                return false;
            }
        });

        header.sortView.setOnGeneralClickListener(this);
        header.sortView.setOnSaveMoneyClickListener(this);
        header.sortView.setOnPriceClickListener(this);
        header.flBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterActivity.this.finish();
            }
        });
    }
    @OnClick({R.id.iv_back_top, R.id.iv_history})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_top:
                recycleGoods.smoothScrollToPosition(0);
                break;
            case R.id.iv_history:
                break;
        }
    }
    /**
     * 刷新商品列表
     */
    private void refreshGoods() {

    }
    /**
     * 搜索
     */
    private void search() {

    }

    /**
     * 筛选弹出框的点击事件
     * @param position
     * @param selectedData
     */
    @Override
    public void onFilterClick(int position, List<String> selectedData) {
        filterViewTop.setFiltered(position, selectedData);
        header.filterView.setFiltered(position, selectedData);
    }

    /**
     * 综合排序
     * @param v
     */
    @Override
    public void onGeneralClick(View v) {
        refreshGoods();
    }

    /**
     * 省钱方式排序
     * @param v
     */
    @Override
    public void onSaveMoneyClick(View v) {
        refreshGoods();
    }

    /**
     * 按价钱排名
     *
     * @param v
     * @param priceUp true:从低到高 false:从高到低
     */
    @Override
    public void onPriceClick(View v, boolean priceUp) {
        refreshGoods();
    }

    static class Header {
        @Bind(R.id.fl_back)
        FrameLayout flBack;
        @Bind(R.id.et_search)
        EditText etSearch;
        @Bind(R.id.tv_cancel)
        TextView tvCancel;
        @Bind(R.id.filter_View1)
        SortView sortView;
        @Bind(R.id.filter_View)
        FilterView filterView;

        Header(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
