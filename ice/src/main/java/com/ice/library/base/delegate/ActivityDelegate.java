package com.ice.library.base.delegate;

import android.os.Bundle;
import android.os.Parcelable;

/**
 * Created by ICE on 2017/6/12.
 */

public interface ActivityDelegate extends Parcelable{
    String LAYOUT_LINEARLAYOUT = "LinearLayout";
    String LAYOUT_FRAMELAYOUT = "FrameLayout";
    String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    String ACTIVITY_DELEGATE = "activity_delegate";


    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(Bundle outState);

    void onDestroy();
}
