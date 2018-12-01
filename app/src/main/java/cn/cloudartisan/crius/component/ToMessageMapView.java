/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class ToMessageMapView extends ToMessageView {
    
    public ToMessageMapView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        _context = paramContext;
    }
    
    public void displayMessage() {
        my_map_layout.setTag(message.file);
        my_map_layout.initViews(message);
        message_content.setOnClickListener(my_map_layout);
        if(message.status.equals("-2")) {
            my_map_layout.uploadProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
