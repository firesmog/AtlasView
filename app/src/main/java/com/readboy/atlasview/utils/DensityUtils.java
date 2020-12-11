package com.readboy.atlasview.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 单位转换 工具类<br>
 */
public class DensityUtils {

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dpVal) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
                .getDisplayMetrics());
        return result;
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
                .getDisplayMetrics());
        return result;
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int result = (int) (pxVal / scale);
        return result;
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        int result = (int) (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
        return result;
    }

    public static double calculateAngle(double x1, double y1, double x2, double y2) {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil( -angle / 360 ) * 360;

        return angle - 90;
    }

}
