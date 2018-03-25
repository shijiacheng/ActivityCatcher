package com.shijc.activitycatcher.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by shijiacheng on 2018/3/25.
 */

public class FloatWindowManager {
    private Context context;
    private WindowManager windowManager;

    private View floatingView;
    private static final WindowManager.LayoutParams LAYOUT_PARAMS;

    public FloatWindowManager(Context context) {
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    static {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.x = 0;
        params.y = 0;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        LAYOUT_PARAMS = params;
    }

    public void addView(){
        if (floatingView==null){
            floatingView = new FloatingView(context);
            floatingView.setLayoutParams(LAYOUT_PARAMS);
            windowManager.addView(floatingView,LAYOUT_PARAMS);
        }
    }

    public void removeView(){
        if (floatingView != null){
            windowManager.removeView(floatingView);
            floatingView =null;
        }
    }
}
