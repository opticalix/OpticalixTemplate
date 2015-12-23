package com.opticalix.opticalixtemplate.utils;

import com.google.gson.Gson;

/**
 * Created by opticalix@gmail.com on 15/12/22.
 * Offer an instance of gson object.
 * You can use own deserializer to parse special json. http://www.javacreed.com/gson-deserialiser-example/
 */
public class GsonUtils {
    private static Gson sGson;

    private GsonUtils() {
    }

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }
}
