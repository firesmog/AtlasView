package com.readboy.atlasview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.readboy.atlasview.R;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.constants.Constants;
import com.readboy.atlasview.utils.AtlasUtil;
import com.readboy.atlasview.utils.DensityUtils;
import com.readboy.atlasview.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.GradientDrawable.OVAL;
import static android.graphics.drawable.GradientDrawable.RECTANGLE;

public class NodeView extends RelativeLayout {
    private String nodeBackground;
    private int nameColor;
    private int numberColor;
    private String name;
    private String nodeShape;
    private int order;
    private float nodeRadius;
    private float nameSize;
    private float orderSize;
    private float marginSize;
    private float tvOrderWidth;
    private TextView tvName;
    private TextView tvOrder;
    private long nodeId;

    private Context context;
    private Node node;
    private int[] colors;
    private AlphaAnimation animation;
    private TextView tvOrderCircle;
    private FrameLayout flCircle;
    private TextView tv1;
    private TextView tv2;
    private int circleGap = 14;

    public NodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NodeView, 0, 0);
        try {
            order = array.getInteger(R.styleable.NodeView_order, 1);
            name = array.getString(R.styleable.NodeView_name);
            nodeShape = array.getString(R.styleable.NodeView_nodeShape);
            nodeBackground = array.getString(R.styleable.NodeView_nodeBackground);
            nameColor = array.getColor(R.styleable.NodeView_nameColor, Color.BLACK);
            numberColor = array.getColor(R.styleable.NodeView_orderColor, Color.WHITE);
            nodeRadius = array.getDimension(R.styleable.NodeView_nodeRadius, 14);
            nameSize = array.getDimension(R.styleable.NodeView_nameSize, 14);
            marginSize = array.getDimension(R.styleable.NodeView_marginSize, -1);
            orderSize = array.getDimension(R.styleable.NodeView_orderSize, 14);
            tvOrderWidth = array.getDimension(R.styleable.NodeView_tvOrderWidth, 80);
        } catch (Exception e) {
            LogUtils.d("NodeView create error is " + e.getMessage());
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
        initView(context);
    }

    @SuppressLint("WrongConstant")
    private void initTvOrder(TextView textView,boolean reduce) {

        //创建Drawable对象
        if (null != node) {
            String color = node.getShape().getColor();
            String[] curColors = color.split(",");
            if (curColors.length >= 2) {
                colors = new int[]{Color.parseColor(curColors[0]), Color.parseColor(curColors[1])};
            } else {
                colors = new int[]{Color.parseColor(curColors[0]), Color.parseColor(curColors[0])};
            }

        }
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);

        if (Constants.SHAPE_RECTANGLE.equals(nodeShape)) {
            drawable.setShape(RECTANGLE);
            drawable.setCornerRadius(10f);
        } else {
            drawable.setShape(OVAL);
        }

       if(reduce){
           if (node.getShape().getRadius() > 0) {
               textView.setWidth(node.getShape().getRadius() * 2 - circleGap);
               textView.setHeight(node.getShape().getRadius() * 2 - circleGap);
           } else {
               textView.setWidth(node.getShape().getWidth() - circleGap);
               textView.setHeight(node.getShape().getWidth() - circleGap);
           }
       }else {
           if (node.getShape().getRadius() > 0) {
               textView.setWidth(node.getShape().getRadius() * 2);
               textView.setHeight(node.getShape().getRadius() * 2);
           } else {
               textView.setWidth(node.getShape().getWidth());
               textView.setHeight(node.getShape().getWidth());
           }
       }

        textView.setBackground(drawable);

        if (node.getFloor() != 0) {
            textView.setText(String.valueOf(node.getFloor()));
            textView.setTextColor(numberColor);
            textView.setTextSize(orderSize);
        }
        if (marginSize != -1) {
            LayoutParams lp = (LayoutParams) textView.getLayoutParams();
            lp.topMargin = (int) marginSize;
            textView.setLayoutParams(lp);
        }
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
        name = node.getName();
        order = node.getOrder();
        nodeBackground = node.getShape().getColor();
        nodeShape = node.getShape().getType();
        nodeId = node.getId();
        initTvName();
        initTvOrder(tvOrder,false);
    }

    public NodeView(Context context) {
        super(context);
        this.context = context;
    }


    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.node_view_layout, this);
        tvName = (TextView) inflate.findViewById(R.id.tv_name);
        tvOrder = (TextView) inflate.findViewById(R.id.tv_order);
        tvOrderCircle = (TextView) inflate.findViewById(R.id.tv_circle);
        flCircle = (FrameLayout) inflate.findViewById(R.id.fl_circle);
        tv1 = (TextView)findViewById(R.id.tv_wave_1);
        tv2 = (TextView)findViewById(R.id.tv_wave_2);
    }


    private void initTvName() {
        if (!TextUtils.isEmpty(name)) {
            tvName.setText(AtlasUtil.replace(name));
        }

        tvName.setTextSize(DensityUtils.px2sp(context, node.getFont().getSize()));
        tvName.setTextColor(nameColor);
    }

    public void showSpreadView(){
        tvOrderCircle.setVisibility(GONE);
        setFlCircleWidthHeight();
        tvOrder.setVisibility(GONE);
        tv1.setVisibility(VISIBLE);
        tv2.setVisibility(VISIBLE);
        initTvOrder(tv1,true);
        initTvOrder(tv2,true);
        setAnim1();
        setAnim2();
    }

    private void setFlCircleWidthHeight(){
        RelativeLayout.LayoutParams lp = (LayoutParams) flCircle.getLayoutParams();

        if (node.getShape().getRadius() > 0) {
            lp.width= (int) ((node.getShape().getRadius() * 2 - circleGap) *2f);
            lp.height =  (int) ((node.getShape().getRadius() * 2 - circleGap) *2f);

        } else {
            lp.width=  (int) ((node.getShape().getWidth() - circleGap) *2f);
            lp.height = (int) ((node.getShape().getWidth() - circleGap) *2f);
        }
        lp.topMargin = -2*circleGap;
        lp.leftMargin = -3*circleGap;

        flCircle.setLayoutParams(lp);

    }

    private void setImageViewWidthHeight(ImageView view){
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (node.getShape().getRadius() > 0) {
            lp.width= node.getShape().getRadius() * 2 - circleGap;
            lp.height = node.getShape().getRadius() * 2 - circleGap;

        } else {
            lp.width= node.getShape().getWidth() - circleGap;
            lp.height = node.getShape().getWidth() - circleGap;
        }
        view.setLayoutParams(lp);
    }


    private void setAnim1() {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从原始放大到1.4倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.5f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        tv1.startAnimation(as);
    }

    private void setAnim2() {
        AnimationSet as = new AnimationSet(true);
        //缩放动画，以中心从1.4倍放大到1.8倍
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1.6f, 1.3f, 1.6f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 0.1f);
        scaleAnimation.setDuration(800);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        as.setDuration(800);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);
        tv2.startAnimation(as);
    }


    public void clearAnimation() {
        if(null != flCircle && flCircle.getVisibility() == VISIBLE){
            tv1.clearAnimation();
            tv2.clearAnimation();
        }
    }

    public void showRecommendNode() {
        if (Constants.SHAPE_RECTANGLE.equals(nodeShape)) {
            showRect();
        } else {
            showCircle();
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void showCircle() {
        tvOrderCircle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_tv_circle));
        int addRadius = 10;
        if (node.getShape().getRadius() > 0) {
            tvOrderCircle.setWidth(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(), addRadius * 2));
            tvOrderCircle.setHeight(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(), addRadius * 2));
        } else {

            tvOrderCircle.setWidth(node.getShape().getWidth() + DensityUtils.dp2px(getContext(), addRadius * 2));
            tvOrderCircle.setHeight(node.getShape().getWidth() + DensityUtils.dp2px(getContext(), addRadius * 2));
        }
        LayoutParams lp = (LayoutParams) flCircle.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) tvOrderCircle.getLayoutParams();
        int top = DensityUtils.px2dp(getContext(), lp.topMargin) - addRadius;
        top = DensityUtils.dp2px(getContext(), top);
        lp2.topMargin = top;
        tvOrderCircle.setLayoutParams(lp2);
        tvOrderCircle.setVisibility(VISIBLE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showRect() {
        tvOrderCircle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_tv_rect));
        int addRadius = 10;
        if (node.getShape().getRadius() > 0) {
            tvOrderCircle.setWidth(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(), addRadius * 2));
            tvOrderCircle.setHeight(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(), addRadius * 2));
        } else {

            tvOrderCircle.setWidth(node.getShape().getWidth() + DensityUtils.dp2px(getContext(), addRadius * 2));
            tvOrderCircle.setHeight(node.getShape().getWidth() + DensityUtils.dp2px(getContext(), addRadius * 2));
        }
        LayoutParams lp = (LayoutParams) tvOrder.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) tvOrderCircle.getLayoutParams();
        int top = DensityUtils.px2dp(getContext(), lp.topMargin) - addRadius;
        top = DensityUtils.dp2px(getContext(), top);
        lp2.topMargin = top;
        tvOrderCircle.setLayoutParams(lp2);
        tvOrderCircle.setVisibility(VISIBLE);
    }


    public TextView getTvNameTextView() {
        return tvName;
    }

    public TextView getTvOrderTextView() {
        return tvOrder;
    }

    public String getNodeBackground() {
        return nodeBackground;
    }

    public void setNodeBackground(String nodeBackground) {
        this.nodeBackground = nodeBackground;
    }

    public int getNameColor() {
        return nameColor;
    }

    public void setNameColor(int nameColor) {
        this.nameColor = nameColor;
    }

    public int getNumberColor() {
        return numberColor;
    }

    public void setNumberColor(int numberColor) {
        this.numberColor = numberColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeShape() {
        return nodeShape;
    }

    public void setNodeShape(String nodeShape) {
        this.nodeShape = nodeShape;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public float getNodeRadius() {
        return nodeRadius;
    }

    public void setNodeRadius(float nodeRadius) {
        this.nodeRadius = nodeRadius;
    }

    public float getNameSize() {
        return nameSize;
    }

    public void setNameSize(float nameSize) {
        this.nameSize = nameSize;
    }

    public float getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(float orderSize) {
        this.orderSize = orderSize;
    }

    public float getMarginSize() {
        return marginSize;
    }

    public void setMarginSize(float marginSize) {
        this.marginSize = marginSize;
    }

    public float getTvOrderWidth() {
        return tvOrderWidth;
    }

    public void setTvOrderWidth(float tvOrderWidth) {
        this.tvOrderWidth = tvOrderWidth;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvOrder() {
        return tvOrder;
    }

    public void setTvOrder(TextView tvOrder) {
        this.tvOrder = tvOrder;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }
}
