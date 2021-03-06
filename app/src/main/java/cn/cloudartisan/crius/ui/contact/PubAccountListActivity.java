/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.PubAccountListViewAdapter;
import cn.cloudartisan.crius.bean.PublicAccount;
import cn.cloudartisan.crius.db.PublicAccountDBManager;
import cn.cloudartisan.crius.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class PubAccountListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    PubAccountListViewAdapter adapter;
    List<PublicAccount> list=new ArrayList<>();
    protected ListView listview;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        list.addAll(PublicAccountDBManager.getManager().queryPublicAccountList());
        listview = (ListView)findViewById(R.id.listview);
        adapter = new PubAccountListViewAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }
    
    public int getConentLayout() {
        return R.layout.activity_pubaccount_list;
    }
    
    public int getActionBarTitle() {
        return R.string.label_contacts_public;
    }
    
    public void onClick(View v) {
    }
    
    public void onResume() {
        super.onResume();
        list.clear();
        list.addAll(PublicAccountDBManager.getManager().queryPublicAccountList());
        adapter.notifyDataSetChanged();
    }
    
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, PubAccountDetailActivity.class);
        intent.putExtra(PublicAccount.NAME, adapter.getItem(position));
        startActivity(intent);
    }
}
