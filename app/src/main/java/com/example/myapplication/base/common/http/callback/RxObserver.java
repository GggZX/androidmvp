package com.example.myapplication.base.common.http.callback;




import com.example.myapplication.base.baseview.BaseModel;
import com.example.myapplication.base.common.http.HttpResponse;
import com.example.myapplication.base.common.http.exception.ExceptionHandle;
import com.example.myapplication.base.common.http.exception.ResponeThrowable;
import com.example.myapplication.utils.Logout;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : liukang
 *     desc   : 先实现错误回调，再实现其它回调
 *     version: 1.0
 * </pre>
 */
public abstract class RxObserver<T> implements Observer<HttpResponse<T>> {

    BaseModel baseModel;

    public RxObserver(BaseModel baseModel) {
        this.baseModel = baseModel;
    }

    @Override
    public void onSubscribe(Disposable d) {
        baseModel.setDisposable(d);
    }


    String message = "";


    @Override
    public void onNext(HttpResponse<T> tHttpResponse) {
        Logout.e("网络请求返回json",tHttpResponse.getReturn_msg()+tHttpResponse.getReturn_code());
        if ( tHttpResponse.getData() != null) {
            onSuccess(tHttpResponse.getData());
        } else {
            message = tHttpResponse.getReturn_msg();
            message = message == null ? "网络异常，请稍后再试" : message;
            onFail(message);
        }
    }


    @Override
    public void onError(Throwable e) {
        ResponeThrowable responeThrowable = ExceptionHandle.handleException(e);
        baseModel.cancel();
        onFail(responeThrowable.message);


    }

    @Override
    public void onComplete() {
        baseModel.cancel();
    }

    public abstract void onFail(String msg);

    public abstract void onSuccess(T t);
}
