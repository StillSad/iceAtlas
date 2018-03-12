package com.ice.library.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * 数据库切片，根据注解DbRealm，缓存方法中含有需要缓存到数据库的RealmObject的参数
 * Created by baixiaokang on 17/1/24.
 */
@Aspect
public class DbRealmAspect {

    @Pointcut("execution(@com.ice.library.aop.annotation.DbRealm * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();//执行原方法
//        Observable.fromIterable(Arrays.asList(joinPoint.getArgs()))
//                .filter(obj -> {
//                    return obj instanceof RealmObject || obj instanceof List;
//                })
//                .subscribe(obj -> {
//                    realm.beginTransaction();
//                    if (obj instanceof List) realm.copyToRealmOrUpdate((List) obj);
//                    else realm.copyToRealmOrUpdate((RealmObject) obj);
//                    realm.commitTransaction();
//                },e -> {
//                    e.printStackTrace();
//                });
    }
}
