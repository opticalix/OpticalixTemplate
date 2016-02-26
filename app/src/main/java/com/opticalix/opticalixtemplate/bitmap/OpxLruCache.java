package com.opticalix.opticalixtemplate.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by opticalix@gmail.com on 16/2/26.
 */
public class OpxLruCache extends LruCache<String, Bitmap> {
    public OpxLruCache() {
        this((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));
    }

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public OpxLruCache(int maxSize) {
        //allow 1/8
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }
}
