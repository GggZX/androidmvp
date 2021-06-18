package com.example.myapplication.base.common.http.paramsutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by liukang on 2018/4/13 10:32.
 * 有些需要处理后封装成一定结构的json串
 */

public class JsonBuilder {

    public static JsonBuilder mInstance = null;
    JSONObject jsonObject = new JSONObject();

    private JsonBuilder() {
        super();
    }

    public static JsonBuilder createBuilder() {
        mInstance = new JsonBuilder();
        return mInstance;
    }


    public JsonBuilder addParams(String key, Object value) {
        if (value == null) {
            jsonObject.put(key, "");
        } else {
            jsonObject.put(key, value);
        }
        return this;
    }

    public String build2Json() {
        return JSON.toJSONString(jsonObject);
    }
    public String build2JsonObject() {
        return JSON.toJSONString(jsonObject);
    }


}
