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
import cn.cloudartisan.crius.app.LvxinApplication;
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.MessageItemSource;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.HashMap;

public class Type103MessageParser extends MessageParser {
    
    public String getMessagePreview(Message msg) {
        JSONObject json = JSON.parseObject(msg.content);
        StringBuffer sb = new StringBuffer();
        sb.append(LvxinApplication.getInstance().getString(R.string.tip_agree_joingroup,json.getString("targetUserName"), json.getString("targetGroupName")));
        return sb.toString();
    }
    
    public void encodeContent(HashMap<String, Object> inputData) {
        HashMap<String, String> contentData = new HashMap<String, String>();
        Group group= (Group)inputData.get("group");
        User user=(User)inputData.get("user");
        contentData.put("targetUserAccount", user.getAccount());
        contentData.put("targetUserName", user.getName());
        contentData.put("targetGroupId", group.groupId);
        contentData.put("targetGroupName", group.name);
        contentData.put("targetGroupFounder", group.founder);
        contentData.put("targetGroupSummary", group.summary);
        contentData.put("targetGroupCategory", group.category);
        ((Message)inputData.get("message")).content = JSON.toJSONString(contentData);
    }
    
    public MessageItemSource decodeContent(Message msg) {
        Group targetGroup = new Group();
        JSONObject json = JSON.parseObject(msg.content);
        targetGroup.groupId = json.getString("targetGroupId");
        targetGroup.name = json.getString("targetGroupName");
        targetGroup.founder = json.getString("targetGroupFounder");
        targetGroup.summary = json.getString("targetGroupSummary");
        targetGroup.category = json.getString("targetGroupCategory");
        return targetGroup;
    }
    
    public void displayInSysMsgListView(SystemMsgListViewAdapter.MessageViewHolder holder, Message message) {
        Group group = (Group)decodeContent(message);
        holder.name.setVisibility(View.GONE);
        holder.headImageView.load(FileURLBuilder.getGroupIconUrl(group.groupId), R.drawable.grouphead_normal);
        holder.content.setText(getMessagePreview(message));
    }
}