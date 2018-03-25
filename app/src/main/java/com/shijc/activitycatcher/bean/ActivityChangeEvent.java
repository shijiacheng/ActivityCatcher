package com.shijc.activitycatcher.bean;

/**
 * Created by shijiacheng on 2018/3/25.
 */

public class ActivityChangeEvent {
    private String packageName;
    private String className;

    public ActivityChangeEvent(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
