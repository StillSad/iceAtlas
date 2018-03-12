package com.ice.library.http.Subscriber;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by ICE on 2017/8/4.
 */

public abstract class HttpSubscriber<T> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(@NonNull T t) {
        resultSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        resultError(e.toString());
    }

    public abstract void resultSuccess(@NonNull T t);

    public abstract void resultError(String msg);
}
