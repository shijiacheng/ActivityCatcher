package com.shijc.activitycatcher;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.view.accessibility.AccessibilityEvent;

import com.shijc.activitycatcher.bean.ActivityChangeEvent;
import com.shijc.activitycatcher.widget.FloatWindowManager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author shijiacheng
 *
 */

public class ActivityCatcherService extends AccessibilityService {

    public static final String COMMAND = "command";
    public static final String COMMAND_OPEN = "command_open";
    public static final String COMMAND_CLOSE = "command_close";

    private FloatWindowManager floatWindowManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (floatWindowManager == null) {
            floatWindowManager = new FloatWindowManager(this);
        }
        String command = intent.getStringExtra(COMMAND);

        if (command != null) {
            if (command.equals(COMMAND_OPEN)) {
                floatWindowManager.addView();
            } else if (command.equals(COMMAND_CLOSE)) {
                floatWindowManager.removeView();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageNameChange = event.getPackageName().toString();
            String classNameChange = event.getClassName().toString();
            EventBus.getDefault().post(new ActivityChangeEvent(packageNameChange, classNameChange));
        }

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
