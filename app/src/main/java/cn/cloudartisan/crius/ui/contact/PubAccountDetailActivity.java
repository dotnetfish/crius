/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.db.PublicAccountDBManager;
import cn.cloudartisan.crius.db.PublicMenuDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.ui.chat.MMWebViewActivity;
import cn.cloudartisan.crius.ui.chat.PubAccountChatActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;
import java.util.Map;

public class PubAccountDetailActivity extends BaseActivity implements HttpAPIResponser {
    WebImageView icon;
    PublicAccount publicAccount;
    HttpAPIRequester requester;
    User self;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        publicAccount = (PublicAccount)getIntent().getSerializableExtra(PublicAccount.NAME);
        self = Global.getCurrentUser();
        requester = new HttpAPIRequester(this);
        icon = (WebImageView)findViewById(R.id.icon);
        icon.setPopuShowAble();
        ((TextView)findViewById(R.id.pubaccount)).setText(getString(R.string.label_pubaccount, new Object[] {publicAccount.account}));
        ((TextView)findViewById(R.id.name)).setText(publicAccount.name);
        ((TextView)findViewById(R.id.summary)).setText(publicAccount.description);
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.displayer(new RoundedBitmapDisplayer(0x3e7));
        ImageLoader.getInstance().displayImage(FileURLBuilder.getPubAccountLogoUrl(publicAccount.account), icon, builder.build());
        findViewById(R.id.item_homepage).setOnClickListener(this);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.subscribeButton:
            {
                apiParams.put("account", self.getAccount());
                apiParams.put("publicAccount", publicAccount.account);
                requester.execute(null, null, URLConstant.PUBACCOUNT_SUBSCRIBE_URL);
                return;
            }
            case R.id.enterButton:
            {
                Intent intent1 = new Intent(this, PubAccountChatActivity.class);
                intent1.putExtra(PublicAccount.NAME, publicAccount);
                startActivity(intent1);
                return;
            }
            case R.id.item_homepage:
            {
                if(!TextUtils.isEmpty(publicAccount.link)) {
                    Intent intent = new Intent(this, MMWebViewActivity.class);
                    intent.putExtra("url", publicAccount.link);
                    startActivity(intent);
                    break;
                }
            }
        }
    }
    
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        if((CIMConstant.ReturnCode.CODE_200.equals(code)) && (url.equals(URLConstant.PUBACCOUNT_MENU_URL))) {
            hideProgressDialog();
            Message msg = new Message();
            msg.setTimestamp(System.currentTimeMillis());
            msg.setMid(StringUtils.getUUID());
            msg.setType("201");
            msg.setSender(publicAccount.account);
            msg.setReceiver(self.getAccount());
            msg.setFileType("0");
            PubTextMessage textMsg = new PubTextMessage();
            textMsg.content = publicAccount.greet;
            msg.setContent(JSON.toJSONString(textMsg));
            Intent intent = new Intent("com.farsunset.cim.MESSAGE_RECEIVED");
            intent.putExtra("message", msg);
            CriusApplication.getInstance().sendBroadcast(intent);
            Intent intent1 = new Intent(this, PubAccountChatActivity.class);
            intent1.putExtra(PublicAccount.NAME, publicAccount);
            startActivity(intent1);
            PublicAccountDBManager.getManager().savePublicAccount(publicAccount);
            PublicMenuDBManager.getManager().savePublicMenus(list);
            finish();
        }
        if((CIMConstant.ReturnCode.CODE_200.equals(code)) && (url.equals(URLConstant.PUBACCOUNT_SUBSCRIBE_URL))) {
            apiParams.put("account", publicAccount.account);
            requester.execute(null, new TypeReference<PubAccountDetailActivity>() { }.getType(), URLConstant.PUBACCOUNT_MENU_URL);
        }
        if((CIMConstant.ReturnCode.CODE_200.equals(code)) && (url.equals(URLConstant.PUBACCOUNT_UNSUBSCRIBE_URL))) {
            MessageDBManager.getManager().deleteBySenderAndType(publicAccount.account, new String[] {"200", "201"});
            PublicAccountDBManager.getManager().deletePublicAccount(publicAccount.account);
            PublicMenuDBManager.getManager().deleteByAccount(publicAccount.account);
            Intent intent = new Intent("com.farsunset.cim.DELETE_APPEND");
            intent.putExtra(ChatItem.NAME, new ChatItem(publicAccount));
            sendBroadcast(intent);
            hideProgressDialog();
            finish();
        }
    }
    
    public void onFailed(Exception e) {
        hideProgressDialog();
    }
    
    public Map getRequestParams(String code) {
        return apiParams;
    }
    
    public void onRequest() {
        showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.common_handle)}));
    }
    
    public int getConentLayout() {
        return R.layout.activity_pubaccount_detail;
    }
    
    public int getActionBarTitle() {
        return 0x0;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        PublicAccount target = PublicAccountDBManager.getManager().queryPublicAccount(publicAccount.account);
        if(target == null) {
            findViewById(R.id.subscribeButton).setVisibility(View.VISIBLE);
            findViewById(R.id.subscribeButton).setOnClickListener(this);
            findViewById(R.id.enterButton).setVisibility(View.GONE);
            setTitle(R.string.label_detail);
        } else {
            findViewById(R.id.subscribeButton).setVisibility(View.GONE);
            findViewById(R.id.enterButton).setOnClickListener(this);
            findViewById(R.id.enterButton).setVisibility(View.VISIBLE);
            getMenuInflater().inflate(R.menu.pubaccount_detailed, menu);
            setTitle(target.name);
        }
        return super.onCreateOptionsMenu(menu);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.bar_menu_unsubscribe:
            {
                apiParams.put("account", self.getAccount());
                apiParams.put("publicAccount", publicAccount.account);
                requester.execute(new TypeReference<PubAccountDetailActivity>() {}.getType(), null, URLConstant.PUBACCOUNT_UNSUBSCRIBE_URL);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
