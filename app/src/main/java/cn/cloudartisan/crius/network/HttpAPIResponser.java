/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.network;

import cn.cloudartisan.crius.bean.Page;

import java.util.List;
import java.util.Map;

public interface  HttpAPIResponser {
    
     Map getRequestParams(String code);
    
    
    void onFailed(Exception p1);
    
    
    void onRequest();
    
    
    void onSuccess(Object p1, List p2, Page p3, String p4, String p5);
    
}
