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
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.SystemMsg;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.ui.contact.ContactsVerifyActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.HashMap;

public class Type102MessageParser extends MessageParser {
    
    public void encodeContent(HashMap inputData) {
        HashMap<String, String> contentData = new HashMap<String, String>();
        contentData.put("sourceAccount", ((User)inputData.get("user")).getAccount());
        contentData.put("sourceName",( (User)inputData.get("user")).getName());
        contentData.put("requestMsg", String.valueOf(inputData.get("requestMsg")));
        contentData.put("targetGroupId", String.valueOf(inputData.get("groupId")));
        contentData.put("targetGroupName", String.valueOf(inputData.get("groupName")));
        ((Message)inputData.get("message")).content = JSON.toJSONString(contentData);
    }
    
    public void displayInSysMsgListView(final SystemMsgListViewAdapter.MessageViewHolder holder, final Message message) {
        holder.content.setVisibility(View.VISIBLE);
        holder.content.setText(getMessagePreview(message));
        JSONObject json = JSON.parseObject(message.content);
        String handleResult = json.getString(SystemMsg.HANDLE_RESULT);
        holder.name.setVisibility(View.GONE);
        holder.name.setText(json.getString("sourceName"));
        holder.result_show.setVisibility(View.VISIBLE);
        holder.handleButton.setVisibility(View.GONE);
        if(handleResult == null) {
            holder.handleButton.setVisibility(View.VISIBLE);
            holder.handleButton.setOnClickListener(new View.OnClickListener() {
                

                public void onClick(View arg0) {
                    ((ContactsVerifyActivity)holder.content.getContext()).onHandleButtonClick(message);
                }
            });
            holder.result_show.setVisibility(View.GONE);
        } else if(handleResult.equals(SystemMsg.RESULT_AGREE)) {
            holder.result_show.setText(R.string.common_has_agree);
            holder.result_show.setTextColor(CriusApplication.getInstance().getResources().getColor(R.color.theme_green));
        } else if(handleResult.equals(SystemMsg.RESULT_IGNORE)) {
            holder.result_show.setText(R.string.common_has_ignore);
            holder.result_show.setTextColor(CriusApplication.getInstance().getResources().getColor(R.color.text_grey));
        } else if(handleResult.equals(SystemMsg.RESULT_REFUSE)) {
            holder.result_show.setTextColor(CriusApplication.getInstance().getResources().getColor(R.color.red));
            holder.result_show.setText(R.string.common_has_refuse);
        }
        holder.headImageView.load(FileURLBuilder.getUserIconUrl(json.getString("sourceAccount")), R.drawable.icon_head_default);
    }
    
    public Object decodeContent(Message message) {
        return null;
    }
    
    public String getMessagePreview(Message message) {
        StringBuffer sb = new StringBuffer();
        JSONObject json = JSON.parseObject(message.content);
        sb.append(CriusApplication.getInstance().getString(R.string.tip_request_joingroup, new Object[] {json.getString("sourceName"), json.getString("targetGroupName")})).append(")").append(json.getString("requestMsg") == null ? "" : CriusApplication.getInstance().getString(R.string.tip_request_verify, new Object[] {json.getString("requestMsg")}));
        return sb.toString();
    }
}
