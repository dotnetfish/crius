/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.util;

import cn.cloudartisan.crius.bean.Message;

public class MessageUtil {
    
    public static Message transform(cn.cloudartisan.crius.client.model.Message msg) {
        Message m = new Message();
        m.content =msg.getContent();
        m.file = msg.getFile();
        m.title = msg.getTitle();
        m.fileType = msg.getFileType();
        m.receiver = msg.getReceiver();
        m.sender = msg.getSender();
        m.type =msg. getType();
        m.gid = msg.getMid();
        m.createTime = String.valueOf(msg.getTimestamp());
        m.status = "0";
        return m;
    }
    
    public static Message clone(Message msg) {
        Message m = new Message();
        m.content =msg.content;
        m.file = msg.file;
        m.title = msg.title;
        m.fileType = msg.fileType;
        m.receiver = msg.receiver;
        m.sender = msg.sender;
        m.type = msg.type;
        m.gid = msg.gid;
        m.createTime = msg.createTime;
        m.status = msg.status;
        return m;
    }
}
