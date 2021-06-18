package com.example.myapplication.base.common.http.paramsutil;

import java.util.HashMap;

public class HashMapBuilder {

    private static HashMapBuilder mInstance;
    private static HashMap<String, Object> hashMap;

    private HashMapBuilder() {
    }

    public static HashMapBuilder createBuilder() {
        mInstance = new HashMapBuilder();
        hashMap = new HashMap<>();
        return mInstance;
    }

    public HashMapBuilder putParams(String key, Object value) {
        if (key == null) {
            throw new RuntimeException("params is not allow null");
        }
        hashMap.put(key, value);
        return mInstance;
    }

    public HashMap<String, Object> build() {
        return hashMap;
    }

}
