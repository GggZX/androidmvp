package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SPUtils {
    private static final String DEFAULT_SP_NAME = "default_sp";
    public static final String USER_AVATAR="avatar";
    public static final String USER_NAME="user_name";
    /**
     * 存储List<String>
     * @param context
     * @param key     List<String>对应的key
     * @param strList 对应需要存储的List<String>
     */
    public static void putStrListValue(Context context, String key,
                                       List<String> strList) {
        if (null == strList) {
            return;
        }
        // 保存之前先清理已经存在的数据，保证数据的唯一性
        removeStrList(context, key);
        int size = strList.size();
        putIntValue(context, key + "size", size);
        for (int i = 0; i < size; i++) {
            putStringValue(context, key + i, strList.get(i));
        }
    }

    /**
     * 取出List<String>
     *
     * @param context
     * @param key     List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(Context context, String key) {
        List<String> strList = new ArrayList<>();
        int size = getIntValue(context, key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getStringValue(context, key + i, null));
        }
        return strList;
    }

    /**
     * 取出数据（String)
     *
     * @param context
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static String getStringValue(Context context, String key,
                                        String defValue) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        String value = sp.getString(key, defValue);
        return value;
    }


    public static void logout(Context context){
        remove(context,"loginstatus");
    }

    public static boolean isLogin(Context context){
        if (getString(context,"loginstatus","")==null||getString(context,"loginstatus","").equals("")){
            return false;
        }
        else return true;


    }
    /**
     * 存储数据(String)
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putStringValue(Context context, String key, String value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.putString(key, value);
        sp.apply();
    }

    /**
     * 存储数据(Int)
     *
     * @param key
     * @param value
     */
    public static void putIntValue(Context context, String key, int value) {
        SharedPreferences.Editor sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.putInt(key, value);
        sp.apply();
    }

    /**
     * 清空List<String>所有数据
     *
     * @param context
     * @param key     List<String>对应的key
     */
    public static void removeStrList(Context context, String key) {
        int size = getIntValue(context, key + "size", 0);
        if (0 == size) {
            return;
        }
        remove(context, key + "size");
        for (int i = 0; i < size; i++) {
            remove(context, key + i);
        }
    }

    /**
     * 取出数据（int)
     *
     * @param context
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static int getIntValue(Context context, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        int value = sp.getInt(key, defValue);
        return value;
    }


    public static String getStringPreference(Context context, String key) {
        String value = "";
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getString(key, (String) null);
        }

        return value;
    }

    public static boolean setStringPreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public static float getFloatPreference(Context context, String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }

        return value;
    }

    public static boolean setFloatPreference(Context context, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public static long getLongPreference(Context context, String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }

        return value;
    }

    public static boolean setLongPreference(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public static int getIntegerPreference(Context context, String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }

        return value;
    }

    public static boolean setIntegerPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }

        return value;
    }

    public static boolean setBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        } else {
            return false;
        }
    }

    /**
     * 清空对应key数据
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.remove(key);
        sp.apply();
    }

    public static void logout(Context context, String key){
        SharedPreferences.Editor sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE)
                .edit();
        sp.remove(key);
        sp.apply();
    }

    public static <T> T getObject(Context context, Class<T> clazz) {
        String key = getKey(clazz);
        String json = getString(context, key, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void putObject(Context context, Object object) {
        String key = getKey(object.getClass());
        Gson gson = new Gson();
        String json = gson.toJson(object);
        putString(context, key, json);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void removeObject(Context context, Class<?> clazz) {
        remove(context, getKey(clazz));
    }

    public static String getKey(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * * 存储List集合
     * * @param context 上下文
     * * @param key 存储的键
     * * @param list 存储的集合
     */
    public static void putList(Context context, String key, List<? extends Serializable> list) {
        try {
            put(context, key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * 获取List集合
     * * @param context 上下文
     * * @param key 键
     * * @param <E> 指定泛型
     * * @return List集合
     */
    public static <E extends Serializable> List<E> getList(Context context, String key) {
        try {
            return (List<E>) get(context, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象
     */
    private static void put(Context context, String key, Object obj) throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();
        putString(context, key, objectStr);
    }

    /**
     * 获取对象
     */
    private static Object get(Context context, String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(context, key, null);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) {
            //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }
}
