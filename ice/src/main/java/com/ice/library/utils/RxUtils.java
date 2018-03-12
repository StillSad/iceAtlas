package com.ice.library.utils;

import com.ice.library.base.fragment.RxSupportFragment;
import com.ice.library.base.activity.RxSupportActivity;
import com.ice.library.mvp.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by jess on 11/10/2016 16:39
 * Contact with jess.yan.effort@gmail.com
 */

public class RxUtils {

    private RxUtils() {

    }

//    public static <T> ObservableTransformer<T, T> applySchedulers(final BaseView view) {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public Observable<T> apply(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Consumer<Disposable>() {
//                            @Override
//                            public void accept(@NonNull Disposable disposable) throws Exception {
//                                view.showLoading();//显示进度条
//                            }
//                        })
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doAfterTerminate(new Action() {
//                            @Override
//                            public void run() {
//                                view.hideLoading();//隐藏进度条
//                            }
//                        }).compose(RxUtils.<T>bindToLifecycle(view));
//            }
//        };
//    }


    public static <T> LifecycleTransformer<T> bindToLifecycle(BaseView view) {
        if (view instanceof RxSupportActivity || view instanceof RxSupportFragment){
            return ((LifecycleProvider)view).bindToLifecycle();
        }else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }

    }

}
