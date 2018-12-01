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
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.ArrayList;
import java.util.List;

public class GroupListViewAdapter extends BaseAdapter {
    protected Context context;
    protected List<Group> list=new ArrayList<>();
    
    public GroupListViewAdapter(Context c, List<Group> list) {
        context = c;
       this.list = list;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Group getItem(int position) {
        return list.get(position);
    }
    
    public long getItemId(int position) {
        return 0x0;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_contact_group, null);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        name.setText(group.name);
        ((WebImageView)convertView.findViewById(R.id.groupIcon)).load(FileURLBuilder.getGroupIconUrl(group.groupId), R.drawable.grouphead_normal);
        return convertView;
    }
}
