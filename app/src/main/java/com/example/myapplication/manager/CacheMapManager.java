package com.example.myapplication.manager;

import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;


public class CacheMapManager {
	private  static HashMap dataMap=new HashMap();

	/**
	 * This class is singleton so private constructor is used.
	 */
	private CacheMapManager() {
		super();
	}

	/**
	 * 获取Map中的数据时尽量不要使用这个方法获取，如果内存回收，应用不会重启
	 * @return
	 */
	public static HashMap getDataMap() {
		return dataMap;
	}

	public static void remove(String key) {

		if (!TextUtils.isEmpty(key)) {
			if (dataMap==null) {
				dataMap = new HashMap<String, Object>();
			}
			dataMap.remove(key);
		}

	}

	public static void setDataMap(HashMap dataMap) {
		CacheMapManager.dataMap = dataMap;
	}

	/**
	 * Adds new item to cache hashmap 存入的实体必须序列化
	 *
	 * @param key
	 * @return Cache
	 */
	public synchronized static void putCache(String key, Object object) {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.put(key, object);
	}

	/**
	 * returns cache item from hashmap
	 *
	 * @param key
	 * @return Cache
	 */
	public static <T> T getCache(Context context, String key) {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		Object obj=dataMap.get(key);
//		if (obj==null) {
//			if(!TextUtils.isEmpty(key)&&key.equals(CacheMapKeyConstant.LOGIN_KEY)&& UserManager.instance().isLogin()){
//				Logout.e("YCW", "缓存数据丢失，重启程序包");
//				Util.toRestart(context);
//			}
//		}
		return (T) obj;
	}
	//是否可以为空
	public static <T> T getCache(Context context, String key, boolean canEmpty) {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		Object obj=dataMap.get(key);
//		if (!canEmpty && obj==null) {
//			Util.toRestart(context);
//			Logout.e("YCW", "缓存数据丢失，重启程序包");
//		}
		return (T) obj;
	}

	/**
	 * Looks at the hashmap if a cache item exists or not
	 *
	 * @param key
	 * @return Cache
	 */
	public static boolean hasCache(String key) {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		return dataMap.containsKey(key);
	}

	/**
	 * 清空
	 */
	public static void invalidateAll() {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.clear();
	}

	/**
	 * 去除key值
	 *
	 * @param key
	 */
	public static void invalidate(String key) {
		if (dataMap==null) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.remove(key);
	}

}