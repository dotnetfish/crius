/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui.setting;

import android.content.Intent;
import android.os.Process;
import android.view.View;
import cn.cloudartisan.crius.client.android.CIMPushManager;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.UpgradeManger;
import cn.cloudartisan.crius.ui.LoginActivity;
import cn.cloudartisan.crius.ui.base.BaseActivity;

public class SettingCenterActivity extends BaseActivity {
    User user;
    
    public void initComponents() {
        user = Global.getCurrentUser();
        setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.cache_item).setOnClickListener(this);
        findViewById(R.id.sound_config_item).setOnClickListener(this);
        findViewById(R.id.update_item).setOnClickListener(this);
        findViewById(R.id.about_item).setOnClickListener(this);
        findViewById(R.id.feedback_item).setOnClickListener(this);
        findViewById(R.id.logoutButton).setOnClickListener(this);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.feedback_item:
            {
                Intent fintent = new Intent(this, FeedbackActivity.class);
                startActivity(fintent);
                break;
            }
            case R.id.about_item:
            {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.update_item:
            {
                new UpgradeManger(this).excute(true);
                break;
            }
            case R.id.sound_config_item:
            {
                Intent sintent = new Intent(this, NotifySettingActivity.class);
                startActivity(sintent);
                break;
            }
            case R.id.logoutButton:
            {
                doLogout();
                break;
            }
            case R.id.cache_item:
            {
                break;
            }
        }
    }
    
    public void doLogout() {
        Global.removePassword();
        CriusApplication.finishAllActivity();
        CIMPushManager.stop(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    
    public void doEdxit() {
        CIMPushManager.destory(this);
        CriusApplication.finishAllActivity();
        Process.killProcess(Process.myPid());
    }
    
    public int getConentLayout() {
        return R.layout.activity_settingcenter;
    }
    
    public int getActionBarTitle() {
        return R.string.common_setting;
    }
}
