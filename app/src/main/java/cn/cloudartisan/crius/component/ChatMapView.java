/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.listener.OnUploadProgressListener;
import cn.cloudartisan.crius.ui.chat.MapViewActivity;
import cn.cloudartisan.crius.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;

public class ChatMapView extends RelativeLayout implements View.OnClickListener, ImageLoadingListener, OnUploadProgressListener {
    Context _context;
    ImageView image;
    String key;
    Message message;
    DisplayImageOptions options;
    View progressbar;
    TextView text;
    ProgressBar uploadProgressBar;
    DecimalFormat format = new DecimalFormat("0.00");
    
    public ChatMapView(Context context, AttributeSet attrs) {
        super(context, attrs, 0x0);
        _context = context;
    }
    
    public void onFinishInflate() {
        super.onFinishInflate();
        text = (TextView)findViewById(R.id.text);
        image = (ImageView)findViewById(R.id.image);
        progressbar = findViewById(R.id.progress_view);
        this.options = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(true).considerExifParams(true).build();    uploadProgressBar = (ProgressBar)findViewById(R.id.uploadProgressBar);
    }
    
    public void initViews(Message msg) {
        message = msg;
        key = message.file;
        text.setText(JSON.parseObject(message.content).getString("address"));
        String url = StringUtils.getOSSFileURI("lvxin-files", key);
        ImageLoader.getInstance().displayImage(url, image, options, this);
    }
    
    public void onClick(View view) {
        Intent intentLoc = new Intent(_context, MapViewActivity.class);
        intentLoc.putExtra("data", message.content);
        _context.startActivity(intentLoc);
    }
    
    public void onLoadingStarted(String imageUri, View view) {
        progressbar.setVisibility(View.VISIBLE);
    }
    
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        onLoadingComplete(imageUri, view, null);
        progressbar.setVisibility(View.GONE);
    }
    
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        progressbar.setVisibility(View.GONE);
    }
    
    public void onLoadingCancelled(String imageUri, View view) {
        progressbar.setVisibility(View.GONE);
    }
    
    public void onProgress(float progress) {
        uploadProgressBar.setVisibility(View.VISIBLE);
        uploadProgressBar.setProgress((int)progress);
        if(progress >= 100.0f) {
            uploadProgressBar.setVisibility(View.GONE);
        }
    }
}
