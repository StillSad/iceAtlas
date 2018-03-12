package com.ice.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.thinkwage.library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ICE on 2017/9/13.
 */

public class PhotoSelectUtils {
    public static final int COMPRESS_REQUEST_CODE = 1024;

    public static void selectFormCameraOrImage(@NonNull Activity context) {
        String cachePath = BoxingFileHelper.getCacheDir(context);
        if (TextUtils.isEmpty(cachePath)) {
            Toast.makeText(context.getApplicationContext(), R.string.boxing_storage_deny, Toast.LENGTH_SHORT).show();
            return;
        }
        BoxingConfig singleCropImgConfig =
                new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG)
                        .needCamera(R.drawable.ic_boxing_camera_white)
                        .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image);
        Boxing.of(singleCropImgConfig)
                .withIntent(context, BoxingActivity.class)
                .start(context, COMPRESS_REQUEST_CODE);
    }

    public static String getPathFromResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK) {

            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            if (requestCode == PhotoSelectUtils.COMPRESS_REQUEST_CODE) {

                final List<BaseMedia> imageMedias = new ArrayList<>(1);
                BaseMedia baseMedia = medias.get(0);
                if (!(baseMedia instanceof ImageMedia)) {
                    return null;
                }
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                return path;
            }
        }

        return null;
    }
}
