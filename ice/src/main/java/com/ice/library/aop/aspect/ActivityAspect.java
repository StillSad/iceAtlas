/*
 * ActivityAspect      2016-03-04
 * Copyright (c) 2016 hujiang Co.Ltd. All right reserved(http://www.hujiang.com).
 * 
 */

package com.ice.library.aop.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

//import com.hujiang.common.util.ToastUtils;
//import com.hujiang.framework.app.RunTimeManager;

/**
 * activity aspect
 *
 * @author simon
 * @version 1.0.0
 * @since 2016-03-04
 */
@Aspect
public class ActivityAspect {

//    @Pointcut("execution(* android.app.Activity.onCreate(..))")
//    public void onCreateCutPoint() {
//
//    }
//
//    @Pointcut("execution(* android.app.Activity.onResume())")
//    public void onResumeCutPoint() {
//
//    }
//
//    @Pointcut("execution(* android.app.Activity.onPause(..))")
//    public void onPauseCutPoint() {
//
//    }
//
//    @Pointcut("execution(* android.app.Activity.onStart(..))")
//    public void onStartCutPoint() {
//
//    }
//
//    @Pointcut("execution(* android.app.Activity.onStop(..))")
//    public void onStopCutPoint() {
//
//    }
//
//    @Pointcut("execution(* android.app.Activity.onDestroy(..))")
//    public void onDestroyCutPoint() {
//
//    }


    @After("execution(* com.ice.app.ui.MainActivity.on**(..))")
    public void onResumeMethod(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    ///////////
    @After("execution(* com.hujiang.library.demo.Greeter.**())")
    public void greeterAdvice(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    @After("execution(* android.support.v4.app.Fragment.on**(..))")
    public void fragmentMethod(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    @After("call(* com.hujiang.library.demo.AspectJavaDemo.work())")
    public void aspectJavaDemoAdvice(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    @After("execution(* com.nostra13.universalimageloader.core.ImageLoader.displayImage(..))")
    public void aspectImageLoader(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    @After("execution(* com.hujiang.library.demo.NormalClass.**(..))")
    public void aspectNormalClass(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

    @Around("execution( * com.hujiang.library.demo.AOPActivity.onCreate(..))")
    public void aopActivityAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();

        Log.i("helloAOP", "aspect:::" + "------------>>>>>AOPActivity.onCreate");
    }
}