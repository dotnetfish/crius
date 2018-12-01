/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.contact;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.bean.MessageItemSource;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.message.request.RequestHandler;
import cn.cloudartisan.crius.message.request.RequestHandlerFactory;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class RequestHandleActivity extends BaseActivity implements ImageLoadingListener {
    WebImageView icon;
    Message message;
    RequestHandler messageHandler;
    HttpAPIRequester requester;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.ignoreButton).setOnClickListener(this);
        findViewById(R.id.agreeButton).setOnClickListener(this);
        findViewById(R.id.refuseButton).setOnClickListener(this);
        message = (Message)getIntent().getExtras().getSerializable("message");
        messageHandler = RequestHandlerFactory.getFactory().getMessageHandler(message.type);
        messageHandler.setContext(this);
        messageHandler.setMessage(message);
        MessageItemSource source = messageHandler.decodeMessageSource();
        setTitle(messageHandler.getTitle());
        ( (TextView)findViewById(R.id.messsage)).setText(messageHandler.getMessage());
        ((TextView)findViewById(R.id.description)).setText(messageHandler.getDescription());
        ((TextView)findViewById(R.id.name)).setText(source.getName());
        icon = (WebImageView)findViewById(R.id.icon);
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.displayer(new RoundedBitmapDisplayer(0x3e7));
        ImageLoader.getInstance().displayImage(source.getWebIcon(), icon, builder.build(), this);
        icon.setPopuShowAble();
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.agreeButton:
            {
                messageHandler.handleAgree();
                return;
            }
            case R.id.ignoreButton:
            {
                messageHandler.handleIgnore();
                return;
            }
            case R.id.refuseButton:
            {
                messageHandler.handleRefuse();
                break;
            }
        }
    }
    
    public int getConentLayout() {
        return R.layout.activity_request_handle;
    }
    
    public int getActionBarTitle() {
        return 0x0;
    }
    
    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.displayer(new RoundedBitmapDisplayer(0x3e7));
        ImageLoader.getInstance().displayImage("drawable://R.drawable.icon_head_default", icon, builder.build());
    }
    
    public void onLoadingComplete(String url, View arg1, Bitmap arg2) {
        icon.setUrl(url);
    }
    
    public void onLoadingCancelled(String arg0, View arg1) {
    }
    
    public void onLoadingStarted(String arg0, View arg1) {
    }
}