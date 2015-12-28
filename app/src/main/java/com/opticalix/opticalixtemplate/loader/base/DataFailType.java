package com.opticalix.opticalixtemplate.loader.base;

/**
 * Created by opticalix@gmail.com on 15/10/16.
 * Fail code at Loader layer.
 */
public enum DataFailType {
    DATA_EMPTY(-1, "Nothing to show"),
    DATA_NO_NET(-2, "The network is not available"),
    DATA_FAIL(-3, "Fail to load data"),
    DATA_UNKNOWN(-10086, "Unknown error");

    private int value = 0;
    private String desc = "";

    DataFailType(int value, String desc
    ) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
