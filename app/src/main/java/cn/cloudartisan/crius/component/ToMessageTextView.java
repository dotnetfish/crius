/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;
import com.alibaba.fastjson.JSON;

public class ToMessageTextView extends ToMessageView {
    
    public ToMessageTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        _context = paramContext;
    }
    
    public void displayMessage() {
        my_text.setFaceSize(0x18);
        try {
            my_text.setText(JSON.parseObject(message.content).getString("content"));
            return;
        } catch(Exception e) {
            my_text.setText(message.content);
        }
    }
}