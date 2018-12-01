/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.message.parser;

import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.SystemMsgListViewAdapter;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.HashMap;

public class Type107MessageParser extends MessageParser {
    
    public String getMessagePreview(Message msg) {
        JSONObject json = JSON.parseObject(msg.content);
        StringBuffer sb = new StringBuffer();
        sb.append(CriusApplication.getInstance().getString(R.string.tip_Kickout_group_completed, new Object[] {json.getString("groupName")}));
        return sb.toString();
    }
    
    public void encodeContent(HashMap<String, Object> inputData) {
        HashMap<String, String> contentData = new HashMap<String, String>();
        contentData.put("groupId", inputData.get("groupId").toString());
        contentData.put("groupName", inputData.get("groupName").toString());
        ((Message)inputData.get("message")) .content = JSON.toJSONString(contentData);
    }
    
    public Group decodeContent(Message msg) {
        Group targetGroup = new Group();
        JSONObject json = JSON.parseObject(msg.content);
        targetGroup.groupId = json.getString("groupId");
        targetGroup.name = json.getString("groupName");
        return targetGroup;
    }
    
    public void displayInSysMsgListView(SystemMsgListViewAdapter.MessageViewHolder holder, Message message) {
        Group group = decodeContent(message);
        holder.name.setVisibility(View.GONE);
        holder.headImageView.load(FileURLBuilder.getGroupIconUrl(group.groupId), R.drawable.grouphead_normal);
        holder.content.setText(getMessagePreview(message));
    }
}
