/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean isScrollable;
    
    public CustomViewPager(Context context) {
        super(context);
    }
    
    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isScrollable) {
            return false;
        }
        return super.onTouchEvent(ev);
    }
    
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isScrollable) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
    
    public boolean isScrollable() {
        return isScrollable;
    }
    
    public void setScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }
}
