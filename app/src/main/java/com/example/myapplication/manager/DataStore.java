package com.example.myapplication.manager;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

public class DataStore {
    protected Context context;

    public DataStore(Context context) {
        this.context = context;
    }

    public <T> void put(String key, T value) {
        put(key, value, true);
    }

    public <T> void put(String key, T value, boolean saveInFile) {
        CacheMapManager.putCache(key, value);
        if (saveInFile) {
            Hawk.put(key, value);
        } else {
        }
    }

    public <T> T get(String key) {
        T value = CacheMapManager.getCache(context, key, true);
        if (value == null) {
            value = Hawk.get(key);
            if (value != null) {
                CacheMapManager.putCache(key, value);
            }
        }
        return value;
    }

    public <T> T get(String key, T defaultValue) {
        T value = CacheMapManager.getCache(context, key, true);
        if (value == null) {
            value = Hawk.get(key, defaultValue);
            if (value != null) {
                CacheMapManager.putCache(key, value);
            }
        }
        return value;
    }

    protected void init(Context context) {
        this.context = context;
    }
}
