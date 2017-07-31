package com.opticalix.opticalixtemplate.view.crop;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.opticalix.opticalixtemplate.R;
import com.opticalix.opticalixtemplate.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

/**
 * Created by Felix on 2017/07/31.
 */
public class CalcOffsetView extends RelativeLayout {
    private static final String TAG = CalcOffsetView.class.getSimpleName();
    public static int ORIENTATION_HORIZONTAL = 1;
    public static int ORIENTATION_VERTICAL = 2;
    ImageView mIv;
    CropOverlay mCropOverlay;
    private int[] mRealImgPositions;

    public CalcOffsetView(Context context) {
        this(context, null, 0);
    }

    public CalcOffsetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalcOffsetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_calc_offset, this, true);
        mIv = (ImageView) view.findViewById(R.id.iv);
        mCropOverlay = (CropOverlay) view.findViewById(R.id.overlay);
    }

    private boolean isRealImgPositionsReady() {
        return mRealImgPositions != null;
    }

    private void setupCropOverlay() {
        mRealImgPositions = getBitmapPositionInsideImageView(mIv);
        LogUtils.d(TAG, "mRealImgPositions: "+ Arrays.toString(mRealImgPositions));

        mCropOverlay.setOrientation(judgeOrientation());
        RectF rectF = new RectF(mRealImgPositions[0], mRealImgPositions[1], mRealImgPositions[0] + mRealImgPositions[2], mRealImgPositions[1] + mRealImgPositions[3]);
        mCropOverlay.setFrame(rectF);
        mCropOverlay.setStartPosition(judgeOrientation() == ORIENTATION_HORIZONTAL ? mRealImgPositions[0] : mRealImgPositions[1]);
        mCropOverlay.setEndPosition(judgeOrientation() == ORIENTATION_HORIZONTAL ? mRealImgPositions[2] : mRealImgPositions[3]);
        requestLayout();
    }

    private int judgeOrientation() {
        return mRealImgPositions[2] > mRealImgPositions[3] ? ORIENTATION_HORIZONTAL : ORIENTATION_VERTICAL;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            int measureHeight = child.getMeasuredHeight();
            if (child instanceof ImageView) {
                child.layout(0, 0, measuredWidth, measureHeight);
            } else if (child instanceof  CropOverlay) {
                if (!isRealImgPositionsReady()) {
                    return;
                }
                if (judgeOrientation() == ORIENTATION_HORIZONTAL) {
                    child.layout(mRealImgPositions[0], mRealImgPositions[1],
                            mRealImgPositions[0] + measuredWidth, mRealImgPositions[1] + mRealImgPositions[3]);
                } else {
                    child.layout(mRealImgPositions[0], mRealImgPositions[1],
                            mRealImgPositions[0] + mRealImgPositions[2], mRealImgPositions[1] + measureHeight);
                }
            }
        }
    }

    /**
     * Returns the bitmap position inside an imageView.
     * @param imageView source ImageView
     * @return 0: left, 1: top, 2: width, 3: height
     */
    private int[] getBitmapPositionInsideImageView(ImageView imageView) {
        int[] ret = new int[4];

        if (imageView == null || imageView.getDrawable() == null)
            return ret;

        // Get image dimensions
        // Get image matrix values and place them in an array
        float[] f = new float[9];
        imageView.getImageMatrix().getValues(f);

        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
        final float scaleX = f[Matrix.MSCALE_X];
        final float scaleY = f[Matrix.MSCALE_Y];

        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
        final Drawable d = imageView.getDrawable();
        final int origW = d.getIntrinsicWidth();
        final int origH = d.getIntrinsicHeight();

        // Calculate the actual dimensions
        final int actW = Math.round(origW * scaleX);
        final int actH = Math.round(origH * scaleY);

        ret[2] = actW;
        ret[3] = actH;

        // Get image position
        // We assume that the image is centered into ImageView
        int imgViewW = imageView.getWidth();
        int imgViewH = imageView.getHeight();

        int top = (int) (imgViewH - actH)/2;
        int left = (int) (imgViewW - actW)/2;

        ret[0] = left;
        ret[1] = top;

        return ret;
    }

    public void setImageUrl(String url) {
        Picasso.with(getContext()).load(Uri.parse(url)).into(mIv, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        setupCropOverlay();
                    }
                });
            }

            @Override
            public void onError() {

            }
        });
    }

    public int getOffset() {
        return mCropOverlay.getOffset();
    }

    public int getOrientation() {
        return mCropOverlay.getOrientation();
    }
}
