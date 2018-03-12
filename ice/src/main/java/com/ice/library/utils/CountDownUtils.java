package com.ice.library.utils;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ICE on 2017/8/29.
 */

public class CountDownUtils {


    public static void countDown(int time, LifecycleProvider lifecycleProvider, final CountDownListener countDownListener) {
        getCountDown(time)
                .compose(lifecycleProvider.<Integer>bindToLifecycle())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        countDownListener.onNext(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        countDownListener.onFinish();

                    }
                });
    }

    public interface CountDownListener {
        void onNext(@NonNull Integer integer);

        void onFinish();
    }

    public static Observable<Integer> getCountDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0,1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long increaseTime) throws Exception {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);
    }
}
