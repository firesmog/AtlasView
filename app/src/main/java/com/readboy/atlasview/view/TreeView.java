package com.readboy.atlasview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.readboy.atlasview.R;
import com.readboy.atlasview.bean.Link;
import com.readboy.atlasview.bean.Node;
import com.readboy.atlasview.constants.Constants;
import com.readboy.atlasview.interfaces.TreeViewItemClick;
import com.readboy.atlasview.interfaces.TreeViewItemLongClick;
import com.readboy.atlasview.model.TreeModel;
import com.readboy.atlasview.utils.DensityUtils;
import com.readboy.atlasview.utils.ViewUtil;
import com.readboy.atlasview.utils.log.LogUtils;

import java.util.LinkedHashMap;
import java.util.List;

public class TreeView extends ViewGroup implements ScaleGestureDetector.OnScaleGestureListener {
    private  Paint mPaint;
    private Context mContext;
    private TreeModel mTreeModel;
    private TreeViewItemClick mTreeViewItemClick;
    private TreeViewItemLongClick mTreeViewItemLongClick;
    private final int firstNodeMargin = 120;
    private MoveAndScaleHandler mMoveAndScaleHandler;
    private GestureDetector mGestureDetector;



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
        mMoveAndScaleHandler = new MoveAndScaleHandler(context, this);
        mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = getChildCount();
        for (int i = 0; i < size; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        layoutChildren();
        setMeasuredDimension((int)mTreeModel.getCanvasBean().getWidth() + 200,(int)mTreeModel.getCanvasBean().getHeight() + 200);

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
        drawLine(canvas);
        //drawRect(canvas);
        //canvas.drawRect(0,0,(int)(mTreeModel.getCanvasBean().getWidth()+100),(int)(mTreeModel.getCanvasBean().getHeight() + 100),mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return mMoveAndScaleHandler.onTouchEvent(event);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    private void  drawRect(Canvas canvas){
        List<Node> nodes =  mTreeModel.getAtlasBean().getData().getMapping().getNodes();
        LinkedHashMap<Long, Node> models = mTreeModel.getModels();
        double startX = mTreeModel.getCanvasBean().getStartX();
        double startY  = mTreeModel.getCanvasBean().getStartY();
        for (Node node : nodes) {
            mPaint.setColor(mContext.getResources().getColor(R.color.black));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(2);
            int radius = node.getShape().getRadius();
            canvas.drawRect((int)(node.getX() -startX - radius + firstNodeMargin),(int)(node.getY() -startY - radius + firstNodeMargin),(int)(node.getX() + radius -startX + firstNodeMargin),(int)(node.getY() + radius - startY) + firstNodeMargin,mPaint);

        }
    }

