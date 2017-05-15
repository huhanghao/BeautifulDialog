package com.example.huhanghao.beautifuldialog;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static android.support.v4.view.ViewCompat.isAttachedToWindow;

/**
 * Created by huhanghao on 2017/5/11.
 */

public class MyPopWindow {

    private final Activity activity;
    private final int mBackGroundColor;
    private final WindowManager mWindowManager;
    private final WindowManager.LayoutParams params;
    private View contentView;
    private final Window window;

    public MyPopWindow(Activity context, int bg, int animatorModel) {



        activity = context;
        mBackGroundColor = bg;
        //获取WindowManager
//        mWindowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager = context.getWindowManager();
        window = context.getWindow();
        params = new WindowManager.LayoutParams();
        params.format = PixelFormat.RGBA_8888;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.setTitle("Toast");
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

    }

    public void addView(View view) {
        this.contentView = view;
        if (mWindowManager == null) return;
        // 当View已经被加到Window上去了，那么就不能再加
        if (isAttachedToWindow(contentView) || contentView.getParent() != null) return;
        mWindowManager.addView(contentView, params);
//        window.addContentView(contentView, params);
    }
}
