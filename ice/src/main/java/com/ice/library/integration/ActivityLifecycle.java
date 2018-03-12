package com.ice.library.integration;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;


import com.ice.library.base.delegate.ActivityDelegateImpl;
import com.ice.library.base.delegate.FragmentDelegate;
import com.ice.library.base.delegate.FragmentDelegateImpl;
import com.ice.library.base.delegate.ActivityDelegate;
import com.ice.library.base.delegate.IActivity;
import com.ice.library.base.delegate.IFragment;
import com.ice.library.utils.LogUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by jess on 21/02/2017 14:23
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private AppManager mAppManager;
    private Application mApplication;
    private Map<String, Object> mExtras;
    private FragmentLifecycle mFragmentLifecycle;
    private List<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle(AppManager appManager, Application application, Map<String, Object> extras) {
        this.mAppManager = appManager;
        this.mApplication = application;
        this.mExtras = extras;
    }

    @Override
    public void onActivityCreated(final Activity activity, Bundle savedInstanceState) {
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            mAppManager.addActivity(activity);

        //配置ActivityDelegate
        if (activity instanceof IActivity && activity.getIntent() != null) {
            ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                activity.getIntent().putExtra(ActivityDelegate.ACTIVITY_DELEGATE, activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        /**
         * 给每个Activity配置Fragment的监听,Activity可以通过 {@link IActivity#useFragment()} 设置是否使用监听
         * 如果这个Activity返回false的话,这个Activity将不能使用{@link FragmentDelegate},意味着 {@link com.jess.arms.base.BaseFragment}也不能使用
         */
        boolean useFragment = activity instanceof IActivity ? ((IActivity) activity).useFragment() : true;
        if (activity instanceof FragmentActivity && useFragment) {
            if (mFragmentLifecycle == null) {
                mFragmentLifecycle = new FragmentLifecycle();
            }
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle, true);
        }

//        //这里全局给Activity设置toolbar和title,你想象力有多丰富,这里就有多强大,以前放到BaseActivity的操作都可以放到这里
//        //找到 Toolbar 并且替换 Actionbar
//        if (activity.findViewById(R.id.toolbar) != null) {
//            if (activity instanceof AppCompatActivity) {
//                ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
//                ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
//            } else {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
//                    activity.getActionBar().setDisplayShowTitleEnabled(false);
//                }
//            }
//        }
//        //找到 Toolbar 的标题栏并设置标题名
//        if (activity.findViewById(R.id.toolbar_title) != null) {
//            ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
//        }
//        //找到 Toolbar 的返回按钮,并且设置点击事件,点击关闭这个 Activity
//        if (activity.findViewById(R.id.toolbar_back) != null) {
//            activity.findViewById(R.id.toolbar_back).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    activity.onBackPressed();
//                }
//            });
//        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mAppManager.setCurrentActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mAppManager.removeActivity(activity);

        ActivityDelegate activityDelegate = fetchActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
            activity.getIntent().removeExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
    }

    private ActivityDelegate fetchActivityDelegate(Activity activity) {
        ActivityDelegate activityDelegate = null;
        if (activity instanceof IActivity && activity.getIntent() != null) {
            activityDelegate = activity.getIntent().getParcelableExtra(ActivityDelegate.ACTIVITY_DELEGATE);
        }
        return activityDelegate;
    }


    static class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {


        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            super.onFragmentAttached(fm, f, context);
            LogUtils.w(f.toString() + " - onFragmentAttached");
            if (f instanceof IFragment && f.getArguments() != null) {
                FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
                if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                    fragmentDelegate = new FragmentDelegateImpl(fm, f);
                    f.getArguments().putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
                }
                fragmentDelegate.onAttach(context);
            }
        }

        @Override
        public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
            LogUtils.w(f.toString() + " - onFragmentCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState);
            LogUtils.w(f.toString() + " - onFragmentViewCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onCreateView(v, savedInstanceState);
            }
        }

        @Override
        public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
            super.onFragmentActivityCreated(fm, f, savedInstanceState);
            LogUtils.w(f.toString() + " - onFragmentActivityCreated");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onActivityCreate(savedInstanceState);
            }
        }

        @Override
        public void onFragmentStarted(FragmentManager fm, Fragment f) {
            super.onFragmentStarted(fm, f);
            LogUtils.w(f.toString() + " - onFragmentStarted");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onStart();
            }
        }

        @Override
        public void onFragmentResumed(FragmentManager fm, Fragment f) {
            super.onFragmentResumed(fm, f);
            LogUtils.w(f.toString() + " - onFragmentResumed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onResume();
            }
        }

        @Override
        public void onFragmentPaused(FragmentManager fm, Fragment f) {
            super.onFragmentPaused(fm, f);
            LogUtils.w(f.toString() + " - onFragmentPaused");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onPause();
            }
        }

        @Override
        public void onFragmentStopped(FragmentManager fm, Fragment f) {
            super.onFragmentStopped(fm, f);
            LogUtils.w(f.toString() + " - onFragmentStopped");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onStop();
            }
        }

        @Override
        public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentViewDestroyed(fm, f);
            LogUtils.w(f.toString() + " - onFragmentViewDestroyed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDestroyView();
            }
        }

        @Override
        public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
            super.onFragmentDestroyed(fm, f);
            LogUtils.w(f.toString() + " - onFragmentDestroyed");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDestroy();
            }
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            super.onFragmentDetached(fm, f);
            LogUtils.w(f.toString() + " - onFragmentDetached");
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate != null) {
                fragmentDelegate.onDetach();
                f.getArguments().clear();
            }
        }

        private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
            if (fragment instanceof IFragment) {
                if (fragment.getArguments() == null) {
                    return null;
                } else {
                    return fragment.getArguments().getParcelable(FragmentDelegate.FRAGMENT_DELEGATE);
                }


            }
            return null;
        }
    }

}
