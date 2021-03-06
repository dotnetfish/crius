/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.network;

import android.app.Activity;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.db.FriendDBManager;
import cn.cloudartisan.crius.db.GroupDBManager;
import cn.cloudartisan.crius.db.PublicAccountDBManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsRequester extends HttpAPIRequester implements HttpAPIResponser {
    Activity activity;
    ContactsRequester.OnLoadCompletedListener onLoadCompletedListener;
    public HashMap<String, Object> apiParams = new HashMap();
    
    public ContactsRequester(Activity context, ContactsRequester.OnLoadCompletedListener ol) {
        activity = context;
        onLoadCompletedListener = ol;
        responser = this;
    }
    
    public void execute(String account) {
        apiParams.put("account", account);
        if(FriendDBManager.getManager().queryFriendList().isEmpty()) {
            execute(null, new TypeReference<ContactsRequester>() {}.getType(), URLConstant.FRIEND_LIST_URL);
        }
        if(GroupDBManager.getManager().queryGroupList().isEmpty()) {
            execute(null, new TypeReference<ContactsRequester>() {
                   }.getType(),URLConstant. GROUP_LIST_URL);
        }
        if(PublicAccountDBManager.getManager().queryPublicAccountList().isEmpty()) {
            execute(null, new TypeReference<ContactsRequester>() {}.getType(), URLConstant.PUBACCOUNT_LIST_URL);
        }
    }
    
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        if(CIMConstant.ReturnCode.CODE_200.equals(code)) {
            if(URLConstant.FRIEND_LIST_URL.equals(url)) {
                FriendDBManager.getManager().saveFriends(list);
            }
            if(URLConstant.GROUP_LIST_URL.equals(url)) {
                GroupDBManager.getManager().saveGroups(list);
            }
            if(URLConstant.PUBACCOUNT_LIST_URL.equals(url)) {
                PublicAccountDBManager.getManager().savePublicAccounts(list);
            }
        }
        onLoadCompletedListener.onLoadCompleted(url);
    }
    
    public void onFailed(Exception e) {
    }
    
    public Map getRequestParams(String code) {
        return apiParams;
    }
    
    public void onRequest() {
    }
    public  interface OnLoadCompletedListener
    {
        void onLoadCompleted(String paramString);
    }
}
