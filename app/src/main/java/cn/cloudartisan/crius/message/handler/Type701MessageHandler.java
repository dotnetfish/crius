/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.message.handler;

import android.content.Context;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.db.BottleDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;

import java.util.HashMap;

public class Type701MessageHandler implements CustomMessageHandler {
    public HashMap<String, Object> apiParams;
    
    public boolean handle(Context context, Message message) {
        String gid = message.content;
        Bottle bottle = BottleDBManager.getManager().queryById(gid);
        if(bottle == null) {
            return false;
        }
        if(Global.getCurrentUser().getAccount().equals(bottle.sender)) {
            MessageDBManager.getManager().deleteBySenderAndType(bottle.receiver, "700");
        } else {
            MessageDBManager.getManager().deleteBySenderAndType(bottle.sender, "700");
        }
        BottleDBManager.getManager().deleteById(gid);
        MessageDBManager.getManager().deleteById(message.gid);
        return false;
    }
}
