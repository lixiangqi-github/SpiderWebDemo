package com.mozre.spider;

import android.support.annotation.NonNull;

public class SpiderData {
    private String name;
    private float value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public SpiderData(@NonNull String name,@NonNull float value) {

        this.name = name;
        this.value = value;
    }
}
