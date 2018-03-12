package com.thinkwage.visiontestbundle.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

/**
 * Created by ICE on 2017/8/21.
 */

public class E extends ImageView {
    //5米视力表边长
    //4.0 72.72
    //4.1 57.76
    //4.2 45.88
    //4.3 36.45
    //4.4 28.95
    //4.5 23.00
    //4.6 18.27
    //4.7 14.51
    //4.8 11.53
    //4.9 9.16
    //5.0 7.27
    //5.1 5.78
    //5.2 4.59
    //5.3 3.64
    double[] eLengths = {72.7074256, 57.75472682, 45.87713625, 36.44224025, 28.94768468, 22.99442742, 18.26549164, 14.5090886, 11.52521138, 9.154985603, 7.272210345, 5.776638609, 4.588639772, 3.644959704};
    private float xdpi;
    private float ydpi;

    public static final int D_LEFT = 0;
    public static final int D_UP = 1;
    public static final int D_RIGHT = 2;
    public static final int D_DOWN = 3;


    public E(Context context) {
        this(context, null);
    }

    public E(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public E(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDpi();
    }

    private void initDpi() {
        Point point = new Point();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRealSize(point);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);

        //获取屏幕的款和高
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        float density = dm.density;      // 屏幕密度
        int densityDpi = dm.densityDpi;  // 屏幕密度DPI
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;
        float i = (point.y / ydpi) * (point.y / ydpi) + (point.x / xdpi) * (point.x / xdpi);
//        主屏尺寸：5.2英寸
//        主屏分辨率：1920x1080像素
        Log.d("WindowUtil", "xdpi：" + xdpi + "ydpi：" + ydpi);
        Log.d("WindowUtil", "screenWidth：" + screenWidth + "screenHeight：" + screenHeight);
        Log.d("WindowUtil", "屏幕密度：" + density + "屏幕密度DPI：" + densityDpi);
        Log.d("WindowUtil", "屏幕尺寸：" + Math.sqrt(i));

    }

    public void setE(int direction, int level) {
        //
        if (eLengths.length - 1 < level) return;
        //设置大小
        double eLen = eLengths[level] / 2 / 25.4;
        double xl = eLen * xdpi;
        double yl = eLen * ydpi;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = (int) yl;
        layoutParams.width = (int) xl;
        setLayoutParams(layoutParams);

        //设置方向
        RotateAnimation rotateAnimation
                = new RotateAnimation(0f, direction * 90f,
                                      Animation.RELATIVE_TO_SELF,0.5f,
                                      Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);
        startAnimation(rotateAnimation);
    }

}
