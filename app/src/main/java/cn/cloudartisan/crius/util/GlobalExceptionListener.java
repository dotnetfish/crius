/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.util;

import android.content.Context;
import cn.cloudartisan.crius.app.LvxinApplication;

public class GlobalExceptionListener implements Thread.UncaughtExceptionHandler {
    Context mcontext;
    private static GlobalExceptionListener INSTANCE = new GlobalExceptionListener();
    
    private GlobalExceptionListener() {
    }
    
    public static GlobalExceptionListener getInstance() {
        return INSTANCE;
    }
    
    public void init(Context ctx) {
        mcontext = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    
    public void uncaughtException(Thread thread, Throwable ex) {
        CrashLogUtils.getInstace().saveErrorLogToFile(ex);
        ex.printStackTrace();
        LvxinApplication.finishAllActivity();
        //TODO:uncomment this when release
        //android.os.Process.killProcess();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}