package com.opticalix.opticalixtemplate.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.opticalix.opticalixtemplate.utils.LogUtils;


/**
 * Created by opticalix@gmail.com on 16/1/4.
 */
public class DemoCustomView extends View {

    private boolean mChanging;
    private final int startSize = 500;
    private final int endSize = 200;

    private int mWidth;
    private int mHeight;
    private ScrollerChecker mScrollerChecker;
    private Paint mPaint;

    public DemoCustomView(Context context) {
        super(context);
        init();
    }

    public DemoCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DemoCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DemoCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mScrollerChecker = new ScrollerChecker();
        setBackgroundColor(0xff00ff00);

        mPaint = new Paint();
        mPaint.setColor(0xffff0000);
    }

    public void startScroll() {
        if (mScrollerChecker != null) {
            mScrollerChecker.startScroll();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mChanging) {
            setMeasuredDimension(startSize, startSize);
        } else {
            setMeasuredDimension(mWidth, mHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mChanging) {
            canvas.drawRect(0, 0, startSize, startSize, mPaint);
        } else {
            canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
        }
    }

    public void changeSize(int currX, int currY) {
        LogUtils.d(this, "currX=" + currX + "currY=" + currY);
        mWidth = currX;
        mHeight = currY;
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        LogUtils.i("call computeScroll");
    }

    class ScrollerChecker implements Runnable {
        Scroller mScroller;

        public ScrollerChecker() {
            mScroller = new Scroller(getContext());
        }

        public void startScroll() {
            mScroller.forceFinished(true);
            removeCallbacks(this);
            mScroller.startScroll(startSize, startSize, endSize - startSize, endSize - startSize, 500);
            mChanging = true;
            post(this);
        }

        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                int currX = mScroller.getCurrX();
                int currY = mScroller.getCurrY();
                changeSize(currX, currY);
                post(this);
            } else {
                removeCallbacks(this);
                mChanging = false;
            }
        }
    }
}
