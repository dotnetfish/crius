/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.message.handler;

import android.content.Context;
import cn.cloudartisan.crius.bean.Message;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import android.content.Intent;
import cn.cloudartisan.crius.bean.ChatItem;
import cn.cloudartisan.crius.bean.Friend;

public class Type109MessageHandler implements CustomMessageHandler {
    
    public boolean handle(Context context, Message message) {
        String account = JSON.parseObject(message.content).getString("sourceAccount");
        FriendDBManager.getManager().deleteFriend(account);
        MessageDBManager.getManager().deleteBySender(account);
        MessageDBManager.getManager().deleteById(message.gid);
        Intent intent = new Intent();
        intent.setAction("com.farsunset.cim.DELETE_APPEND");
        intent.putExtra(ChatItem.NAME, (new Friend(account)));
        context.sendBroadcast(intent);
        return false;
    }
}