/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.content.Intent;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.CriusApplication;

import android.os.Bundle;

public class LoginAlertActivity extends Activity implements View.OnClickListener {
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        initComponents();
    }
    
    private void initComponents() {
        findViewById(R.id.btnLayout).setVisibility(View.GONE);
        findViewById(R.id.singleBtnLayout).setVisibility(View.VISIBLE);
        findViewById(R.id.singBtn).setOnClickListener(this);
        ((TextView)findViewById(R.id.singBtn)).setText(R.string.common_confirm);
        ((TextView)findViewById(R.id.dialogTitle)).setText(R.string.label_logout_notify);
        ((TextView)findViewById(R.id.dialogText)).setText(R.string.label_logout_detail);
    }
    
    public void onBackPressed() {
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.singBtn:
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                CriusApplication.finishAllActivity();
                break;
            }
        }
    }
    
    public boolean onTouchEvent(MotionEvent event) {
        if((event.getAction() == 0) && (isOutOfBounds(this, event))) {
            return true;
        }
        return super.onTouchEvent(event);
    }
    
    private boolean isOutOfBounds(Activity context, MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        View decorView = context.getWindow().getDecorView();
        return ((x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop)) || (y > (decorView.getHeight() + slop)));
    }
}
