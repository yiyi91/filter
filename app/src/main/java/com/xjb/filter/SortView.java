package com.xjb.filter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xujingbo on 2016/11/16.
 */

public class SortView extends RelativeLayout {
    @Bind(R.id.tv_general)
    TextView tvGeneral;
    @Bind(R.id.tv_save_money)
    TextView tvSaveMoney;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.view_price_up)
    TextView viewPriceUp;
    @Bind(R.id.view_price_down)
    TextView viewPriceDown;
    private Context context;
    private TextView tvFilter[];
    private int selectedPos = 0;
    private boolean priceUp = true;
    private onGeneralClickListener onGeneralClickListener;
    private onSaveMoneyClickListener onSaveMoneyClickListener;
    private onPriceClickListener onPriceClickListener;

    public SortView(Context context) {
        this(context, null);
    }

    public SortView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sort_view, this);
        ButterKnife.bind(this, view);
        tvFilter = new TextView[3];
        tvFilter[0] = tvGeneral;
        tvFilter[1] = tvSaveMoney;
        tvFilter[2] = tvPrice;
        refreshView();
    }

    @OnClick({R.id.tv_general, R.id.tv_save_money, R.id.ll_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_general:
                onGeneralClick();
                break;
            case R.id.tv_save_money:

                onSaveMoneyClick();
                break;
            case R.id.ll_price:

                onPriceClick();
                break;
        }
    }
    private void onGeneralClick(){
        selectedPos = 0;
        if (onGeneralClickListener != null){
            onGeneralClickListener.onGeneralClick(tvGeneral);
        }
        viewPriceUp.setSelected(false);
        viewPriceDown.setSelected(false);
        refreshView();
    }
    private void onSaveMoneyClick(){
        selectedPos = 1;
        if (onSaveMoneyClickListener != null){
            onSaveMoneyClickListener.onSaveMoneyClick(tvSaveMoney);
        }
        viewPriceUp.setSelected(false);
        viewPriceDown.setSelected(false);
        refreshView();
    }
    private void onPriceClick(){
        selectedPos = 2;
        if (onPriceClickListener != null){
            onPriceClickListener.onPriceClick(tvPrice,priceUp);
        }
        viewPriceUp.setSelected(priceUp);
        viewPriceDown.setSelected(!priceUp);
        priceUp = !priceUp;
        refreshView();
    }
    public void refreshView(){
        for (int i = 0; i < 3; i++){
            if (i == selectedPos){
                tvFilter[i].setSelected(true);

            }else {
                tvFilter[i].setSelected(false);

            }

        }
    }

    public void setOnGeneralClickListener(onGeneralClickListener onGeneralClickListener) {
        this.onGeneralClickListener = onGeneralClickListener;
    }

    public void setOnSaveMoneyClickListener(onSaveMoneyClickListener onSaveMoneyClickListener) {
        this.onSaveMoneyClickListener = onSaveMoneyClickListener;
    }

    public void setOnPriceClickListener(onPriceClickListener onPriceClickListener) {
        this.onPriceClickListener = onPriceClickListener;
    }

    public interface onGeneralClickListener{
        void onGeneralClick(View v);
    }
    public interface onSaveMoneyClickListener{
        void onSaveMoneyClick(View v);
    }
    public interface onPriceClickListener{
        void onPriceClick(View v,boolean priceUp);
    }
}
