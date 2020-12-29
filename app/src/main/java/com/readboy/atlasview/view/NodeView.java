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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
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
            nameColor = array.getColor(R.styleable.NodeView_nameColor, Color.BLUE);
            numberColor = array.getColor(R.styleable.NodeView_orderColor, Color.BLUE);
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
    private void initTvOrder() {
        if(order != 0){
            tvOrder.setText(String.valueOf(order));
            tvOrder.setTextColor(numberColor);
            tvOrder.setTextSize(orderSize);
        }

//        colors = new int[]{context.getResources().getColor(R.color.color_31cfff), context.getResources().getColor(R.color.color_0097e6)};

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
        } else {
            drawable.setShape(OVAL);
        }

        if (node.getShape().getRadius() > 0) {
            tvOrder.setWidth(node.getShape().getRadius() * 2);
            tvOrder.setHeight(node.getShape().getRadius() * 2);
        } else {

            tvOrder.setWidth(node.getShape().getWidth());
            tvOrder.setHeight(node.getShape().getWidth());
        }

        tvOrder.setBackground(drawable);

        if (marginSize != -1) {
            LayoutParams lp = (LayoutParams) tvOrder.getLayoutParams();
            lp.topMargin = (int) marginSize;
            tvOrder.setLayoutParams(lp);
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
        initTvOrder();
    }

    public NodeView(Context context) {
        super(context);
        this.context = context;
    }


    private void initView(Context context){
        View inflate = LayoutInflater.from(context).inflate(R.layout.node_view_layout, this);
        tvName = (TextView) inflate.findViewById(R.id.tv_name);
        tvOrder = (TextView) inflate.findViewById(R.id.tv_order);
        tvOrderCircle = (TextView) inflate.findViewById(R.id.tv_circle);
    }


    private void initTvName(){
        if(!TextUtils.isEmpty(name)){
            tvName.setText(AtlasUtil.replace(name));
        }

        tvName.setTextSize(DensityUtils.px2sp(context,node.getFont().getSize()));
        tvName.setTextColor(nameColor);
    }

    private String nameFormat(String str) {
        int length = str.length();
        List<Integer> index = new ArrayList<>();
        for (int i = length; i > 0; i--) {
            if (i % 5 == 0) {
                index.add(i);
            }
        }
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i : index) {
            stringBuilder.insert(i, "\n");
        }
        return stringBuilder.toString();
    }

    public void startAnimationOut() {
        animation = new AlphaAnimation(0.4f, 1.0f);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1500);
        tvOrderCircle.startAnimation(animation);
    }

    public void clearAnimation(){
        if(null != animation){
            this.getTvOrder().clearAnimation();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showCircle(){
        tvOrderCircle.setBackground(getContext().getResources().getDrawable(R.drawable.shape_tv_circle));
        int addRadius = 4;
        if (node.getShape().getRadius() > 0) {
            tvOrderCircle.setWidth(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(),addRadius*2));
            tvOrderCircle.setHeight(node.getShape().getRadius() * 2 + DensityUtils.dp2px(getContext(),addRadius*2));
        } else {

            tvOrderCircle.setWidth(node.getShape().getWidth() + DensityUtils.dp2px(getContext(),addRadius*2));
            tvOrderCircle.setHeight(node.getShape().getWidth() + DensityUtils.dp2px(getContext(),addRadius*2));
        }
        LayoutParams lp = (LayoutParams) tvOrder.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) tvOrderCircle.getLayoutParams();
        int top = DensityUtils.px2dp(getContext(),lp.topMargin) - addRadius;
        top = DensityUtils.dp2px(getContext(),top);
        lp2.topMargin = top;
        tvOrderCircle.setLayoutParams(lp2);
        tvOrderCircle.setVisibility(VISIBLE);
        startAnimationOut();
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
