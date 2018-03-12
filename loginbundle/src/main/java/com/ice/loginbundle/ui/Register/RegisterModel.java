package com.thinkwage.loginbundle.ui.Register;

import android.app.Application;

import com.api.BaseServiceFactory;
import com.api.LoginServiceFactory;
import com.google.gson.Gson;
import com.thinkwage.library.bean.NetBody;
import com.thinkwage.library.http.Subscriber.HttpSubscriber;
import com.thinkwage.loginbundle.bean.Kindergarten;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by ICE on 2017/8/10.
 */

public class RegisterModel implements RegisterStepOneContract.Model, RegisterStepTwoContract.Model {

    private Application mApplication;

    @Inject
    public RegisterModel( Application application) {

        this.mApplication = application;
    }

    @Override
    public void onDestory() {

    }

    @Override
    public Observable<NetBody<List<Kindergarten>>> getKindergartenList() {
        return LoginServiceFactory.getKindergartenList()
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());

    }

    @Override
    public Observable<NetBody<String>> register(String schoolid, String truename, String schoolno, String birthdate, String mPhotoPath) {
        MultipartBody.Part schoolidPart =
                MultipartBody.Part.createFormData("schoolid", schoolid);
        MultipartBody.Part truenamePart =
                MultipartBody.Part.createFormData("truename", truename);
        MultipartBody.Part schoolnoPart =
                MultipartBody.Part.createFormData("schoolno", schoolno);
        MultipartBody.Part birthdatePart =
                MultipartBody.Part.createFormData("birthdate", birthdate);

        File file = new File(mPhotoPath);
        String imageType = "image/" + mPhotoPath.substring(mPhotoPath.lastIndexOf("."));
        RequestBody requestBody =
                RequestBody.create(MediaType.parse(imageType), file);
        MultipartBody.Part image =
                MultipartBody.Part.createFormData("headpic", file.getName(), requestBody);
        List<MultipartBody.Part> list = new ArrayList();
        list.add(schoolidPart);
        list.add(truenamePart);
        list.add(schoolnoPart);
        list.add(birthdatePart);
        list.add(image);

        return LoginServiceFactory.register(list)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());


    }

    public Observable<NetBody<String>> getCode(String mobile) {
        return BaseServiceFactory.getCode(mobile.trim())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<NetBody<String>> registerNext(String userId, String mobile, String validCode, String password) {
        return LoginServiceFactory.registerNext(userId, mobile, validCode, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
