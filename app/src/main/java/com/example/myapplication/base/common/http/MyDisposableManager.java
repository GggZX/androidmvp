package com.example.myapplication.base.common.http;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MyDisposableManager {

    static MyDisposableManager instance;
    static CompositeDisposable compositeDisposable = new CompositeDisposable();
    HashMap<String, Disposable> hashMap = new HashMap();

    private MyDisposableManager() {
    }

    public static MyDisposableManager getInstance() {
        if (instance == null) {
            synchronized (MyDisposableManager.class) {
                if (instance == null)
                    instance = new MyDisposableManager();
            }
        }
        return instance;
    }


    public void addDisposable(String key, Disposable disposable) {
        if (hashMap.containsKey(key)) {
            return;
        }
        hashMap.put(key, disposable);
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public void removeDisable(String key) {
        if (hashMap.containsKey(key)) {
            Disposable disposable = hashMap.get(key);
            hashMap.remove(key);
            compositeDisposable.remove(disposable);

        }
    }


    public void cancle() {
        hashMap.clear();
        if (compositeDisposable != null)
            compositeDisposable.clear();
    }


}
