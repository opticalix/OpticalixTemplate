package com.opticalix.opticalixtemplate.model;

import com.opticalix.opticalixtemplate.utils.LogUtils;

import java.lang.reflect.Field;

/**
 * Created by opticalix@gmail.com on 16/1/11.
 */
public class BaseModel {

    public void printFields() {
        Field[] declaredFields = this.getClass().getDeclaredFields();
        String msg = "";
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                try {
                    msg += field.getName() + ":" + field.get(this).toString() + ", ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            msg += "\n";
        }
        LogUtils.d(this, msg);
    }
}
