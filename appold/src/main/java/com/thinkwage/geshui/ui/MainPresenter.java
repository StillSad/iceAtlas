package com.thinkwage.geshui.ui;

import android.app.Application;

import com.api.AppServicesFactory;
import com.ice.library.di.scope.ActivityScope;
import com.ice.library.integration.AppManager;
import com.ice.library.mvp.BasePresenter;

import javax.inject.Inject;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * Created by mac on 17/3/19.
 */

@ActivityScope
public class MainPresenter extends BasePresenter<MainModel, MainContract.View> {
    private Application mApplication;
    private AppManager mAppManager;

    @Inject
    public MainPresenter(MainModel model, Application application, AppManager appManager) {
        super(model);
        this.mApplication = application;
        this.mAppManager = appManager;

    }

    public void homeIndex() {
//        Observable.interval(0, 1, TimeUnit.SECONDS)
//                .subscribeOn( Schedulers.io())
//                .compose(RxUtils.<Long>bindToLifecycle((IView) mRootView))   //这个订阅关系跟Activity绑定，Observable 和activity生命周期同步
//                .observeOn( AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        System.out.println("lifecycle--" + aLong);
////                        mViewBinding.userBirth.setText("aLong" + aLong);
//                    }
//                });

    }

    public void exitApp() {
        mAppManager.appExit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mApplication = null;
    }

}