    private void drawLine(Canvas canvas) {
        if (mTreeModel != null) {
            List<Link> links = mTreeModel.getAtlasBean().getData().getMapping().getLinks();
            LinkedHashMap<Long, Node> models = mTreeModel.getModels();
            double startX = mTreeModel.getCanvasBean().getStartX();
            double startY = mTreeModel.getCanvasBean().getStartY();

            for (Link link : links) {
                mPaint.setColor(mContext.getResources().getColor(R.color.color_9faaff));
                Node source = models.get(link.getSourceid());
                Node target = models.get(link.getTargetid());
                if (target == null || source == null) {
                    continue;
                }
                //todo Lzy 不可见则不画线
            /*if(!target.isVisibility() ){
                return;
            }*/
                int sRadius = source.getShape().getRadius();
                int tRadius = target.getShape().getRadius();

                if(sRadius <= 0){
                    sRadius = source.getShape().getWidth()/2;
                }

                if(tRadius <= 0){
                    tRadius = target.getShape().getWidth()/2;
                }

                double sx = source.getX();
                double sy = source.getY();
                double tx = target.getX();
                double ty = target.getY();
                double distance = Math.abs(Math.sqrt(Math.pow((tx-sx),2) + Math.pow(ty - sy,2)));
                int sxLine = 0;
                int syLine = 0;
                int txLine = 0;
                int tyLine = 0;
                boolean b = Math.abs(ty - sy) >= Math.abs(tx - sx);
                //计算两点角度
                double degrees = DensityUtils.calculateAngle(sx,sy,tx,ty);
                boolean filter = ((degrees > 65 && degrees < 115) || (degrees < 25 && degrees > -25) || (degrees < -65 && degrees >= -90) || degrees > 245 || ((degrees > 155) && degrees < 205));
                LogUtils.d("drawLine drawLine drawLine == " + degrees  + " , target === " + target.getName() + ",filter = "  + filter );
                LogUtils.d("drawLine drawLine drawLine == test 90 = " + Math.tan(-53.680) + " ,150 = " + Math.tan(150) + ",fu 60 = " + Math.tan(-60)   );

                if(source.getShape().getType().equals(Constants.SHAPE_RECTANGLE)  && !filter){
                    if((degrees <= 135 && degrees >= 45)){
                        sxLine = (int) (sx + sRadius*(tx-sx)/(sy-ty));
                        syLine = (int) (sy - tRadius);

                    }else if((degrees >= 225 || degrees <= -45)) {
                        sxLine = (int) (sx + sRadius*(tx-sx)/(ty-sy));
                        syLine = (int) (sy + sRadius);
                    }else if(degrees > -45 && degrees < 45){
                        sxLine = (int) (sx + sRadius);
                        syLine = (int) (sy - sRadius*(sy-ty)/(tx-sx));
                    }else {
                        sxLine = (int) (sx - sRadius);
                        syLine = (int) (sy  + sRadius*(ty-sy)/(sx-tx));
                    }

                }else {
                    sxLine = (int)((sRadius/distance)*(tx-sx) + sx);
                    syLine = (int)(sy - (sRadius/distance)*(sy -ty));

                }

                if(target.getShape().getType().equals(Constants.SHAPE_RECTANGLE) && !filter ){
                    if((degrees <= 135 && degrees >= 45)){
                        txLine = (int) (tx - tRadius*(tx-sx)/(sy-ty));
                        tyLine = (int) (ty+tRadius);

                    }else if((degrees >= 225 || degrees <= -45)) {
                        txLine = (int) (tx + tRadius*(sx-tx)/(ty-sy));
                        tyLine = (int) (ty - tRadius);
                    }else if(degrees > -45 && degrees < 45){
                        txLine = (int) (tx - tRadius);
                        tyLine = (int) (ty + tRadius*(sy-ty)/(tx-sx));
                    }else {
                        txLine = (int) (tx + tRadius);
                        tyLine = (int) (ty  - tRadius*(ty-sy)/(sx-tx));
                    }

                }else {
                    txLine = (int)(tx - (tRadius/distance)*(tx- sx) );
                    tyLine = (int)(ty + ( tRadius/distance)*(sy - ty) );

                }


                int fromX = (int) (sxLine - startX + firstNodeMargin);
                int fromY = (int) (syLine - startY + firstNodeMargin);
                int toX = (int) (txLine - startX + firstNodeMargin);
                int toY = (int) (tyLine - startY + firstNodeMargin);
                //canvas.drawLine((int)( source.getX() - startX + leftMargin), (int) (source.getY() - startY + topMargin),(int) (target.getX() - startX + leftMargin), (int) (target.getY() - startY + topMargin), mPaint);
                canvas.drawLine(fromX, fromY, toX, toY, mPaint);
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                ViewUtil.drawTrangle(canvas, mPaint, fromX, fromY, toX, toY, 10, 3);
            }
            mPaint.setStyle(Paint.Style.STROKE);

        }
    }

    private void layoutChildren(){
        final int size = getChildCount();
        double startX = mTreeModel.getCanvasBean().getStartX();
        double startY  = mTreeModel.getCanvasBean().getStartY();
        for (int i = 0; i < size; i++) {
            NodeView nodeView = (NodeView)getChildAt(i);
            Node node = nodeView.getNode();
            TextView tvName = nodeView.getTvName();
            int radius = node.getShape().getRadius();
            //用圆心坐标减去或加上半径再加上偏移量来定位坐标的位置
            if (radius <= 0) {
                radius = node.getShape().getWidth()/2;
            }
            double gap =  (tvName.getWidth() - 2*radius);
            LogUtils.d("nodeView.getChildAt(0) == " + gap);


            if(gap > 0){
                nodeView.layout((int)(node.getX() -startX - radius - gap/2 + firstNodeMargin),(int)(node.getY() -startY - radius - tvName.getHeight()+ firstNodeMargin),(int)(node.getX() + radius -startX + firstNodeMargin),(int)(node.getY() + radius - startY + gap/2 + firstNodeMargin));
            }else {
                nodeView.layout((int)(node.getX() -startX - radius  + firstNodeMargin),(int)(node.getY() -startY - radius - tvName.getHeight()+ firstNodeMargin),(int)(node.getX() + radius -startX + firstNodeMargin),(int)(node.getY() + radius - startY  + firstNodeMargin));

            }
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

        //todo Lzy 设置是否可见
       /* if(!poll.isVisibility()){
            nodeView.setVisibility(GONE);
        }*/

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


    /**
     * 模型查找NodeView
     *
     * @param nodeModel
     * @return
     */
    public View findNodeViewFromNode(Node nodeModel) {
        View view = null;
        int size = getChildCount();
        for (int i = 0; i < size; i++) {
            View childView = getChildAt(i);
            if (childView instanceof NodeView) {
                Node treeNode = ((NodeView) childView).getNode();
                if (treeNode == nodeModel) {
                    view = childView;
                    continue;
                }
            }
        }
        return view;
    }

}
