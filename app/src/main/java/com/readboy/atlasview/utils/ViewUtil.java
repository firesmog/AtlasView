package com.readboy.atlasview.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;


import com.readboy.atlasview.R;
import com.readboy.atlasview.view.NodeView;

import static android.view.View.GONE;
import static com.readboy.atlasview.utils.DensityUtils.dp2px;

public class ViewUtil {
    /**
     * 绘制三角
     * @param canvas
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param height
     * @param bottom
     */
    public   static  void drawTrangle(Canvas canvas, Paint paintLine, float fromX, float fromY, float toX, float toY, int height, int bottom){
        try{
            float juli = (float) Math.sqrt((toX - fromX) * (toX - fromX)
                    + (toY - fromY) * (toY - fromY));// 获取线段距离
            float juliX = toX - fromX;// 有正负，不要取绝对值
            float juliY = toY - fromY;// 有正负，不要取绝对值
            float dianX = toX - (height / juli * juliX);
            float dianY = toY - (height / juli * juliY);
            float dian2X = fromX + (height / juli * juliX);
            float dian2Y = fromY + (height / juli * juliY);
            //终点的箭头
            Path path = new Path();
            path.moveTo(toX, toY);// 此点为三边形的起点
            path.lineTo(dianX + (bottom / juli * juliY), dianY
                    - (bottom / juli * juliX));
            path.lineTo(dianX - (bottom / juli * juliY), dianY
                    + (bottom / juli * juliX));
            path.close(); // 使这些点构成封闭的三边形
            canvas.drawPath(path, paintLine);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 绘制两个View直接的连线
     *
     * @param canvas
     * @param from
     * @param to
     */
    public static  void drawLineToView(Canvas canvas, View from, View to, Paint mPaint, Context mContext) {
        if (to.getVisibility() == GONE) {
            return;
        }

        mPaint.setStyle(Paint.Style.STROKE);
        float width = 2f;
        mPaint.setStrokeWidth(dp2px(mContext, width));
        mPaint.setColor(mContext.getResources().getColor(R.color.black));

        NodeView fromView = (NodeView)from;
        NodeView toView = (NodeView)to;
       // LogUtils.d("fromView = " + from.getMeasuredHeight() );
        int top = fromView.getTop();
        int formY = top + fromView.getMeasuredHeight() / 2 + fromView.getTvName().getHeight()/2;
        int formX = from.getRight();

        int top1 = toView.getTop();
        int toY = top1 + toView.getMeasuredHeight() / 2 + toView.getTvName().getHeight()/2;
        int toX = to.getLeft();
        mPaint.setAlpha(70);
        canvas.drawLine(formX, formY,toX, toY, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        drawTrangle(canvas, mPaint, formX, formY, toX, toY, 10, 3);
    }
}
