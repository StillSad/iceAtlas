package com.ice.library.aop.aspect;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;


import com.ice.library.aop.annotation.Permission;
import com.ice.library.base.BaseApplication;
import com.ice.library.utils.MPermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by baixiaokang on 17/1/31.
 * 申请系统权限切片，根据注解值申请所需运行权限
 */
@Aspect
public class SysPermissionAspect {

    @Around("execution(@com.ice.library.aop.annotation.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        final Activity ac =  BaseApplication.getAppContext().getCurActivity();
        if (ac == null) {
            return;
        }
        //判断是否授权
        if (MPermissionUtils.checkPermissions(ac,permission.value()) || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            joinPoint.proceed();//获得权限，执行原方法
            return;//已授权
        }

        //未授权
        new AlertDialog.Builder(ac)
                .setTitle("提示")
                .setMessage("为了应用可以正常使用，请您点击确认申请权限。")
                .setNegativeButton("取消", null)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MPermissionUtils.requestPermissionsResult(ac, 1, permission.value()
                                , new MPermissionUtils.OnPermissionListener() {
                                    @Override
                                    public void onPermissionGranted() {
                                        try {
                                            joinPoint.proceed();//获得权限，执行原方法
                                        } catch (Throwable e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onPermissionDenied() {
                                        MPermissionUtils.showTipsDialog(ac);
                                    }
                                });
                    }
                })
                .create()
                .show();

    }


}


