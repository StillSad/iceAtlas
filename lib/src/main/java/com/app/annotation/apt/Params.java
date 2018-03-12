package com.app.annotation.apt;

/**
 * Created by baixiaokang on 16/12/28.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;


@Retention(RetentionPolicy.SOURCE)
@Target(METHOD)
public @interface Params {
    String[] value();
}
