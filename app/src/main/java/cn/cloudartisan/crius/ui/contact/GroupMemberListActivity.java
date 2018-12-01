/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.GroupMemberListViewAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.db.GroupMemberDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupMemberListActivity extends BaseActivity implements CustomDialog.OnOperationListener, HttpAPIResponser, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    protected GroupMemberListViewAdapter adapter;
    Context context;
    CustomDialog customDialog;
    Group group;
    private ArrayList<Friend> list;
    ListView memberListView;
    HttpAPIRequester requester;
    User self;
    
    public void initComponents() {
        group = (Group)getIntent().getSerializableExtra("group");
        setDisplayHomeAsUpEnabled(true);
        memberListView = (ListView)findViewById(R.id.memberListView);
        adapter = new GroupMemberListViewAdapter(this, list, group.founder);
        memberListView.setAdapter(adapter);
        memberListView.setOnItemClickListener(this);
        memberListView.setOnItemLongClickListener(this);
        requester = new HttpAPIRequester(this);
        requester.execute(null, new TypeReference<GroupMemberListActivity>() { }.getType(), URLConstant.GROUPMEMBER_LIST_URL);
        customDialog = new CustomDialog(this);
        customDialog.setOperationListener(this);
        customDialog.setTitle(R.string.common_hint);
        customDialog.setMessage(getString(R.string.tip_kickout_group));
        customDialog.setButtonsText(getString(R.string.common_cancel), getString(R.string.common_confirm));
        self = Global.getCurrentUser();
    }
    
    public void onClick(View v) {
        v.getId();
    }
    
    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        Friend member = (Friend)list.get(index);
        Intent intent = new Intent(this, UserDetailActivity.class);
        intent.putExtra("user", member);
        startActivity(intent);
    }
    
    public void onSuccess(Object data, List datalist, Page page, String resultCode, String url) {
        hideProgressDialog();
        if(CIMConstant.ReturnCode.CODE_200.equals(resultCode)) {
            if(URLConstant.GROUPMEMBER_LIST_URL.equals(url)) {
                list.clear();
                list.addAll(datalist);
                adapter.notifyDataSetChanged();
            }
            if(URLConstant.GROUPMEMBER_GETOUT_URL.equals(url)) {
                list.remove(Integer.parseInt(apiParams.get("index").toString()));
                GroupMemberDBManager.getManager().delete(group.groupId, apiParams.get("account").toString());
                adapter.notifyDataSetChanged();
                showToast(R.string.tip_group_kickout_complete);
            }
        }
    }
    
    public void onFailed(Exception e) {
        hideProgressDialog();
    }
    
    public Map getRequestParams(String code) {
        apiParams.put("groupId", group.groupId);
        return apiParams;
    }
    
    public void onRequest() {
        if(!apiParams.containsKey("account")) {
            showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.common_load)}));
            return;
        }
        showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.common_handle)}));
    }
    
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        if((self.getAccount().equals(group.founder)) && (!self.getAccount().equals(((Friend)list.get(index)).account))) {
            customDialog.setTag(((Friend)list.get(index)).account);
            customDialog.show();
            apiParams.put("index", Integer.valueOf(index));
        }
        return true;
    }
    
    public void onLeftClick() {
        customDialog.dismiss();
    }
    
    public void onRightClick() {
        customDialog.dismiss();
        apiParams.put("account", customDialog.getTag().toString());
        JSONObject json = new JSONObject();
        json.put("groupId", group.groupId);
        json.put("groupName", group.name);
        apiParams.put("content", json.toJSONString());
        requester.execute(new TypeReference<GroupMemberListActivity>() {}.getType(), null, URLConstant.GROUPMEMBER_GETOUT_URL);
    }
    
    public int getConentLayout() {
        return R.layout.activity_group_member_list;
    }
    
    public int getActionBarTitle() {
        return R.string.label_group_members;
    }
}
