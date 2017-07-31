package com.opticalix.opticalixtemplate.view.crop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.opticalix.opticalixtemplate.utils.LogUtils;

import java.util.Locale;

/**
 * Created by Felix on 2017/07/31.
 */
public class CropOverlay extends View {

    private static final String TAG = CropOverlay.class.getSimpleName();
    private RectF mFrameRect;
    private int mOrientation;
    private float mStartPosition;
    private float mEndPosition;
    private float mDownRawX;
    private float mDownRawY;
    private float mPrevRawX;
    private float mPrevRawY;

    public CropOverlay(Context context) {
        this(context, null, 0);
    }

    public CropOverlay(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropOverlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public int getOrientation() {
        return mOrientation;
    }

    /**
     * overlay可移动的区域
     * @param rectF
     */
    public void setFrame(RectF rectF) {
        mFrameRect = rectF;
    }

    public void setStartPosition(float pos) {
        mStartPosition = pos;
    }

    public void setEndPosition(float pos) {
        mEndPosition = pos;
    }

    public int getOffset() {
        int startPos = Math.round(mStartPosition);
        return mOrientation == CalcOffsetView.ORIENTATION_HORIZONTAL ? (getLeft() - startPos) : (getTop() - startPos);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //ratio 1:1
        if (mFrameRect == null) {
            return;
        }
        int size = Math.min((int) mFrameRect.width(), (int) mFrameRect.height());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(50, 0, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownRawX = event.getRawX();
                mDownRawY = event.getRawY();
                mPrevRawX = mDownRawX;
                mPrevRawY = mDownRawY;
                return true;
            case MotionEvent.ACTION_MOVE:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                float dX = rawX - mPrevRawX;
                float dY = rawY - mPrevRawY;

                int[] correctDelta = correctDelta(dX, dY);
                if (mOrientation == CalcOffsetView.ORIENTATION_HORIZONTAL) {
                    ViewCompat.offsetLeftAndRight(this, correctDelta[0]);
                } else {
                    ViewCompat.offsetTopAndBottom(this, correctDelta[1]);
                }
                mPrevRawX = rawX;
                mPrevRawY = rawY;
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float startPos = mOrientation == CalcOffsetView.ORIENTATION_HORIZONTAL ? mDownRawX : mDownRawY;
                float endPos = mOrientation == CalcOffsetView.ORIENTATION_HORIZONTAL ? event.getRawX() : event.getRawY();
                LogUtils.d(TAG, String.format(Locale.getDefault(), "move distance: %d", (int) (endPos - startPos)));
                return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean checkMoveValid(float dX, float dY) {
        if (mOrientation == CalcOffsetView.ORIENTATION_HORIZONTAL) {
            return (this.getLeft() + dX) >= mStartPosition && (this.getRight() + dX) <= mEndPosition;
        } else {
            return (this.getTop() + dY) >= mStartPosition && (this.getBottom() + dY) <= mEndPosition;
        }
    }

    private int[] correctDelta(float dX, float dY) {
        int[] ret = new int[]{Math.round(dX), Math.round(dY)};
        int l = getLeft();
        int r = getRight();
        int t = getTop();
        int b = getBottom();
        if (dX < 0 && l + dX < mStartPosition) {
            ret[0] = (int) (mStartPosition - l + 0.5f);
        }
        if (dX > 0 && r + dX > mEndPosition) {
            ret[0] = (int) (mEndPosition - r + 0.5f);
        }
        if (dY < 0 && t + dY < mStartPosition) {
            ret[1] = (int) (mStartPosition - t + 0.5f);
        }
        if (dY > 0 && b + dY > mEndPosition) {
            ret[1] = (int) (mEndPosition - b + 0.5f);
        }
        return ret;
    }
}
