/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.util;

import java.util.Comparator;
import cn.cloudartisan.crius.bean.Message;

public class ChatRecordTimeDescComparator implements Comparator<Message> {
    
    public int compare(Message arg0, Message arg1) {
        return arg1.createTime.compareTo(arg0.createTime);
    }
}
