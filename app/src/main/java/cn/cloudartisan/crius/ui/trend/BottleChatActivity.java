/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.trend;

import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.MessageItemSource;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.ui.chat.FriendChatActivity;
import cn.cloudartisan.crius.util.MessageUtil;

public class BottleChatActivity extends FriendChatActivity {
    Bottle bottle;
    
    public void onCreate(Bundle savedInstanceState) {
        messageType = "700";
        bottle = (Bottle)getIntent().getSerializableExtra(Bottle.class.getSimpleName());
        super.onCreate(savedInstanceState);
    }
    
    public String[] getIncludedMsgTypes() {
        String[] types = {"700"};
        return types;
    }
    
    public int getMenuIcon() {
        return R.drawable.bar_menu_addfriend;
    }
    
    public void onMessageReceived(cn.cloudartisan.crius.client.model.Message message) {
        if("700".equals(message.getType())) {
            cn.cloudartisan.crius.bean.Message msg = MessageUtil.transform(message);
            lastMessage = MessageDBManager.getManager().queryMessageById(msg.gid);
            messageList.add(msg);
            adapter.notifyDataSetChanged();
            chatListView.setSelection(chatListView.getBottom());
            MessageDBManager.getManager().updateStatus(msg.gid, "1");
        }
        super.onMessageReceived(message);
    }
    
    public MessageItemSource getMessageSource() {
        return bottle;
    }
    
    public void onSendContent(String content) {
        bottle.content = null;
        apiParams.put("content", content);
        super.onSendContent(JSON.toJSONString(apiParams));
    }
    
    public void submitToSendQueue(String context, String fileName, String fileType) {
        JSONObject json = JSON.parseObject(context);
        json.put("bottle", bottle);
        context = json.toJSONString();
        super.submitToSendQueue(context, fileName, fileType);
    }
}
