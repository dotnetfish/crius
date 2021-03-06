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
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.HashMap;

public class Type106MessageParser extends Type103MessageParser {
    
    public String getMessagePreview(Message msg) {
        JSONObject json = JSON.parseObject(msg.content);
        StringBuffer sb = new StringBuffer();
        sb.append(CriusApplication.getInstance().getString(R.string.tip_agree_invitegroup, json.getString("targetUserName"), json.getString("targetGroupName")));
        return sb.toString();
    }
    
    public void encodeContent(HashMap<String, Object> inputData) {
        HashMap<String, String> contentData = new HashMap<String, String>();
        contentData.put("targetUserAccount", ((User)inputData.get("user")).getAccount());
        contentData.put("targetUserName", ((User)inputData.get("user")).getName());
        contentData.put("targetGroupId", inputData.get("targetGroupId").toString());
        contentData.put("targetGroupName", inputData.get("targetGroupName").toString());
        ((Message)inputData.get("message")).content = JSON.toJSONString(contentData);
    }
    
    public Friend decodeContent(Message msg) {
        Friend f = new Friend();
        JSONObject json = JSON.parseObject(msg.content);
        f.account = json.getString("targetUserAccount");
        f.name = json.getString("targetUserName");
        return f;
    }
    
    public void displayInSysMsgListView(SystemMsgListViewAdapter.MessageViewHolder holder, Message message) {
        Friend f = decodeContent(message);
        holder.name.setVisibility(View.GONE);
        holder.headImageView.load(FileURLBuilder.getUserIconUrl(f.account), R.drawable.grouphead_normal);
        holder.content.setText(getMessagePreview(message));
    }
}
