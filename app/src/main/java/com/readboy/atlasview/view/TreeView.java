package com.readboy.atlasview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.readboy.atlasview.R;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.interfaces.TreeViewItemClick;
import com.readboy.atlasview.interfaces.TreeViewItemLongClick;
import com.readboy.atlasview.model.TreeModel;
import com.readboy.atlasview.utils.log.LogUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class TreeView extends ViewGroup {
    private  Paint mPaint;
    private Context mContext;
    private TreeModel mTreeModel;
    private TreeViewItemClick mTreeViewItemClick;
    private TreeViewItemLongClick mTreeViewItemLongClick;



    public TreeView(Context context) {
        super(context);
    }

    public TreeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setClipChildren(false);
        setClipToPadding(false);
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = getChildCount();
        for (int i = 0; i < size; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        layoutChildren();
        LogUtils.d("curent size == " + size);
        setMeasuredDimension((int)mTreeModel.getCanvasBean().getWidth() + 150,(int)mTreeModel.getCanvasBean().getHeight() + 150);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.layout(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
        }
    }

    /**
     * 绘制VIew本身的内容，通过调用View.onDraw(canvas)函数实现
     * 绘制自己的孩子通过dispatchDraw（canvas）实现
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(mContext.getResources().getColor(R.color.black));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0,0,(int)(mTreeModel.getCanvasBean().getWidth()+100),(int)(mTreeModel.getCanvasBean().getHeight() + 100),mPaint);
    }

    private void layoutChildren(){
        final int size = getChildCount();
        double startX = mTreeModel.getCanvasBean().getStartX();
        double startY  = mTreeModel.getCanvasBean().getStartY();
        for (int i = 0; i < size; i++) {
            NodeView nodeView = (NodeView)getChildAt(i);
            Node node = nodeView.getNode();
            int radius = node.getShape().getRadius();
            int leftMargin = 80;
            //用圆心坐标减去或加上半径再加上偏移量来定位坐标的位置
            nodeView.layout((int)(node.getX() -startX - radius + leftMargin),(int)(node.getY() -startY - radius + leftMargin),(int)(node.getX() + radius -startX + leftMargin),(int)(node.getY() + radius - startY) + leftMargin);
        }

    }


    public void setTreeModel(TreeModel treeModel) {
        mTreeModel = treeModel;
        clearAllNoteViews();
        addNoteViews();
    }
    /**
     * 清除所有的NoteView
     */
    private void clearAllNoteViews() {
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View childView = getChildAt(i);
                if (childView instanceof NodeView) {
                    removeView(childView);
                }
            }
        }
    }

    /**
     * 添加所有的NoteView
     */
    private void addNoteViews() {
        if (mTreeModel != null) {
            List<Node> nodes = mTreeModel.getAtlasBean().getData().getMapping().getNodes();
            for (Node model : nodes) {
                addNodeViewToGroup(model);
            }
        }
    }

    private View addNodeViewToGroup(Node poll) {
        final NodeView nodeView = new NodeView(mContext,null);
        nodeView.setFocusable(true);
        nodeView.setClickable(true);
        nodeView.setSelected(false);
        nodeView.setNode(poll);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        nodeView.setLayoutParams(lp);
        nodeView.getTvOrder().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                performTreeItemClick(view);
            }
        });
        nodeView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                preformTreeItemLongClick(v);
                return true;
            }
        });

        this.addView(nodeView);
        return nodeView;
    }

    private void preformTreeItemLongClick(View v) {
        if (mTreeViewItemLongClick != null) {
            mTreeViewItemLongClick.onLongClick(v);
        }
    }

    private void performTreeItemClick(View view) {
        if (mTreeViewItemClick != null) {
            mTreeViewItemClick.onItemClick(view);
        }
    }

    public void setMTreeViewItemClick(TreeViewItemClick mTreeViewItemClick) {
        this.mTreeViewItemClick = mTreeViewItemClick;
    }



    public void setMTreeViewItemLongClick(TreeViewItemLongClick mTreeViewItemLongClick) {
        this.mTreeViewItemLongClick = mTreeViewItemLongClick;
    }


}
