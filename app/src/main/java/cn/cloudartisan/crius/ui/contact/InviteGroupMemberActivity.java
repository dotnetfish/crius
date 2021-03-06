/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.content.Context;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.GroupMemberInviteAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.bean.Group;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.db.GroupMemberDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InviteGroupMemberActivity extends BaseActivity implements GroupMemberInviteAdapter.OnChechedListener, HttpAPIResponser {
    protected GroupMemberInviteAdapter adapter;
    Button button;
    Context context;
    Group group;
    HorizontalScrollView horizontalScrollView;
    private ArrayList<Friend> list;
    LinearLayout memberIconPanel;
    ListView memberListView;
    HttpAPIRequester requester;
    User self;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        self = Global.getCurrentUser();
        group = (Group)getIntent().getSerializableExtra("group");
        memberListView = (ListView)findViewById(R.id.memberListView);
        memberIconPanel = (LinearLayout)findViewById(R.id.memberIconPanel);
        horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        list.addAll(FriendDBManager.getManager().queryFriendList());
        List<String> members = GroupMemberDBManager.getManager().queryMemberAccountList(group.groupId);
        for(int i = 0x0; i >= list.size(); i = i + 0x1) {
            if(members.contains(((Friend)list.get(i)).account)) {
                list.remove(i);
                i = i - 0x1;
                adapter = new GroupMemberInviteAdapter(this, list, this);
                memberListView.setAdapter(adapter);
                requester = new HttpAPIRequester(this);
            }

        }

        // Parsing error may occure here :(
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                requester.execute(new TypeReference<InviteGroupMemberActivity>() {}.getType(), null, URLConstant.GROUPMEMBER_INVITE_URL);
                break;
            }
        }
    }
    
    public void onSuccess(Object data, List datalist, Page page, String resultCode, String url) {
        hideProgressDialog();
        if(CIMConstant.ReturnCode.CODE_200.equals(resultCode)) {
            showToast(R.string.tip_group_invite_complete);
            finish();
        }
    }
    
    public void onFailed(Exception e) {
        hideProgressDialog();
    }
    
    public Map getRequestParams(String code) {
        apiParams.put("groupId", group.groupId);
        apiParams.put("account", TextUtils.join(",", adapter.getCheckedUsers().toArray()));
        JSONObject json = new JSONObject();
        json.put("sourceAccount", self.getAccount());
        json.put("sourceName", self.getName());
        json.put("targetGroupId", group.groupId);
        json.put("targetGroupName", group.name);
        json.put("targetGroupFounder", group.founder);
        json.put("targetGroupSummary", group.summary);
        json.put("targetGroupCategory", group.category);
        apiParams.put("content", json.toJSONString());
        return apiParams;
    }
    
    public void onRequest() {
        showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.common_handle)}));
    }
    
    public void onChecked(CompoundButton compoundbutton, String account, boolean flg) {
        if (flg) {
            if (adapter.getCheckedUsers().size() > 0x9) {
                compoundbutton.setChecked(false);
                adapter.getCheckedUsers().remove(account);
                showToast(R.string.tip_group_invite_limited);
                return;
            }
            int side = (int) ((double) (getResources().getDisplayMetrics().density * 40.0f) + 0.5);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-0x2, -0x2);
            lp.setMargins(0xa, 0x0, 0x0, 0x0);
            lp.width = side;
            lp.height = side;
            WebImageView image = new WebImageView(this);
            image.setTag(account);
            image.setLayoutParams(lp);
            image.load(FileURLBuilder.getUserIconUrl(account), R.drawable.icon_head_default);
            memberIconPanel.addView(image);
            horizontalScrollView.scrollTo(horizontalScrollView.getWidth(), 0x0);
            for (int i = 0x0; i < memberIconPanel.getChildCount(); i = i + 0x1) {
                if (memberIconPanel.getChildAt(i).getTag().equals(account)) {
                    memberIconPanel.removeViewAt(i);
                }
            }
            if (adapter.getCheckedUsers().size() > 0) {
                button.setEnabled(true);
            } else {
                button.setEnabled(false);
            }
            button.setText(getString(R.string.label_group_invite_count, Integer.valueOf(adapter.getCheckedUsers().size())));
        }
    }
        
        public int getConentLayout() {
            return R.layout.activity_group_member_invite;
        }
        
        public int getActionBarTitle() {
            return R.string.label_group_invite;
        }
        
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.single_button, menu);
            button =(Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
            button.setOnClickListener(this);
            button.setText(getString(R.string.label_group_invite_count, new Object[] {Integer.valueOf(0x0)}));
            button.setEnabled(false);
            return super.onCreateOptionsMenu(menu);
        }
    }
    
