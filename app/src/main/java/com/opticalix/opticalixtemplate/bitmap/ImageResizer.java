package com.opticalix.opticalixtemplate.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.opticalix.opticalixtemplate.utils.LogUtils;

/**
 * Created by opticalix@gmail.com on 16/2/26.
 */
public abstract class ImageResizer {
    public static final String TAG = "ImageResizer";

    public static Bitmap resizeBitmapByPath(String path, int reqW, int reqH) {
        //get w/h of the Bitmap
        BitmapFactory.Options bitmapOpt = new BitmapFactory.Options();
        bitmapOpt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bitmapOpt);

        //resolve suitable inSampleSize
        int sampleSize = resolveSampleSize(bitmapOpt, reqW, reqH);
        bitmapOpt.inJustDecodeBounds = false;
        bitmapOpt.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(path, bitmapOpt);
    }

    /**
     * @param bitmapOpt bitmapFactory.options with w/h of a bitmap
     * @param reqW      required width
     * @param reqH      required height
     * @return suitable value of inSampleSize
     */
    public static int resolveSampleSize(BitmapFactory.Options bitmapOpt, int reqW, int reqH) {
        int inSampleSize = 1;
        int outWidth = bitmapOpt.outWidth;
        int outHeight = bitmapOpt.outHeight;

        while (outWidth / inSampleSize >= reqW && outHeight / inSampleSize >= reqH) {
            inSampleSize *= 2;
        }
        int ret = inSampleSize > 1 ? inSampleSize / 2 : 1;
        Log.d(TAG, String.format("resolve inSampleSize, reqW=%d, reqH=%d, bitmapW=%d, bitmapH=%d, ret=%d", reqW, reqH, outWidth, outHeight, ret));
        return ret;
    }

    public Bitmap resizeBitmap(int reqW, int reqH) {
        //get w/h of the Bitmap
        BitmapFactory.Options bitmapOpt = new BitmapFactory.Options();
        bitmapOpt.inJustDecodeBounds = true;
        decodeBitmap(bitmapOpt);

        //resolve suitable inSampleSize
        int sampleSize = resolveSampleSize(bitmapOpt, reqW, reqH);
        bitmapOpt.inJustDecodeBounds = false;
        bitmapOpt.inSampleSize = sampleSize;
        return decodeBitmap(bitmapOpt);
    }

    /**
     * Notice: can't use decode Bitmap by inputStream
     */
    protected abstract Bitmap decodeBitmap(BitmapFactory.Options opt);
}
