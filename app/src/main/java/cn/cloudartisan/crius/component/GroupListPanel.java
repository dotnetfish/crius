/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.cloudartisan.crius.adapter.GroupListViewAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.db.GroupDBManager;
import cn.cloudartisan.crius.ui.chat.GroupChatActivity;

import java.util.ArrayList;

public class GroupListPanel extends ListView implements AdapterView.OnItemClickListener {
    protected GroupListViewAdapter adapter;
    Context context;
    protected Group group;
    private ArrayList<Group> list;
    
    public GroupListPanel(Context paramContext) {
        super(paramContext);
        init(paramContext);
    }
    
    public GroupListPanel(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
    }
    
    public void init(Context paramContext) {
        context = paramContext;
        list = new ArrayList();
        adapter = new GroupListViewAdapter(context, list);
        setAdapter(adapter);
        setOnItemClickListener(this);
    }
    
    public void addGroup(Group group) {
        list.add(group);
    }
    
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(context, GroupChatActivity.class);
        intent.putExtra("group", adapter.getItem(position));
        Group group=list.get(position);
        intent.putExtra("othersId",group.groupId);
        intent.putExtra("othersName", group.name);
        context.startActivity(intent);
    }
    
    public void loadGroups(int index) {
        list.clear();
        if(index == 0) {
            toogleMyCreatedGroup();
        } else {
            toogleMyJoinGroup();
        }
        adapter.notifyDataSetChanged();
    }
    
    public void toogleMyCreatedGroup() {
        list.clear();
        list.addAll(GroupDBManager.getManager().queryMyCreatedGroupList(Global.getCurrentUser().getAccount()));
        adapter.notifyDataSetChanged();
    }
    
    public void toogleMyJoinGroup() {
        list.clear();
        list.addAll(GroupDBManager.getManager().queryJoinList(Global.getCurrentUser().getAccount()));
        adapter.notifyDataSetChanged();
    }
}
