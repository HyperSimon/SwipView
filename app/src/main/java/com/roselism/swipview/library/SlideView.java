package com.roselism.swipview.library;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by simon on 16-6-8.
 */
public class SlideView extends RelativeLayout {

    private static final String TAG = "SwipView";

    private ViewDragHelper mViewDragHelper;
    private View mSurfaceChild;  // 表面控件
    private View mBottomChild;   // 底部控件

    /**
     * 滑动方式的回调
     */
    private ViewDragHelper.Callback mSlideCallBack = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i(TAG, "onViewReleased: ");

            float showLength = getWidth() - mSurfaceChild.getRight();
            float showPercent = showLength / mBottomChild.getWidth();
            if (showPercent > 0.60f) {
                open();
            } else {
                closeBottom();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mSurfaceChild) { // 表面控件

                int newLeft = left; // 新的左边界
                int newTop = 0; // 新的上边界
                int newRight = left + mSurfaceChild.getWidth(); // 新的右边界
                int newBottom = mSurfaceChild.getHeight(); // 新的下边界
                mSurfaceChild.layout(newLeft, newTop, newRight, newBottom);

                int bottomLeft = mSurfaceChild.getWidth() + left;
                int bottomRight = bottomLeft + mBottomChild.getWidth();
                int bottomTop = 0;
                int bottomBottom = mSurfaceChild.getHeight();
                mBottomChild.layout(bottomLeft, bottomTop, bottomRight, bottomBottom);
            }
        }

        /**
         * 限制被拖动的子布局在水平方向上的运动。
         * 默认实现不允许水平运动；
         * 集成类必须重写此方法并提供对应需求。
         *
         * 仅仅起限制作用，用于返回子控件需要移动的距离
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return determinBorder(left);
        }

        /**
         * 判断移动的边界
         * @param left
         * @return
         */
        private int determinBorder(int left) {
            int leftBorder = 0;
            int rightBorder = 0;

            /**
             * 当前边界就是左边到底部控件的距离
             * 右边到屏幕的最右
             */
            leftBorder = -mBottomChild.getWidth();
            rightBorder = 0;

            if (left <= leftBorder)
                left = leftBorder;
            else if (left > rightBorder)
                left = rightBorder;
            return left;
        }
    };

    private float startX;
    private float startY;

    public SlideView(Context context) {
        super(context);
        init();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 打开操作栏
     */
    public void open() {
        int finalLeft = -mBottomChild.getWidth();
        mViewDragHelper.smoothSlideViewTo(mSurfaceChild, finalLeft, 0);
        invalidate();
    }

    /**
     * 关闭操作栏
     */
    public void closeBottom() {
        int finalLeft = 0;
        mViewDragHelper.smoothSlideViewTo(mSurfaceChild, finalLeft, 0);
        invalidate();
    }

    public void init() {
        mViewDragHelper = ViewDragHelper.create(this, mSlideCallBack);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event); // 让ViewDragHelper接受MotionEvent

        return true;
    }

    /**
     * 布局的第一个是表面控件
     * 第二个布局文件是底部控件(侧滑显示控件)
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mSurfaceChild = getChildAt(0); // 表面控件
        mBottomChild = getChildAt(1); // 底部控件

        layoutSurface();
        layoutBottom();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 布局底部视图
     */
    private void layoutBottom() {
        int leftBottom = getWidth();
        int topBottom = 0;
        int rightBottom = getWidth() + mBottomChild.getMeasuredWidth();
        int bottomBottom = getHeight();
        mBottomChild.layout(leftBottom, topBottom, rightBottom, bottomBottom);
    }

    /**
     * 布局表面视图
     */
    private void layoutSurface() {
        int left = 0;
        int top = 0;
        int right = getWidth();
        int bottom = getHeight();
        mSurfaceChild.layout(left, top, right, bottom);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            Log.i(TAG, "computeScroll: ");
            ViewCompat.postInvalidateOnAnimation(this); // 兼容性更好
//            invalidate(); // 使用这个无法正常工作
        }
    }
}
