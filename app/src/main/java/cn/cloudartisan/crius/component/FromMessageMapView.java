/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;

public class FromMessageMapView extends FromMessageView {
    
    public FromMessageMapView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        _context = paramContext;
    }
    
    public void displayMessage() {
        others_map_layout.initViews(message);
        message_content.setOnClickListener(others_map_layout);
    }
}
