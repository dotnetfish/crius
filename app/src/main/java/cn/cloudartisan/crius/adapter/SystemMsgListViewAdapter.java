/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.SystemMsg;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.message.parser.MessageParserFactory;
import cn.cloudartisan.crius.util.AppTools;

import java.util.ArrayList;
import java.util.List;

public class SystemMsgListViewAdapter extends BaseAdapter {
    protected Context _context;
    protected List<Message> list =new ArrayList<>();
    
    public SystemMsgListViewAdapter(Context context, List<Message> list) {
        _context = context;
        this.list = list;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Message getItem(int position) {
        return list.get(position);
    }
    
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    
    public long getItemId(int position) {
        return 0x0;
    }
    
    public View getView(int position, View chatItemView, ViewGroup parent) {
        Message msg = getItem(position);
        SystemMsgListViewAdapter.MessageViewHolder holder = chatItemView == null?
                new SystemMsgListViewAdapter.MessageViewHolder():(SystemMsgListViewAdapter.MessageViewHolder)chatItemView.getTag();

        if(chatItemView == null) {
             chatItemView = LayoutInflater.from(_context).inflate(R.layout.item_system_message, null);
            holder.textMsgType = (TextView)chatItemView.findViewById(R.id.textMsgType);
            holder.time = (TextView)chatItemView.findViewById(R.id.time);
            holder.headImageView = (WebImageView)chatItemView.findViewById(R.id.headImageView);
            holder.name = (TextView)chatItemView.findViewById(R.id.name);
            holder.content = (TextView)chatItemView.findViewById(R.id.content);
            chatItemView.setTag(holder);
        }
        holder.textMsgType.setText(SystemMsg.getTypeText(msg.type));
        holder.time.setText(AppTools.getDateTimeString(Long.valueOf(msg.createTime).longValue()));
        MessageParserFactory.getFactory().getMessageParser(msg.type).displayInSysMsgListView(holder, msg);
        if("0".equals(msg.status)) {
            MessageDBManager.getManager().updateStatus(msg.gid, "1");
        }
        return chatItemView;
    }
    
    public List getList() {
        return list;
    }
    
    public void setList(List<Message> list) {
        this.list = list;
    }

    public static class MessageViewHolder
    {
        public TextView content;
        public Button handleButton;
        public WebImageView headImageView;
        public TextView name;
        public TextView result_show;
        public TextView textMsgType;
        public TextView time;
    }
}
