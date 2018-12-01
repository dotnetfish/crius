/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.message.parser;

import cn.cloudartisan.crius.bean.Message;
import java.util.HashMap;
import cn.cloudartisan.crius.adapter.SystemMsgListViewAdapter;
import cn.cloudartisan.crius.bean.MessageItemSource;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.bean.Friend;

public class UserMessageParser extends MessageParser {
    
    public MessageItemSource getMessageItemSource(Message msg) {
        Friend user = FriendDBManager.getManager().queryFriend(msg.sender);
        if(user == null) {
            user = FriendDBManager.getManager().queryFriend(msg.receiver);
        }
        return user;
    }
    
    public HashMap<String, Object> decodeContent(Message msg) {
        return null;
    }
    
    public void displayInSysMsgListView(SystemMsgListViewAdapter.MessageViewHolder holder, Message message) {
    }
    
    public void encodeContent(HashMap<String, Object> data) {
    }
    
    public String getMessagePreview(Message message) {
        return MessageParserFactory.getPreviewText(message);
    }
}