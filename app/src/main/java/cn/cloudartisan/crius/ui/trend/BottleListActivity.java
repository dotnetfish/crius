/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.trend;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.BottleListAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Bottle;
import cn.cloudartisan.crius.bean.ChatItem;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.db.BottleDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.widget.CustomDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BottleListActivity extends CIMMonitorActivity implements AdapterView.OnItemClickListener, CustomDialog.OnOperationListener, AdapterView.OnItemLongClickListener {
    protected BottleListAdapter adapter;
    CustomDialog customDialog;
    private List<Bottle> dataList = new ArrayList();
    HashMap<String, Object> deleteParams = new HashMap();
    ListView messageListView;
    User self;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        self = Global.getCurrentUser();
        messageListView = (ListView)findViewById(R.id.messageListView);
        messageListView.setOnItemClickListener(this);
        messageListView.setOnItemLongClickListener(this);
        customDialog = new CustomDialog(this);
        customDialog.setOperationListener(this);
        customDialog.setTitle(R.string.common_delete);
        customDialog.setMessage(getString(R.string.tip_clear_bottle));
        customDialog.setButtonsText(getString(R.string.common_cancel), getString(R.string.common_confirm));
    }
    
    public void onResume() {
        super.onResume();
        dataList.clear();
        dataList.addAll(BottleDBManager.getManager().queryBottleList());
        adapter = new BottleListAdapter(this, dataList);
        messageListView.setAdapter(adapter);
    }
    
    public void onMessageReceived(Message msg) {
        if(("700".equals(msg.getType())) || ("701".equals(msg.getType()))) {
            dataList.clear();
            dataList.addAll(BottleDBManager.getManager().queryBottleList());
            adapter.notifyDataSetChanged();
        }
    }
    
    public void onClick(View view) {
    }
    
    public int getConentLayout() {
        return R.layout.activity_circle_message;
    }
    
    public int getActionBarTitle() {
        return R.string.label_bottle_list;
    }
    
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BottleChatActivity.class);
        intent.putExtra(Bottle.class.getSimpleName(), (Serializable)dataList.get(position));
        startActivity(intent);
    }
    
    public void onLeftClick() {
        customDialog.dismiss();
    }
    
    public void onRightClick() {
        customDialog.dismiss();
        Bottle target = (Bottle)customDialog.getTag();
        BottleDBManager.getManager().deleteById(target.gid);
        if(self.getAccount().equals(target.sender)) {
            MessageDBManager.getManager().deleteBySenderAndType(target.receiver, "700");
            apiParams.put("receiver", target.receiver);
        } else {
            apiParams.put("receiver", target.sender);
            MessageDBManager.getManager().deleteBySenderAndType(target.sender, "700");
        }
        deleteParams.put("gid", target.gid);
        HttpAPIRequester.execute(deleteParams,  URLConstant.BOTTLE_DELETE_URL);
        apiParams.put("content", target.gid);
        apiParams.put("type", "701");
        HttpAPIRequester.execute(apiParams, URLConstant. MESSAGE_SEND_URL);
        dataList.remove(target);
        adapter.notifyDataSetChanged();
        Intent intent = new Intent("com.farsunset.cim.DELETE_APPEND");
        intent.putExtra(ChatItem.NAME, new ChatItem((Bottle)customDialog.getTag()));
        sendBroadcast(intent);
    }
    
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        customDialog.setTag(dataList.get(position));
        customDialog.show();
        return true;
    }
}