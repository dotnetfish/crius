/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.util;

import java.util.Comparator;
import cn.cloudartisan.crius.bean.ChatItem;

public class MessageTimeDescComparator implements Comparator<ChatItem> {
    
    public int compare(ChatItem arg0, ChatItem arg1) {
        return arg1.message.createTime.compareTo(arg0.message.createTime);
    }
}
