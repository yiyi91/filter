package com.xjb.filter;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xujingbo on 2016/11/9.
 * Grid间距
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private boolean hasHeader = false;
    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge,boolean hasHeader) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.hasHeader = hasHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if (hasHeader) {
            if (position < spanCount) {
                outRect.set(0, 0, 0, 0);
                return;
            }
        }
        int column = position % spanCount; // item column
        if (includeEdge) {

            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
            if (hasHeader){
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            }

        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
