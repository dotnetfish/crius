/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.component.ChatListView;
import cn.cloudartisan.crius.component.FromMessageView;
import cn.cloudartisan.crius.component.ToMessageView;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewAdapter extends BaseAdapter implements ChatListView.OnMessageDeleteListenter {
    private static final int VIEW_TAG = 748562824;
    protected Context context;
    long lastTime;
    protected List<Message> list=new ArrayList<>();
    MessageItemSource others;
    User self;
    
    public ChatListViewAdapter(Context context, List<Message> list, MessageItemSource friend) {
        this.context = context;
        self = Global.getCurrentUser();
        others = friend;
       this.list = list;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Message getItem(int position) {
        return (Message)list.get(position);
    }
    
    public long getItemId(int position) {
        return ((Message)list.get(position)).gid.hashCode();
    }
    
    public int getItemViewType(int position) {
        Message msg = getItem(position);
        String type = msg.fileType;
        if(StringUtils.isEmpty(type)) {
             type = "0";
        }
        boolean isSelf = self.getAccount().equals(msg.sender);

        return (isSelf ? Integer.parseInt(type) : Integer.parseInt(type) + 5);
    }
    
    public int getViewTypeCount() {
        return 10;
    }
    
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private int getViewId(boolean isSelf,String filetype){
        if(isSelf){
            switch (filetype){
                default:
                case "0":
                    return  R.layout.item_chat_record_to_text;
                case "1":
                    return  R.layout.item_chat_record_to_image;
                case "2":
                    return  R.layout.item_chat_record_to_voice;
                case "3":
                    return  R.layout.item_chat_record_to_file;
                case "4":
                    return  R.layout.item_chat_record_to_map;
            }
        }else {
            switch (filetype){
                default:
                case "0":
                    return  R.layout.item_chat_record_from_text;
                case "1":
                    return  R.layout.item_chat_record_from_image;
                case "2":
                    return  R.layout.item_chat_record_from_voice;
                case "3":
                    return  R.layout.item_chat_record_from_file;
                case "4":
                    return  R.layout.item_chat_record_from_map;
            }
        }
    }
    
    @TargetApi(Build.VERSION_CODES.DONUT)
    public View getView(int position, View chatItemView, ViewGroup parent) {
        Message message = getItem(position);
        ChatListViewAdapter.ViewHolder viewHolder ;
        boolean isSelf = self.getAccount().equals(message.sender);
        if(chatItemView == null) {
            chatItemView=LayoutInflater.from(context).inflate(getViewId(isSelf,message.fileType),null);
            viewHolder = new ChatListViewAdapter.ViewHolder();
            viewHolder.myChatItemView = (ToMessageView)chatItemView.findViewById(R.id.to_message_view);
            viewHolder.othersChatItemView = (FromMessageView)chatItemView.findViewById(R.id.from_message_view);
            viewHolder.datetime = (TextView)chatItemView.findViewById(R.id.datetime);
            chatItemView.setTag(VIEW_TAG, viewHolder);
        } else {
            viewHolder = (ChatListViewAdapter.ViewHolder)chatItemView.getTag(VIEW_TAG);
        }

        if("3".equals(message.type)){
            Message tempMsg = MessageDBManager.getManager().queryMessageById(message.gid);
            others =JSON.parseObject(JSON.parseObject(tempMsg.content).getString("user"), Friend.class);
            message.content = JSON.parseObject(tempMsg.content).getString("content");
        }
        if(isSelf) {
            viewHolder.myChatItemView.displayMessage(message, self, others);
            viewHolder.myChatItemView.setOnMessageDeleteListenter(this);
        } else {
            viewHolder.othersChatItemView.displayMessage(message, self, others);
            viewHolder.othersChatItemView.setOnMessageDeleteListenter(this);
        }
        if((position == 0) || (position > 0) && ((Long.parseLong(message.createTime) - Long.parseLong(getItem((position - 0x1)).createTime)) >= 0x1770)) {
            viewHolder.datetime.setText(AppTools.howTimeAgo(context, Long.parseLong(message.createTime)));
            viewHolder.datetime.setVisibility(View.VISIBLE);
            return chatItemView;
        }
        viewHolder.datetime.setVisibility(View.GONE);
        return chatItemView;
    }
    
    public List getList() {
        return list;
    }
    
    public void setList(List<Message> list) {
        this.list = list;
    }
    
    class ViewHolder {
        TextView datetime;
        ToMessageView myChatItemView;
        FromMessageView othersChatItemView;
    }
    
    public void onDelete(Message msg) {
        MessageDBManager.getManager().deleteById(msg.gid);
        list.remove(msg);
        notifyDataSetChanged();
        Intent intent = new Intent("com.farsunset.cim.DELETE_APPEND");
        intent.putExtra(ChatItem.NAME, new ChatItem(msg, others));
        CriusApplication.getInstance().sendBroadcast(intent);
    }
}
