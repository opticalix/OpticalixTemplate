package com.opticalix.opticalixtemplate.utils;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

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

    /**
     * for test
     */
    public static void baseGson() {
        String json = "{\"name\":\"gson\",\"date\":\"2015/12/29\"}";
        BaseGson fromJson = getSimpleGson().fromJson(json, BaseGson.class);
        Log.d("Gson", fromJson.toString());

        String toJson = getSimpleGson().toJson(fromJson);

    }

    /**
     * for test
     */
    public static void annotataionGson(){
        String json = "{\"name\":\"gson\",\"date\":\"2015/12/29\", \"mobile_phone\":\"13111111111\"}";
        Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        BaseGson fromJson = g.fromJson(json, BaseGson.class);

        Log.d("Gson", fromJson.toString());

        Log.d("Gson", g.toJson(fromJson));

        new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
    }

    static class BaseGson {
        @Expose
        public String name;
        public String date;
        @SerializedName("mobile_phone")
        public String mobilePhone;

        @Override
        public String toString() {
            return "BaseGson{" +
                    "name='" + name + '\'' +
                    ", date='" + date + '\'' +
                    ", mobilePhone='" + mobilePhone + '\'' +
                    '}';
        }
    }

}
