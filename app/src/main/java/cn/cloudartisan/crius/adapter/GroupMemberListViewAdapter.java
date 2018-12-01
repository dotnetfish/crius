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
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberListViewAdapter extends BaseAdapter {
    protected Context context;
    String creator;
    protected List<Friend> list =new ArrayList<>();
    
    public GroupMemberListViewAdapter(Context c, List<Friend> list, String creator) {
        context = c;
        this.list = list;
        this.creator = creator;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Friend getItem(int position) {
        return (Friend)list.get(position);
    }
    
    public long getItemId(int position) {
        return 0x0;
    }
    
    public View getView(int index, View friendItemView, ViewGroup parent) {
        Friend target = getItem(index);
        if(friendItemView == null) {
            friendItemView = LayoutInflater.from(context).inflate(R.layout.item_group_member, null);
        }
        TextView name = (TextView)friendItemView.findViewById(R.id.name);
        WebImageView headImage = (WebImageView)friendItemView.findViewById(R.id.headImageView);
        headImage.load(FileURLBuilder.getUserIconUrl(target.account), R.drawable.icon_head_default);
        name.setText(target.name);
        if(target.account.equals(creator)) {
            friendItemView.findViewById(R.id.createor).setVisibility(View.VISIBLE);
            return friendItemView;
        }
        friendItemView.findViewById(R.id.createor).setVisibility(View.GONE);
        return friendItemView;
    }
}