package com.opticalix.opticalixtemplate.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.lang.reflect.Type;

/**
 * Created by opticalix@gmail.com on 15/12/22.
 * Offer an instance of gson object.
 * You can use own deserializer/typeAdapter to parse special json.
 * http://www.javacreed.com/gson-deserialiser-example/
 * http://www.javacreed.com/gson-typeadapter-example/
 */
public class GsonUtils {
    private static GsonUtils sInstance;
    private static Gson sGson;

    private GsonUtils() {
    }

    public static Gson getSimpleGson() {
        if (sGson == null) {
            sGson = new Gson();
        }
        return sGson;
    }

    public static GsonUtils getInstance() {
        if (sInstance == null)
            sInstance = new GsonUtils();
        return sInstance;
    }

    public Gson buildGson(Type type, TypeAdapter typeAdapter){
        GsonBuilder gb = new GsonBuilder().registerTypeAdapter(type, typeAdapter);
        return gb.create();
    }
}
