package com.example.myapplication.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 用于本地数据管理，数据存放于：assets/data.txt文件中
 * 文件格式：
 *         menuId + "_" + "请求地址（不包含域名端口号）" + ":" + Response的json字符串
 *         menuId + "_" + "请求地址（不包含域名端口号）" + ":" + Response的json字符串
 * 每一行代表一类数据
 *
 * 获取数据实例：
 * BannerResponse br = LocalData.getLocalData(getContext(), "001_getBannerInfo", BannerResponse.class);
 */
public class LocalData {

    public static <T> T getLocalData(Context context, String dataName, Class<T> c) {
        return parseData(getJson(context, dataName), c);
    }

    private static String getJson(Context context, String dataName) {//从文件中获取json串
        if (!TextUtils.isEmpty(dataName)) {
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open("data.txt")));
                String line;
                while ((line = bf.readLine()) != null) {
                    if (line.startsWith(dataName + ":")) {
                        bf.close();
                        return line.substring(dataName.length() + 1);
                    }
                }
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static <T> T parseData(String result, Class<T> c) {//Gson 解析
        try {
            Gson gson = new Gson();
//            T t =;
            return  gson.fromJson(result, c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
