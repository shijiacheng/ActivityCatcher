package com.shijc.activitycatcher.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shijc.activitycatcher.ActivityCatcherService;
import com.shijc.activitycatcher.R;
import com.shijc.activitycatcher.bean.ActivityChangeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author shijiacheng
 */

public class FloatingView extends LinearLayout {
    private Context context;
    private TextView tvPackageName;
    private TextView tvClassName;
    private ImageView ivClose;

    private WindowManager windowManager;


    public FloatingView(Context context) {
        super(context);
        this.context = context;

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView();
    }

    private void initView() {
        inflate(context, R.layout.item_floating_view, this);
        tvPackageName = findViewById(R.id.tv_package_name);
        tvClassName = findViewById(R.id.tv_class_name);
        ivClose = findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭悬浮窗
                Intent intent = new Intent(context,ActivityCatcherService.class);
                intent.putExtra(ActivityCatcherService.COMMAND,ActivityCatcherService.COMMAND_CLOSE);
                context.startService(intent);

            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceive(ActivityChangeEvent event){

        String packageName = event.getPackageName();
        String className = event.getClassName();

//        if (className.startsWith(packageName)){
//            className = className.substring(packageName.length());
//        }

        tvPackageName.setText(packageName);
        tvClassName.setText(className);

    }

    private Point moveBefore;
    private Point moveAfter;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveBefore = new Point((int) event.getRawX(), (int) event.getRawY());
                break;

            case MotionEvent.ACTION_MOVE:
                moveAfter = new Point((int) event.getRawX(), (int) event.getRawY());

                WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
                int dx = moveAfter.x - moveBefore.x;
                int dy = moveAfter.y - moveBefore.y;

                params.x += dx;
                params.y += dy;

                windowManager.updateViewLayout(this, params);

                moveBefore = moveAfter;

                break;
        }

        return false;
    }
}
