/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.client.model.ReplyBody;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.FriendListPanel;
import cn.cloudartisan.crius.component.SortSideBar;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.network.ContactsRequester;
import cn.cloudartisan.crius.ui.HomeActivity;
import cn.cloudartisan.crius.ui.base.CIMMonitorFragment;

import java.util.Arrays;

public class ContactFragment extends CIMMonitorFragment implements View.OnClickListener, SortSideBar.OnTouchCharChangedListener, FriendListPanel.LoadFriendListener, ContactsRequester.OnLoadCompletedListener {
    public static final String ACTION_DELETE_CONTACTS = "com.farsunset.cim.DELETE_CONTACTS";
    ContactsRequester contactsRequester;
    ContactFragment.DeleteContactsReceiver deleteContactsReceiver;
    protected FriendListPanel friendListPanel;
    TextView hasnoFriendTips;
    User self;
    SortSideBar sideBar;
    TextView tabNewCountLabel;
    TextView unreadCountLabel;
    public static String[] types = new String[] {"100", "102", "105"};
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_contact, container, false);
        return root;
    }
    
    public void onResume() {
        super.onResume();
        friendListPanel.loadFriends(this);
        showNewMsgLable();
    }
    
    private void showNewMsgLable() {
        long newCount = MessageDBManager.getManager().countNewMessage(types);

        if(newCount > 0) {
            unreadCountLabel.setVisibility(View.VISIBLE);
            tabNewCountLabel.setVisibility(View.VISIBLE);
            unreadCountLabel.setText(String.valueOf(newCount));
            tabNewCountLabel.setText(String.valueOf(newCount));

        }else {
            unreadCountLabel.setVisibility(View.GONE);
            tabNewCountLabel.setVisibility(View.GONE);
        }
    }
    
    public void onViewCreated(View view, Bundle savedInstanceState) {
        sideBar = (SortSideBar)findViewById(R.id.sidrbar);
        sideBar.setTextView((TextView)findViewById(R.id.dialog));
        sideBar.setOnTouchCharChangedListener(this);
        friendListPanel = (FriendListPanel)view.findViewById(R.id.friendList);
        friendListPanel.findViewById(R.id.newFriendItem).setOnClickListener(this);
        friendListPanel.findViewById(R.id.groupItem).setOnClickListener(this);
        friendListPanel.findViewById(R.id.publicItem).setOnClickListener(this);
        View tabView = ((HomeActivity)getActivity()).getTrendDashboard();
        tabNewCountLabel = (TextView)tabView.findViewById(R.id.badge_unread_count);
        unreadCountLabel = (TextView)friendListPanel.findViewById(R.id.new_msg_count_label);
        super.onViewCreated(view, savedInstanceState);
    }
    
    public void onAttach(Activity activity) {
        super.onDetach();
        self = Global.getCurrentUser();
        contactsRequester = new ContactsRequester(getActivity(), this);
        contactsRequester.execute(self.getAccount());
        super.onAttach(activity);
        deleteContactsReceiver = new ContactFragment.DeleteContactsReceiver();
        getActivity().registerReceiver(deleteContactsReceiver, deleteContactsReceiver.getIntentFilter());
    }
    
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(deleteContactsReceiver);
    }
    
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()) {
            case R.id.groupItem:
            {
                intent.setClass(getActivity(), GroupListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.publicItem:
            {
                intent.setClass(getActivity(), PubAccountListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.newFriendItem:
            {
                intent.setClass(getActivity(), ContactsVerifyActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.newFriendIcon:
            case R.id.label_contacts_new_friend:
            {
                break;
            }
        }
    }
    
    public void onMessageReceived(Message msg) {
        if(Arrays.asList(types).contains(msg.getType())) {
            showNewMsgLable();
        }
        if(("101".equals(msg.getType())) || ("109".equals(msg.getType()))) {
            friendListPanel.loadFriends(this);
        }
    }
    
    public void onReplyReceived(ReplyBody reply) {
        if(reply.getKey().equals(CIMConstant.RequestKey.CLIENT_BIND)) {
            if(reply.getCode().equals(CIMConstant.ReturnCode.CODE_200)) {
                contactsRequester.execute(self.getAccount());
            }
        }
    }
    
    public void onLoadCompleted(String url) {
        if(URLConstant.FRIEND_LIST_URL.equals(url)) {
            friendListPanel.loadFriends(this);
        }
    }
    
    public void completed(int count) {
    }
    
    public void onCharChanged(String s) {
        int position = friendListPanel.adapter.getPositionForSection(s.charAt(0x0));
        if(position != -0x1) {
            friendListPanel.setSelection((position + 0x1));
        }
    }
    public class DeleteContactsReceiver
            extends BroadcastReceiver
    {
        public DeleteContactsReceiver() {}

        public IntentFilter getIntentFilter()
        {
            IntentFilter localIntentFilter = new IntentFilter();
            localIntentFilter.addAction("com.farsunset.cim.DELETE_CONTACTS");
            return localIntentFilter;
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            if ("com.farsunset.cim.DELETE_CONTACTS".equals(paramIntent.getAction())) {
                ContactFragment.this.friendListPanel.loadFriends(ContactFragment.this);
            }
        }
    }
}
