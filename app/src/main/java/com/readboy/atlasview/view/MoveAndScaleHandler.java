package com.readboy.atlasview.view;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;


/**
 * Created by owant on 09/02/2017.
 */

public class MoveAndScaleHandler implements ScaleGestureDetector.OnScaleGestureListener {

    static final float max_scale = 1.4f;
    static final float min_scale = 0.65f;

    /**
     * 作用于的View
     */
    private View mView;

    private int lastX = 0;
    private int lastY = 0;

    private int mode = 0;

    private ScaleGestureDetector mScaleGestureDetector;
    private Context mContext;
    private long beforeTime;
    private float oldDist;
    private float ratio;
    private float beforeScale;

    public MoveAndScaleHandler(Context context, View view) {
        this.mView = view;
        this.mContext = context;
        mScaleGestureDetector = new ScaleGestureDetector(mContext, this);
    }

    public boolean onTouchEvent(MotionEvent event) {

        int currentX = (int) event.getRawX();//获得手指当前的坐标,相对于屏幕
        int currentY = (int) event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
                // 0
            case MotionEvent.ACTION_DOWN:
                mode = 1;
                break;
                //1
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
                //6
            case MotionEvent.ACTION_POINTER_UP:
                //将模式进行为负数这样，多指下，抬起不会触发移动
                mode = -2;
                break;
                //5
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);//两点按下时的距离
                mode += 1;
                break;
                //2
            case MotionEvent.ACTION_MOVE:
                if (mode >= 2) {
                    float newDist = spacing(event);
                    ratio  = newDist/oldDist;
                    Log.d("LZY","current new = " + newDist + ", old = " + oldDist + ",ratio = " + ratio);
                    if ( Math.abs(newDist - oldDist) > 50) {
                        mScaleGestureDetector.onTouchEvent(event);
                    }

                } else if (mode == 1) {
                    int deltaX = currentX - lastX;
                    int deltaY = currentY - lastY;

                    int translationX = (int) ViewHelper.getTranslationX(mView) + deltaX;
                    int translationY = (int) ViewHelper.getTranslationY(mView) + deltaY;

                    ViewHelper.setTranslationX(mView, translationX);
                    ViewHelper.setTranslationY(mView, translationY);
                }
                break;
        }

        lastX = currentX;
        lastY = currentY;

        return true;

    }

    /**
     * 两点之间的距离
     *
     * @param event
     * @return
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    //todo Lzy目前双击放大缩小还未触发这个方法
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();

        if (scaleFactor >= max_scale) {
            scaleFactor = max_scale;
        }
        if (scaleFactor <= min_scale) {
            scaleFactor = min_scale;
        }

        if(System.currentTimeMillis() - beforeTime > 150){

            Log.d("LZY","current onScale == " +         ratio + ", scaleFactor =    " + scaleFactor + " , beforeScale = " + beforeScale);
            if((ratio > 1 && scaleFactor > beforeScale) || ratio < 1 && (scaleFactor < beforeScale || beforeScale == 0)){
                Log.d("LZY","current onScale == " +         scaleFactor);
                beforeTime = System.currentTimeMillis();
                ViewHelper.setScaleX(mView, scaleFactor);
                ViewHelper.setScaleY(mView, scaleFactor);
                beforeScale = scaleFactor;
            }
        }
        return false;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }


}
