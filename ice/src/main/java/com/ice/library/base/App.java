package com.ice.library.base;



import android.support.annotation.NonNull;

import com.ice.library.di.component.AppComponent;

/**
 * Created by jess on 25/04/2017 14:54
 * Contact with jess.yan.effort@gmail.com
 */

public interface App {
    @NonNull
    AppComponent getAppComponent();
}
