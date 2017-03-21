package com.xjb.filter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author xujingbo
 * @date 2017/3/8 10:51
 */
public class GoodsAdapter extends ListBaseAdapter {


    private Context context;

    public GoodsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_goods, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        ViewHolder holder = (ViewHolder)viewHolder;
        holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (position % 2 == 1) {
            holder.tvSoldOut.setVisibility(View.VISIBLE);
        } else {
            holder.tvSoldOut.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_goods_icon)
        ImageView ivGoodsIcon;
        @Bind(R.id.tv_sold_out)
        TextView tvSoldOut;
        @Bind(R.id.tv_goods_name)
        TextView tvGoodsName;
        @Bind(R.id.tv_cur_price)
        TextView tvCurPrice;
        @Bind(R.id.tv_decrease_price)
        TextView tvDecreasePrice;
        @Bind(R.id.tv_old_price)
        TextView tvOldPrice;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
