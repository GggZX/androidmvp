package com.example.myapplication.base.baseview;

import io.reactivex.disposables.Disposable;

/**
 * <pre>
 */
public abstract class BaseModel {

    private Disposable disposable;

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    /**
     * 取消当前网络请求
     */
    public void cancel() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = null;
    }
}
