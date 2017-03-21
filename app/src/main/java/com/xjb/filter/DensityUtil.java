package com.xjb.filter;



public class DensityUtil {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        return FApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight() {
        return FApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取换算比例
     */
    public static float getDensity() {
        return FApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = FApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip( float pxValue) {
        final float scale = FApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = FApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = FApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
