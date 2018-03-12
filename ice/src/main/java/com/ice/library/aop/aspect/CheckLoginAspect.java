package com.ice.library.aop.aspect;



import android.content.Intent;

import com.ice.library.constant.DataKey;
import com.ice.library.utils.DataHelper;
import com.ice.library.utils.StringUtils;
import com.ice.library.base.BaseApplication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * Created by baixiaokang
 * 通过CheckLogin注解检查用户是否登陆注解，通过aop切片的方式在编译期间织入源代码中
 * 功能：检查用户是否登陆，未登录则提示登录，不会执行下面的逻辑
 */
@Aspect
public class CheckLoginAspect {

    @Pointcut("execution(@com.ice.library.aop.annotation.CheckLogin * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (StringUtils.isNotNull(DataHelper.getStringSF(DataKey.TOKEN)) ) {
            joinPoint.proceed();//执行原方法

        } else {
            BaseApplication appContext = BaseApplication.getAppContext();
            Intent intent = new Intent();
            intent.setPackage(appContext.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName(appContext,"com.ice.loginbundle.ui.Login.LoginActivity");
            appContext.startActivity(intent);
        }

    }
}

