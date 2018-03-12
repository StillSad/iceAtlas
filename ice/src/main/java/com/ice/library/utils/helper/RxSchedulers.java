package com.ice.library.utils.helper;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers<T> {

    public static final ObservableTransformer<?, ?> mTransformer =
            new ObservableTransformer<Object, Object>() {
                @Override
                public ObservableSource<Object> apply(@NonNull Observable<Object> upstream) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
//    public static final Observable.Transformer<?, ?> mTransformer
//            = observable -> observable
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> io_main() {
        return (ObservableTransformer<T, T>) mTransformer;
    }
}
