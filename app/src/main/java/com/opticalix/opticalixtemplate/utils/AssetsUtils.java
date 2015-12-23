package com.opticalix.opticalixtemplate.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by opticalix@gmail.com on 15/12/22.
 */
public class AssetsUtils {

    /**
     * Open assets file, return inputStream
     * @param context context
     * @param fullPath such as "dir/file"
     * @return inputStream
     * @throws IOException
     */
    public static InputStream open(Context context, String fullPath) throws IOException {
        AssetManager assetManager = context.getAssets();
        return assetManager.open(fullPath);
    }

    /**
     *
     * @param context context
     * @param fullPath such as "dir/file"
     * @param clazz the class what you want from gson parsing
     * @return instance of the clazz
     * @throws IOException
     */
    public static Object getObject(Context context, String fullPath, Class clazz) throws IOException {
        return GsonUtils.getGson().fromJson(new InputStreamReader(open(context, fullPath)), clazz);
    }
}
